package com.murniandhany.myappfilm;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PrivateKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listFilm);

        new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/now_playing?api_key=78221a25965590753e4f2cb1e4d1c00d");
    }

    class CheckConnectionStatus extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try{
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try{
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();

                return s;
            }catch (IOException e) {
                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(s);

                ArrayList<DetailFilmActivity> filmList = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i=0; i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    DetailFilmActivity filmDetails = new DetailFilmActivity();
                    filmDetails.setOriginal_title(object.getString("original_title"));
                    filmDetails.setOverview(object.getString("overview"));
                    filmDetails.setRelease_date(object.getString("release_date"));
                    filmDetails.setPoster_path(object.getString("poster_path"));
                   filmList.add(filmDetails);
                }
                MovieArrayAdapter movieArrayAdapter = new MovieArrayAdapter(MainActivity.this, R.layout.activity_list_film,filmList);
                listView.setAdapter(movieArrayAdapter);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.setting:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

                break;
            case R.id.help:

                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
