package com.example.android.cs496finalmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewWorkouts extends AppCompatActivity {

    private OkHttpClient mOkHttpClient;
    private String WORKOUT_VIEW_URL = "http://10.0.2.2:8080/workout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workouts);



        //////////////////////////////////////
        //Begin HTTP calls
        mOkHttpClient = new OkHttpClient();

        HttpUrl reqUrl = HttpUrl.parse(WORKOUT_VIEW_URL);
        Request request = new Request.Builder()
                .url(reqUrl)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String r = response.body().string(); //put JSON in string variable r
                try {

                    ////////////////////////////
                    //Prepare array and map from json response.
                    Log.d("MyTag","r: " + r);

                    JSONArray items = new JSONArray(r); //create JSON array with JSON valid string r
                    List<Map<String,String>> posts = new ArrayList<Map<String,String>>();
                    for(int i = 0; i < items.length(); i++){
                        HashMap<String, String> m = new HashMap<String, String>();
                        m.put("name", items.getJSONObject(i).getString("name"));
                        m.put("date",items.getJSONObject(i).getString("date"));
                        m.put("type",items.getJSONObject(i).getString("type"));
                        m.put("notes",items.getJSONObject(i).getString("notes"));
                        m.put("workoutURLID",items.getJSONObject(i).getString("workoutURLID"));

                        //debug
                        m.put("exerciseIDs",items.getJSONObject(i).getString("exerciseIDs"));
                        //debug

                        posts.add(m);
                        Log.d("MyTag", "testing, testing, " + i);
                    }



                    final SimpleAdapter adapter = new SimpleAdapter(
                            ViewWorkouts.this,
                            posts,
                            R.layout.workout_list_item,
                            new String[]{"name", "date", "type", "notes", "workoutURLID", "exerciseIDs"},
                            new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6});
                    //ListView listView = (ListView) findViewById(R.id.workout_view);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ListView)findViewById(R.id.workout_view)).setAdapter(adapter);
                        }
                    });

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        });





    }
}
