package com.example.asynctaskexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //Reference our components in activity_main.xml
    Button startTaskBtn;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get our components by their ids
        startTaskBtn = findViewById(R.id.button);
        webView = findViewById(R.id.webView);

        //add a listener to our button
        startTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //execute our AsyncTask on button click
                //create a new instance
                MyAsyncTask task = new MyAsyncTask();
                //set the url we want to call in a string array
                String[] url = {"https://hephaestus.mobi/wp/2019/05/09/building-a-note-taking" +
                        "-app-using-android-android-architecture-components-part-one/"};
                //execute our AsyncTask
                task.execute(url);
            }
        });
    }

    /**
     *
     */
    private class MyAsyncTask extends AsyncTask<String, Void, String>{

        /**
         * Begins the thread in a background process
         */
        @Override
        protected String doInBackground(String... strings) {
            //use squares Okhttp class to make a web call
            OkHttpClient client = new OkHttpClient();
            //build the request with our passed url
            Request request =
                    new Request.Builder()
                            .url(strings[0])
                            .build();
            //place our request call in a try/catch because it may cause an exception that crashes our app
            try {
                //set the response to our built request and execute the call
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    //if it's successful return the resulting response body as a string
                    return Objects.requireNonNull(response.body()).string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Called when the task is done processing
         */
        @Override
        protected void onPostExecute(String result) {
            //update our webview to show the result
            webView.loadData(result, "text/html; charset=utf-8", "UTF-8");
        }
    }



}
