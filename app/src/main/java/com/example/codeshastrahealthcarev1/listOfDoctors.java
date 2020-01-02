package com.example.codeshastrahealthcarev1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class listOfDoctors extends AppCompatActivity {

    RecyclerView recyclerView;
    healthcare application;
    String searchString;
    String serverresponse;
    final List<doctorData> data = new ArrayList<>();

    doctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_doctors);
        searchString = getIntent().getExtras().getString("searchstring");
        application = new healthcare();
        Log.d("a", searchString);
        getData();

        recyclerView = findViewById(R.id.recyclerview_doctor_search);
        adapter = new doctorAdapter(getApplicationContext(), data);

        recyclerView.setLayoutManager(new LinearLayoutManager(listOfDoctors.this));
        recyclerView.setAdapter(adapter);



    }

    public void getData()
    {
        String url = application.baseUrl + "/SearchDoctor/?name="+searchString;
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("searchstring", searchString)
                .build();
        Log.d("requestsbody", requestBody.toString());
        final Request request = getNewRequest(url, requestBody);
        Log.d("doctor", request.toString());

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

                    listOfDoctors.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(serverresponse);
                                JSONArray words = jsonObject.getJSONArray("data");

                                for(int i=0;i<words.length();i++)
                                {
                                    JSONObject a = words.getJSONObject(i);
                                    doctorData w = new doctorData();
                                    w.name = a.getString("name");
                                    w.specialist = a.getString("speciality");
                                    w.currentLocation = a.getString("currentlocation");
                                    data.add(w);
                                }
                                adapter.notifyDataSetChanged();


                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

    }
    public Request getNewRequest(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }

    public class doctorData{
        String name;
        String specialist;
        String currentLocation;
    }

    class doctorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private LayoutInflater inflater;

        List<doctorData> data = Collections.emptyList();
        doctorData current;
        private Context context;

        public doctorAdapter(Context context, List<doctorData> data)
        {
            inflater= LayoutInflater.from(context);

            this.context=context;
            this.data=data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.doctor_row, parent,false);
            inflater= LayoutInflater.from(context);
            MyHolder holder=new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final MyHolder myHolder= (MyHolder) holder;
            current =data.get(position);
            myHolder.specialist_name.setText(current.name);
            myHolder.speciality.setText(current.specialist);
            myHolder.location.setText(current.currentLocation);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        class MyHolder extends RecyclerView.ViewHolder
        {
            TextView specialist_name, speciality, location;
            ImageView call;

            public MyHolder(final View itemView)
            {
                super(itemView);
                specialist_name = (TextView)itemView.findViewById(R.id.name_specialist);
                speciality = (TextView)itemView.findViewById(R.id.speciality);
                location = itemView.findViewById(R.id.specialist_currentlocation);
                call = itemView.findViewById(R.id.video_call);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Log.d("name", specialist_name.getText().toString());
                        startActivity(new Intent(context,VideoChat.class));
                    }
                });

            }
        }
    }

}
