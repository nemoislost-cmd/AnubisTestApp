package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.material.button.MaterialButton;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

        new Clienting().execute();

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
    // Client code
    private class Clienting extends AsyncTask<Void,Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Change server's IP and port no here
                Socket socket = new Socket("192.168.18.48", 4444);
                // send message
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                // Testing message
                String message = "Hello from the client!";
                outputStream.writeUTF(message);
                System.out.println("Sent message to server: " + message);
                outputStream.close();

                //send files
                File file = new File("raw:/storage/emulated/0/Download/test.txt");
                byte[] bytes = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(bytes, 0, bytes.length);

                OutputStream os = socket.getOutputStream();
                os.write(bytes, 0, bytes.length);
                os.flush();

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}