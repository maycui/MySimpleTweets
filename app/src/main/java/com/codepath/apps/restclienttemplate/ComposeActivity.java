package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    EditText editText;
    TextView charCount;
    Tweet newTweet;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient();
        editText = (EditText) findViewById(R.id.editText);
        charCount = (TextView) findViewById(R.id.charCount);
        charCount.setText(String.valueOf(140));

        final TextWatcher txwatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charCount.setText(String.valueOf(140 - s.length()));
            }
            public void afterTextChanged(Editable s) {
            }
        };

        editText.addTextChangedListener(txwatcher);

        //configuring the button
        Button button = (Button) findViewById(R.id.tweetB);
        button.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                // send network request
                view = v;
                client.sendTweet(editText.getText().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Toast.makeText(ComposeActivity.this, "hello", Toast.LENGTH_SHORT).show();
                            newTweet = Tweet.fromJSON(response);
                            onSubmit(view);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("TwitterClient", responseString);
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("TwitterClient", errorResponse.toString());
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Log.d("TwitterClient", errorResponse.toString());
                        throwable.printStackTrace();            }
                });
            }
        });


    }

    public void onSubmit(View v) {
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("newTweet", Parcels.wrap(newTweet));
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }



}
