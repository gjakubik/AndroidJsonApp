package com.example.androidjsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.androidjsonapp.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    private String queryTitle;
    private TextView apiNameText;
    private TextView apiDescriptionText;
    private TextView apiAuthTypeText;
    private TextView apiCategoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Bundle extras = getIntent().getExtras();
        queryTitle = "No API Specified";
        if (extras != null) {
            queryTitle = extras.getString(Intent.EXTRA_TEXT);
        }
        String logStr = "Query API Name: " + queryTitle;
        Log.d("info", logStr);

        apiNameText = findViewById(R.id.tv_api_name);
        apiAuthTypeText = findViewById(R.id.tv_api_authTypeText);
        apiDescriptionText = findViewById(R.id.tv_api_descriptionText);
        apiCategoryText = findViewById(R.id.tv_api_categoryText);

        if (queryTitle.equals("No API Specified")) {
            Log.d("info", "Not making API request");
        }else{
            makeApiNameQuery(queryTitle);
        }
    }

    public void makeApiNameQuery(String queryString){
        new FetchNetworkData().execute(queryString);
    }

    public class FetchNetworkData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){

            URL searchUrl = NetworkUtils.buildApisUrl(params[0]);
            String responseString = null;
            try{
                responseString = NetworkUtils.getResponseFromUrl(searchUrl);
            }catch(Exception e){
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        //Data ordered in list ass follows:
        //0: API
        //1: Description
        //2: Auth
        //3: HTTPS
        //4: Cors
        //5: Link
        //6: Category
        protected void onPostExecute(String responseData){
            super.onPostExecute(responseData);
            //Add elements of list to UI
            ArrayList<String> data = NetworkUtils.parseApiData(responseData);

            apiNameText.setText(data.get(0));
            apiDescriptionText.setText(data.get(1));

            if(data.get(2).equals("")){
                apiAuthTypeText.setText("No API key needed");
            }
            else{
                apiAuthTypeText.setText(data.get(2));
            }

            apiCategoryText.setText(data.get(6));
        }
    }
}