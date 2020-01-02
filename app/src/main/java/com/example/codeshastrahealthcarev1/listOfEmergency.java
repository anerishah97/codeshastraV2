package com.example.codeshastrahealthcarev1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class listOfEmergency extends AppCompatActivity {
    RecyclerView recyclerView;
    healthcare application;
    String searchString;
    String serverresponse;
    final List<emergencyData> data = new ArrayList<>();
    double latitude, longitude;
    emergencyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_emergency);
        application = new healthcare();
        getData();

        recyclerView = findViewById(R.id.recyclerview_emergency_search);
        adapter = new emergencyAdapter(getApplicationContext(), data);

        recyclerView.setLayoutManager(new LinearLayoutManager(listOfEmergency.this));
        recyclerView.setAdapter(adapter);


        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation()) {
            gps = new GPSTracker(listOfEmergency.this);
            if(gps.canGetLocation()){

                 latitude = gps.getLatitude();
                 longitude = gps.getLongitude();
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{
                gps.showSettingsAlert();
            }
        }



    }

    public void getData()
    {
        String url = application.baseUrl + "/EmergencyDoctorSearch/?latitude="+String.valueOf(latitude) + "/?longitude="+String.valueOf(longitude);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Log.d("requestsbody", requestBody.toString());
        final Request request = getNewRequest(url, requestBody);
        Log.d("emergency", request.toString());

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

    public class emergencyData{
        String name;
        String specialist;
        String currentLocation;
    }

    class emergencyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private LayoutInflater inflater;

        List<emergencyData> data = Collections.emptyList();
        emergencyData current;
        private Context context;

        public emergencyAdapter(Context context, List<emergencyData> data)
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

            public MyHolder(final View itemView)
            {
                super(itemView);
                specialist_name = (TextView)itemView.findViewById(R.id.name_specialist);
                speciality = (TextView)itemView.findViewById(R.id.speciality);
                location = itemView.findViewById(R.id.specialist_currentlocation);

            }
        }
    }
}
