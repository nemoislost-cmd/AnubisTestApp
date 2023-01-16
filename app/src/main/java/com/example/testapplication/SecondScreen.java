package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
public class SecondScreen extends AppCompatActivity {
    TextView welcomeMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        //welcomeMessage = (TextView) findViewById(R.id.received_value_id);
        Intent intent = getIntent();
        clickListener();
        //String displayMessage = intent.getStringExtra("message_key");
        //welcomeMessage.setText(displayMessage);


    }

    public void clickListener()
    {
        ImageView viewProfile = (ImageView) findViewById(R.id.viewProfile);
        ImageView viewTeam = (ImageView) findViewById(R.id.viewTeam);
        ImageView viewChat = (ImageView) findViewById(R.id.viewChat);
        ImageView viewGallery = (ImageView)findViewById(R.id.viewGallery);
        ImageView viewUpload = (ImageView)findViewById(R.id.viewUpload);
        ImageView viewUnicorn = (ImageView)findViewById(R.id.viewUnicorn);
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
        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openGallery();
            }
        });

        viewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openUpload();
            }
        });

        viewUnicorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openUnicorn();
            }
        });


    }

    public void openUnicorn(){
        Intent intent = new Intent(getApplicationContext(),ViewUnicorn.class);
        startActivity(intent);

    }

    public void openUpload(){
        Intent intent = new Intent(getApplicationContext(),ViewUpload.class);
        startActivity(intent);

    }

    public void openGallery(){
        Intent intent = new Intent(getApplicationContext(),ViewGallery.class);
        startActivity(intent);

    }
    public void openChat(){
        Intent intent = new Intent(getApplicationContext(),ViewChat.class);
        startActivity(intent);

    }

    public void openProfileScreen(){
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);

    }

    public void openViewTeam(){
        Intent intent = new Intent(getApplicationContext(),ViewTeam.class);
        startActivity(intent);

    }
}