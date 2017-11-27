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

        //////////////////////////////////////
        //Link buttons to activities
        //////////////////////////////////////

        //Workout view button click listener and onClick activity opener
        Button button1 = (Button) findViewById(R.id.workouts_button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open activity
                Intent intent = new Intent(MainActivity.this, ViewWorkouts.class);
                startActivity(intent);
            }
        });

        //Exercise view button click listener and onClick activity opener
        Button button2 = (Button) findViewById(R.id.exercise_button);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open activity
                Intent intent = new Intent(MainActivity.this, ViewExercises.class);
                startActivity(intent);
            }
        });

        //add workouts button click listener and onClick activity opener
        Button button3 = (Button) findViewById(R.id.add_workouts_button);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open activity
                Intent intent = new Intent(MainActivity.this, AddWorkouts.class);
                startActivity(intent);
            }
        });

        //add exercise button click listener and onClick activity opener
        Button button4 = (Button) findViewById(R.id.add_exercises_button);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open activity
                Intent intent = new Intent(MainActivity.this, AddExercises.class);
                startActivity(intent);
            }
        });

        //edit and delete workouts button click listener and onClick activity opener
        Button button5 = (Button) findViewById(R.id.edit_workouts_button);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open activity
                Intent intent = new Intent(MainActivity.this, EditDeleteWorkouts.class);
                startActivity(intent);
            }
        });

        //edit and delete exercises button click listener and onClick activity opener
        Button button6 = (Button) findViewById(R.id.edit_exercises_button);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open activity
                Intent intent = new Intent(MainActivity.this, EditDeleteExercises.class);
                startActivity(intent);
            }
        });





        //////////////////////////////////////
        //Begin HTTP calls
        mOkHttpClient = new OkHttpClient();

        HttpUrl reqUrl = HttpUrl.parse(FRONT_PAGE_URL);
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

                    //////////////////////////////
                    //Set up JSON
                    JSONObject j = new JSONObject(r); //create JSON object with JSON valid string r
                    String title = j.getString("title"); //extract "title" from json object.
                    String[] testArray = {title}; // declare and populate array for adapter


                    //////////////////////////////
                    //Set adapter
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, testArray);
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
