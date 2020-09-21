package com.example.androidjsonapp;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidjsonapp.utilities.NetworkUtils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView resultDisplay;
    private EditText descriptionText;
    private Button descriptionButton;
    private Button searchButton;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultDisplay = (TextView) findViewById(R.id.tv_display_text);
        descriptionText = (EditText) findViewById(R.id.et_search);
        descriptionButton = (Button) findViewById(R.id.description_button);
        searchButton = (Button) findViewById(R.id.search_button);

        resultDisplay.setMovementMethod(new ScrollingMovementMethod());

        titles = new ArrayList<String>();

        makeNetworkSearchQuery();

        descriptionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Class destinationActivity = DescriptionActivity.class;

                        Intent startDescriptionActivityIntent = new Intent(MainActivity.this, destinationActivity);
                        String msg = descriptionText.getText().toString();
                        startDescriptionActivityIntent.putExtra(Intent.EXTRA_TEXT, msg);

                        startActivity(startDescriptionActivityIntent);
                        Log.d("info", "Description Activity Launched");
                    }
                }
        );

        searchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchStr = descriptionText.getText().toString();

                        boolean foundAPI = false;
                        for(String title: titles){
                            // Log.d("info", title + " " + searchStr);
                            if(title.toLowerCase().equals(searchStr.toLowerCase())){
                                foundAPI = true;
                                resultDisplay.setText(title);
                            }
                        }

                        if(!foundAPI){
                            resultDisplay.setText("Unable to find ");
                            resultDisplay.append(descriptionText.getText());
                            resultDisplay.append(" in APIs");
                        }
                    }
                }
        );
    }

    public void makeNetworkSearchQuery(){
        new FetchNetworkData().execute();
    }

    public class FetchNetworkData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){

            URL searchUrl = NetworkUtils.buildApisUrl("");
            String responseString = null;
            try{
                responseString = NetworkUtils.getResponseFromUrl(searchUrl);
            }catch(Exception e){
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String responseData){
            super.onPostExecute(responseData);
            //Add elements of list to UI
            ArrayList<String> apis = NetworkUtils.parseApisJson(responseData);
            for(String title : apis){
                String logStr = "Adding title: " + title;
                Log.d("info", logStr);
                if(title != null) {
                    resultDisplay.append("\n\n" + title);
                    titles.add(title);
                }
            }
        }
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