package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.material.button.MaterialButton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton loginbutton =(MaterialButton) findViewById(R.id.loginbutton);
        String displayUser = "Welcome" + username.getText().toString();


        try {
            TextView textView = new TextView(this);
            textView.setText("Hello World!");
            setContentView(textView);

            Socket socket = new Socket("192.168.18.2", 4444);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            String message = "Hello from the client!";
            outputStream.writeUTF(message);
            System.out.println("Sent message to server: " + message);
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
                    Intent intent = new Intent(getApplicationContext(),SecondScreen.class);
                    intent.putExtra("message_key",displayUser);
                    startActivity(intent);

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