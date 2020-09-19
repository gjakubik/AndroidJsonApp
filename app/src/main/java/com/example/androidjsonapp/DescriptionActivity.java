package com.example.androidjsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DescriptionActivity extends AppCompatActivity {

    private String queryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Bundle extras = getIntent().getExtras();
        if(extras != null){

        }
    }
}