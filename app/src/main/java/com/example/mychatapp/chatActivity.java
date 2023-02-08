package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class chatActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem chat, status;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tabLayout=findViewById(R.id.include);
        chat = findViewById(R.id.chat);
        status = findViewById(R.id.status);

        toolbar = findViewById(R.id.toolbar);
        viewPager= findViewById(R.id.fragmentcontainer);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        pageAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        setSupportActionBar(toolbar);
        viewPager.setAdapter(pageAdapter);

        Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_more_vert_24); //to use the vertical 3 dot icon instead
        toolbar.setOverflowIcon(drawable);

        //people click on status or chat
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0 || tab.getPosition()==1)
                {
                    pageAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            //ERROR HERE NEED SETTLE THE PROFILE INITALLY
            case R.id.setprofile:
                Intent intent = new Intent(chatActivity.this, setProfile.class);
                startActivity(intent);
                break;

            case R.id.updateprofile:
                 intent = new Intent(chatActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(chatActivity.this, MainActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"Logout", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"User is Offline",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"User is Online",Toast.LENGTH_SHORT).show();
            }
        });

    }


}