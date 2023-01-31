package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class setProfile extends AppCompatActivity {

    private CardView mgetuserimg;
    private ImageView getuserimageinimageview;
    private static int PICK_IMAGE = 123;
    public Uri imagepath;
    private EditText mgetusername;
    private android.widget.Button saveprofile;

    private FirebaseAuth firebaseAuth;
    private String name;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String imageuriaccesstoken;
    private FirebaseFirestore firebaseFirestore;
    private ImageButton backbuttonofsetprofile;
    private androidx.appcompat.widget.Toolbar mtoolbarofsetprofile;
    ProgressBar progressbarofsetprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        mtoolbarofsetprofile=findViewById(R.id.toolbarofsetprofile);
        backbuttonofsetprofile=findViewById(R.id.backbuttonofsetprofile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance(); // we are using storage
        storageReference = firebaseStorage.getReference(); //storing of img stored in storage reference
        firebaseFirestore = FirebaseFirestore.getInstance();

        mgetusername = findViewById(R.id.getusername);
        mgetuserimg = findViewById(R.id.getuserimage);
        getuserimageinimageview = findViewById(R.id.getuserimageinimageview);

        saveprofile = findViewById(R.id.safeProfile);
        progressbarofsetprofile = findViewById(R.id.progressbarofSetprofile);


        setSupportActionBar(mtoolbarofsetprofile);

        backbuttonofsetprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mgetuserimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //all them to choose a picture
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mgetusername.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name is Empty", Toast.LENGTH_SHORT).show();
                } else if (imagepath == null) {
                    Toast.makeText(getApplicationContext(), "Image is Empty", Toast.LENGTH_SHORT).show();
                } else {

                    progressbarofsetprofile.setVisibility(View.VISIBLE);
                    sendDataForNewUser();
                    progressbarofsetprofile.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(setProfile.this, chatActivity.class);
                    startActivity(intent);
                    finish();


                }
            }
        });
    }
    private void sendDataForNewUser()
    {

        sendDataToRealTimeDatabase();

    }

    private void sendDataToRealTimeDatabase()
    {


        name=mgetusername.getText().toString().trim();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());

        Userprofile userprofile=new Userprofile(name,firebaseAuth.getUid());
        databaseReference.setValue(userprofile);
        Toast.makeText(getApplicationContext(),"User Profile Added Sucessfully",Toast.LENGTH_SHORT).show();
        sendImagetoStorage();




    }

    private void sendImagetoStorage()
    {

        StorageReference imageref=storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic");

        //Image compresesion

        Bitmap bitmap=null;
        try {
            bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,25,byteArrayOutputStream);
        byte[] data=byteArrayOutputStream.toByteArray();

        ///putting image to storage

        UploadTask uploadTask=imageref.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageuriaccesstoken=uri.toString();
                        Toast.makeText(getApplicationContext(),"URI get success",Toast.LENGTH_SHORT).show();
                        sendDataTocloudFirestore();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"URI get Failed",Toast.LENGTH_SHORT).show();
                    }


                });
                Toast.makeText(getApplicationContext(),"Image is uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Image not uploaded",Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void sendDataTocloudFirestore() {


        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        Map<String , Object> userdata=new HashMap<>();
        userdata.put("name",name);
        userdata.put("image",imageuriaccesstoken);
        userdata.put("uid",firebaseAuth.getUid());
        userdata.put("status","Online");

        documentReference.set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Data on Cloud firestore send success",Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK)
        {
            imagepath=data.getData();
            getuserimageinimageview.setImageURI(imagepath);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}

