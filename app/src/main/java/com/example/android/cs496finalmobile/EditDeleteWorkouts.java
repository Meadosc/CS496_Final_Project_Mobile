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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditDeleteWorkouts extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient mOkHttpClient;
    private String WORKOUT_ADD_URL = "http://10.0.2.2:8080/workout";
    private String mURLwithID;
    private String[] mWorkout = new String[5];
    private String mWorkoutJSON;
    Button mAddButton;
    Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_workouts);

        /////////////////////////////////////
        //Add button. Get object info.
        mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkout[0] = ((EditText)findViewById(R.id.workoutID_input)).getText().toString();
                mWorkout[1] = ((EditText)findViewById(R.id.name_input)).getText().toString();
                mWorkout[2] = ((EditText)findViewById(R.id.date_input)).getText().toString();
                mWorkout[3] = ((EditText)findViewById(R.id.type_input)).getText().toString();
                mWorkout[4] = ((EditText)findViewById(R.id.notes_input)).getText().toString();


                //////////////////////////////////////
                //Begin HTTP calls
                mOkHttpClient = new OkHttpClient();

                mWorkoutJSON = "{\"name\":\"" + mWorkout[1] + "\", \"date\":\"" + mWorkout[2] + "\", \"type\":\"" + mWorkout[3] + "\", \"notes\":\"" + mWorkout[4] + "\"}";

                Log.d("myTag", mWorkoutJSON);

                RequestBody body = RequestBody.create(JSON, mWorkoutJSON);

                //Create url with ID
                mURLwithID = WORKOUT_ADD_URL + "/" + mWorkout[0];

                HttpUrl reqUrl = HttpUrl.parse(mURLwithID);
                Request request = new Request.Builder()
                        .url(reqUrl)
                        .patch(body) //testing
                        .build();

                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }


                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("myTag", "workout added");

                    }
                });


            }
        });

        /////////////////////////////////////
        //delete button. Get object info.
        mDeleteButton = (Button) findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkout[0] = ((EditText)findViewById(R.id.workout_delete_input)).getText().toString();



                //////////////////////////////////////
                //Begin HTTP calls
                mOkHttpClient = new OkHttpClient();


                //Create url with ID
                mURLwithID = WORKOUT_ADD_URL + "/" + mWorkout[0];

                HttpUrl reqUrl = HttpUrl.parse(mURLwithID);
                Request request = new Request.Builder()
                        .url(reqUrl)
                        .delete() //testing
                        .build();

                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }


                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("myTag", "workout deleted");

                    }
                });


            }
        });

    }
}
