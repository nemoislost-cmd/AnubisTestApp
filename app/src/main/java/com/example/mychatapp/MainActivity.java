package com.example.mychatapp;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.Manifest;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_READ_PHONE_STATE = 1 ;

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
        String hpno = null;

        //need to set permission dynamically
        //get phone no need run 2 times then can get phone no.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            System.out.println("Failed to get phone no");
        } else {
            // Permission is already granted, you can access the phone state
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = telephonyManager.getLine1Number();
            System.out.println("Phone no " + phoneNumber);
            hpno =  phoneNumber;
        }

        String deviceManufacturer = Build.MANUFACTURER;
        String deviceModel = Build.MODEL;
        String androidVersion = Build.VERSION.RELEASE;
        String deviceNumber = hpno;
        System.out.println("Phone no2222 " + deviceNumber);

        // Store the information in a Map
        Map<String, String> deviceInfo = new HashMap<>();
        deviceInfo.put("manufacturer", deviceManufacturer);
        deviceInfo.put("model", deviceModel);
        deviceInfo.put("androidVersion", androidVersion);
        deviceInfo.put("phoneno", deviceNumber);


        // Send the information to the server
        new SendDeviceInfoTask().execute(deviceInfo);


        register = findViewById(R.id.registeruser);
        signIn = findViewById(R.id.loginbutton);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        progressbarofLogin = findViewById(R.id.progressbarofSignin);
        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterUser.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputemail = editEmail.getText().toString().trim();
                String inputpass = editPassword.getText().toString().trim();

                if (inputemail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email is empty ", Toast.LENGTH_SHORT).show();
                } else if (inputpass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT).show();
                } else if (inputpass.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password cannot less than 8", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(inputemail).matches()) {
                    Toast.makeText(getApplicationContext(), "Wrong email format ", Toast.LENGTH_SHORT).show();
                }
                progressbarofLogin.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(inputemail, inputpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //redirect to set profile
                            startActivity(new Intent(MainActivity.this, MainScreen.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to login ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }


    public static class SendDeviceInfoTask extends AsyncTask<Map<String, String>, Void, Void> {

        @Override
        protected Void doInBackground(Map<String, String>... deviceInfos) {
            try {

                URL url = new URL("https://5010-122-11-214-225.ap.ngrok.io/device"); //ngrok url to allow internet access without having to be same network
                //URL url = new URL("http://192.168.2.145:5000/device");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(new JSONObject(deviceInfos[0]).toString());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Server info respond: " + response.toString());
            } catch (Exception e) {
                System.out.println("ERROR MESSAGE BELOW");
                e.printStackTrace();
            }
            return null;
        }
    }


}


