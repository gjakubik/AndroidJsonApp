package com.example.androidjsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.menu_publicApis) {

            openWebPage("https://github.com/davemachado/public-api");
            Log.d("info", "Public API link opened");
        }
        else if(menuItemSelected == R.id.menu_sourceCode){

            openWebPage("https://github.com/gjakubik/AndroidJsonApp");
        }

        return true;
    }

    public void openWebPage(String urlString){
        Uri webPage = Uri.parse(urlString);
        Intent openWebPageIntent = new Intent(Intent.ACTION_VIEW, webPage);

        startActivity(openWebPageIntent);

        }
}