package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity  {
    //variable for each elements

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressbarofLogin;
    private TextView register;
    private EditText editEmail, editPassword;
    private android.widget.Button signIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.registeruser);
        signIn=findViewById(R.id.loginbutton);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        progressbarofLogin = findViewById(R.id.progressbarofSignin);
        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterUser.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputemail = editEmail.getText().toString().trim();
                String inputpass = editPassword.getText().toString().trim();

                if(inputemail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email is empty ", Toast.LENGTH_SHORT).show();
                }

                else if(inputpass.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT).show();
                }
                else if (inputpass.length() < 8)
                {
                    Toast.makeText(getApplicationContext(), "Password cannot less than 8", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(inputemail).matches())
                {
                    Toast.makeText(getApplicationContext(), "Wrong email format ", Toast.LENGTH_SHORT).show();
                }
                progressbarofLogin.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(inputemail,inputpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //redirect to set profile
                            startActivity(new Intent(MainActivity.this, MainScreen.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed to login ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });




    }
}