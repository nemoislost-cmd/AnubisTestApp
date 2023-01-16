package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton loginbutton =(MaterialButton) findViewById(R.id.loginbutton);

        // test and test prob change this to a database

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("test") && password.getText().toString().equals("test"))
                { // correct password hardcoded for now
                    Context context = MainActivity.this;
                    CharSequence text = "SUCCESSFUL";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();

                }else{
                    Context context = MainActivity.this;
                    CharSequence text = "UNSUCCESSFUL";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();

                }
            }
        });
    }
}