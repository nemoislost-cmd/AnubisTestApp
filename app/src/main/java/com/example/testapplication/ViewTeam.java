package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.material.AlertDialogKt;
import androidx.compose.ui.window.DialogWindowProvider;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.view.WindowManager;

public class ViewTeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_team);
        clickListener();


    }

    public void clickListener()
    {
        RelativeLayout layout1= (RelativeLayout) findViewById(R.id.profile1);
        RelativeLayout layout2= (RelativeLayout) findViewById(R.id.profile2);
        RelativeLayout layout3= (RelativeLayout) findViewById(R.id.profile3);
        RelativeLayout layout4= (RelativeLayout) findViewById(R.id.profile4);
        RelativeLayout layout5= (RelativeLayout) findViewById(R.id.profile5);
        RelativeLayout layout6= (RelativeLayout) findViewById(R.id.profile6);

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openProfile1();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openProfile2();
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openProfile3();
            }
        });
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openProfile4();
            }
        });
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openProfile5();
            }
        });
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openProfile6();
            }
        });





    }

    private void openProfile6() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_view_profile6,null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void openProfile5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_view_profile5,null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openProfile4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_view_profile4,null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openProfile3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_view_profile3,null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openProfile2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_view_profile2,null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void openProfile1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_view_profile1,null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}