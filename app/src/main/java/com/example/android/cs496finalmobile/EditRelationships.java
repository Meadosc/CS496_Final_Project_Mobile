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

public class EditRelationships extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient mOkHttpClient;
    private String RELATIONSHIP_URL = "http://10.0.2.2:8080/workout";
    private String[] mRelationship = new String[2];
    private String mRelationshipJSON;
    Button mAddButton;
    Button mRemoveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_relationships);

        /////////////////////////////////////
        //Add button.
        mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelationship[0] = ((EditText)findViewById(R.id.workoutID_input)).getText().toString();
                mRelationship[1] = ((EditText)findViewById(R.id.exerciseID_input)).getText().toString();



                //////////////////////////////////////
                //Begin HTTP calls
                mOkHttpClient = new OkHttpClient();

                mRelationshipJSON = "{\"URL_ID\":\"" + mRelationship[1] + "\"}";
                RequestBody body = RequestBody.create(JSON, mRelationshipJSON);

                HttpUrl reqUrl = HttpUrl.parse(RELATIONSHIP_URL + "/" + mRelationship[0] + "/add");
                Request request = new Request.Builder()
                        .url(reqUrl)
                        .post(body) //testing
                        .build();

                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }


                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("myTag", "relationship added");

                    }
                });


            }
        });


        /////////////////////////////////////
        //Remove button.
        mRemoveButton = (Button) findViewById(R.id.remove_button);
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelationship[0] = ((EditText)findViewById(R.id.workoutID_input)).getText().toString();
                mRelationship[1] = ((EditText)findViewById(R.id.exerciseID_input)).getText().toString();



                //////////////////////////////////////
                //Begin HTTP calls
                mOkHttpClient = new OkHttpClient();

                mRelationshipJSON = "{\"URL_ID\":\"" + mRelationship[1] + "\"}";
                RequestBody body = RequestBody.create(JSON, mRelationshipJSON);

                HttpUrl reqUrl = HttpUrl.parse(RELATIONSHIP_URL + "/" + mRelationship[0] + "/remove");
                Request request = new Request.Builder()
                        .url(reqUrl)
                        .post(body) //testing
                        .build();

                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }


                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("myTag", "relationship removed");

                    }
                });


            }
        });

    }
}
