package com.example.codeshastrahealthcarev1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VideoChat extends AppCompatActivity {

    healthcare application;
    String serverresponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);
        application = new healthcare();
       // application.userDataPreference = PreferenceManager.getDefaultSharedPreferences(this);
       // String aadhar_s = application.userDataPreference.getString("aadharNum", null);

        //WebView webView = (WebView) findViewById(R.id.webView);

       // webView.loadUrl("https://appr.tc/r/" + "804442824363");
        videoChat();


        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://appr.tc/r/" + "804442824365"));
        startActivity(browserIntent);

    }

    public Request getNewRequest(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }


    public void videoChat()
    {
        String url = application.baseUrl + "/CreateNewVidChatRoom/";
        //  Log.d("aaaaaaaaaa",url);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("aadhaarid", "804442824363")
                .add("createdat","11-02-2017")
                .add("doctorid","-1")
                .build();
        Log.d("requestsbody", requestBody.toString());
        final Request request = getNewRequest(url, requestBody);
        Log.d("hey", request.toString());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("getData", "failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                serverresponse = response.body().string();
                if(response.isSuccessful())
                {
                    Log.d("serverResponse", serverresponse);
                   // startActivity(new Intent(manualRegistration.this, MainActivity.class));
                }
                else
                {
                    Log.d("aa",serverresponse);
                }
            }
        });

    }
}
