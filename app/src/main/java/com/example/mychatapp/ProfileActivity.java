package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import io.grpc.Context;

public class ProfileActivity extends AppCompatActivity {

     EditText viewusername;
     FirebaseAuth firebaseAuth;
     FirebaseDatabase firebaseDatabase;
     TextView movetoupdateprofile;
     FirebaseFirestore firebaseFirestore;
     ImageView viewuserimageinimageview;
     StorageReference storageReference;
     String ImageURIaccesstoken;

     androidx.appcompat.widget.Toolbar toolbarofviewprofiles;
     ImageButton backbuttonofviewprofile;

     FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        viewuserimageinimageview = findViewById(R.id.viewuserimageinimageview);
        viewusername=findViewById(R.id.viewusername);
        movetoupdateprofile=findViewById(R.id.movetoupdateprofile);
        firebaseFirestore=FirebaseFirestore.getInstance();
        toolbarofviewprofiles=findViewById(R.id.toolbarofviewprofile);
        backbuttonofviewprofile=findViewById(R.id.backbuttonofviewprofile);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        setSupportActionBar(toolbarofviewprofiles);

        backbuttonofviewprofile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        storageReference=firebaseStorage.getReference();
        storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageURIaccesstoken=uri.toString();
                Picasso.get().load(uri).into(viewuserimageinimageview);
            }
        });

        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userprofile userprofile =snapshot.getValue(Userprofile.class);
                viewusername.setText(userprofile.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to show", Toast.LENGTH_SHORT).show();
            }
        });

        movetoupdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UpdateProfile.class);
                intent.putExtra("nameofuser",viewusername.getText().toString());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext()," User is Offline",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext()," User is Online",Toast.LENGTH_SHORT).show();
            }
        });

    }
}