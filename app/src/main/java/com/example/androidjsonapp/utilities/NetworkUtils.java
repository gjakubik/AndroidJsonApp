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
            Log.d("info", "Adding Query message");
            apiStr += "?title=" + entryName;
        }
        try{
            apiUrl = new URL(apiStr);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        String logStr = "URL string created: " + apiUrl.toString();
        Log.d("info", logStr);

        return apiUrl;
    }

    public static String getResponseFromUrl(URL url)throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                String fullJsonString = scanner.next();
                Log.d("info", fullJsonString);
                return fullJsonString;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<String> parseApisJson(String apiResponse){
        ArrayList<String> apiList = new ArrayList<String>();
        Log.d("info", "Parsing JSON response");

        try{
            Log.d("info", "Inside try block");
            JSONObject allApisObject = new JSONObject(apiResponse);
            JSONArray allApisArray = allApisObject.getJSONArray("entries");
            int count = allApisObject.getInt("count");
            String logStr = "JSON object received: count = " + Integer.toString(count);
            Log.d("info", logStr);

            for(int i = 0; i < count; i++){
                JSONObject childJson = allApisArray.getJSONObject(i);
                if(childJson.has("API")){
                    String title = childJson.getString("API");
                    if(title != null) {
                        Log.d("info", title);
                        apiList.add(title);
                    }
                }
            }

        }catch(JSONException e){
            Log.d("info", "JSON Parsing Issue");
        }

        Log.d("info", "Returning list without error");

        return apiList;
    }

    public static ArrayList<String> parseApiData(String apiResponse){
        ArrayList<String> apiData = new ArrayList<String>();
        Log.d("info", "Parsing API data with response: " + apiResponse);
        try{
            JSONObject objectJson = new JSONObject(apiResponse);
            JSONArray apiSingleList = objectJson.getJSONArray("entries");

            JSONObject apiJson = apiSingleList.getJSONObject(0);
            apiData.add(apiJson.get("API").toString());
            apiData.add(apiJson.get("Description").toString());
            apiData.add(apiJson.get("Auth").toString());
            apiData.add(apiJson.get("HTTPS").toString());
            apiData.add(apiJson.get("Cors").toString());
            apiData.add(apiJson.get("Link").toString());
            apiData.add(apiJson.get("Category").toString());
        }catch(JSONException j){
            j.printStackTrace();
        }

        return apiData;
    }
}
