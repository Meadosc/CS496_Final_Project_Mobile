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


public class ViewRelationships extends AppCompatActivity {

    private OkHttpClient mOkHttpClient;
    private String RELATIONSHIP_VIEW_URL = "http://10.0.2.2:8080/workout/ThisTextIsAPlaceholder/add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_relationships);
        //////////////////////////////////////
        //Begin HTTP calls
        mOkHttpClient = new OkHttpClient();

        HttpUrl reqUrl = HttpUrl.parse(RELATIONSHIP_VIEW_URL);
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

                    Log.d("MyTag","try block");

                    ////////////////////////////
                    //Prepare array and map from json response.
                    Log.d("MyTag","r: " + r);

                    JSONArray items = new JSONArray(r); //create JSON array with JSON valid string r
                    Log.d("MyTag","after JSON array");
                    List<Map<String,String>> posts = new ArrayList<Map<String,String>>();
                    for(int i = 0; i < items.length(); i++){
                        HashMap<String, String> m = new HashMap<String, String>();
                        m.put("workoutURLID", items.getJSONObject(i).getString("workoutURLID"));
                        m.put("exerciseURLID0",items.getJSONObject(i).getString("exerciseURLID0"));
                        m.put("exerciseURLID1",items.getJSONObject(i).getString("exerciseURLID1"));
                        m.put("exerciseURLID2",items.getJSONObject(i).getString("exerciseURLID2"));
                        m.put("exerciseURLID3",items.getJSONObject(i).getString("exerciseURLID3"));
                        posts.add(m);
                        Log.d("MyTag", "testing, testing, " + i);
                    }



                    final SimpleAdapter adapter = new SimpleAdapter(
                            ViewRelationships.this,
                            posts,
                            R.layout.workout_list_item,
                            new String[]{"workoutURLID", "exerciseURLID0", "exerciseURLID1", "exerciseURLID2", "exerciseURLID3"},
                            new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5});
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
