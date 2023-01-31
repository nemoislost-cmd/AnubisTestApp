package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private EditText email, password;
    private android.widget.Button createuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        firebaseAuth=FirebaseAuth.getInstance();
        createuser=findViewById(R.id.registeruser);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressbarofregisteruser);

        createuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputemail = email.getText().toString().trim();
                String inputpassword = password.getText().toString().trim();

                if(inputemail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email is empty", Toast.LENGTH_SHORT).show();
                }
                else if(inputpassword.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT).show();
                }
                else if (inputpassword.length() < 8)
                {
                    Toast.makeText(getApplicationContext(), "Password cannot less than 8", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(inputemail).matches())
                {
                    Toast.makeText(getApplicationContext(), "Wrong email format ", Toast.LENGTH_SHORT).show();
                }


                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(inputemail, inputpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    //store the user object in db
                                    User user = new User(email, password);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getUid()) //get id for the registered user
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "successful ", Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);
                                                        //redirect to login page
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Failed to register ", Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });

                                        }else{
                                            Toast.makeText(getApplicationContext(), "Failed to register ", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }



                        });



            }
        });


    }
}