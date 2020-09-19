package com.example.androidjsonapp.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {

    // Build request URL
    // Empty string gets all entries
    // If not empty, queries data for only title that matches string
    public static URL buildApisUrl(String entryName){
        URL apiUrl = null;

        String apiStr = "https://api.publicapis.org/entries";
        if(entryName != ""){
            apiStr += "?title=" + entryName;
        }
        try{
            apiUrl = new URL(apiStr);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return apiUrl;
    }

    public static String getResponseFromUrl(URL url)throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput) return scanner.next();
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<String> parseApisJson(String apiResponse){
        ArrayList<String> apiList = new ArrayList<String>();

        try{
            JSONObject allApisObject = new JSONObject(apiResponse);
            JSONArray allApisArray = allApisObject.getJSONArray("results");

            for(int i = 0; i < allApisArray.length(); i++){
                JSONObject childJson = allApisArray.getJSONObject(i);
                if(childJson.has("API")){
                    String title = childJson.getString("API");
                    apiList.add(title);
                }
            }

        }catch(JSONException e){
            Log.d("info", "JSON Parsing Issue");
        }

        return apiList;
    }
}
