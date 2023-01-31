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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class MainScreen extends AppCompatActivity {
    TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Intent intent = getIntent();
        clickListener();


    }

    public void clickListener()
    {
        ImageView viewProfile = (ImageView) findViewById(R.id.viewProfile);
        ImageView viewTeam = (ImageView) findViewById(R.id.viewTeam);
        ImageView viewChat = (ImageView) findViewById(R.id.viewChat);
        ImageView logout = (ImageView)findViewById(R.id.logout);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openProfileScreen();
            }
        });

        viewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openViewTeam();
            }
        });

        viewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openChat();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                logout();
            }
        });


    }

    public void openProfileScreen() {
        Intent intent = new Intent(getApplicationContext(),ViewProfile.class);
        startActivity(intent);

    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        Toast.makeText(getApplicationContext(),"Logout", Toast.LENGTH_SHORT).show();
    }


    public void openChat(){
        Intent intent = new Intent(getApplicationContext(),chatActivity.class);
        startActivity(intent);

    }



    public void openViewTeam(){
        Intent intent = new Intent(getApplicationContext(),ViewTeam.class);
        startActivity(intent);

    }


}


