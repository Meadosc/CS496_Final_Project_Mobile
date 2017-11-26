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

public class MainActivity extends AppCompatActivity {

    private OkHttpClient mOkHttpClient;
    private String FRONT_PAGE_URL = "http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MyTag", "testing, testing, 1 2 3");

        mOkHttpClient = new OkHttpClient();

        HttpUrl reqUrl = HttpUrl.parse(FRONT_PAGE_URL);
        Request request = new Request.Builder()
                .url(reqUrl)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("MyTag", "testing, testing, 4 5 6");
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String r = response.body().string(); //put JSON in string variable r
                Log.d("MyTag", "testing, testing, 7 8 9");
                try {

                    JSONObject j = new JSONObject(r); //create JSON object with JSON valid string r
                    String title = j.getString("title"); //extract "title" from json object.
                    String[] testArray = {title}; // declare and populate array for adapter

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, testArray);
                    ListView listView = (ListView) findViewById(R.id.test_text_view);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ListView)findViewById(R.id.test_text_view)).setAdapter(adapter);
                        }
                    });

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        });



    }
}
