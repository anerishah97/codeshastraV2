package com.example.codeshastrahealthcarev1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

/**
 * Created by Adib on 13-Apr-17.
 */

public class recordsFragment extends Fragment {

    RecyclerView recyclerView;
    private OnFragmentInteractionListener listener;
    final List<recordData> data = new ArrayList<>();
    private recordAdapter adapter;
    String aadhar_s, serverresponse;
    ImageButton add_new_record;
    healthcare application;


    public static recordsFragment newInstance() {
        return new recordsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_records, container, false);
        recyclerView = (RecyclerView)rootview.findViewById(R.id.recyclerview_record_page);
        add_new_record = rootview.findViewById(R.id.add_new_record);
        application = new healthcare();

        add_new_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("add ", "new rec");
                Intent i = new Intent(getActivity(), addNewRecord.class);
                startActivity(i);
            }
        });
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aadhar_s = "833141213434";
        application.userDataPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        aadhar_s = application.userDataPreference.getString("aadharNum", null);
        //   Log.d("hiiiiiiiiiiiiii",aadhar_s);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new recordAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);

        getData();
    }

    public void getData()
    {
        data.clear();
        String url = application.baseUrl + "/GetAllPatientRecords/?aadhaarid=";
        url = url +  aadhar_s ;
        Log.d("url is", url);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(serverresponse);
                                JSONArray words = jsonObject.getJSONArray("data");

                                for(int i=0;i<words.length();i++)
                                {
                                    JSONObject a = words.getJSONObject(i);
                                    recordData r = new recordData();
                                    r.symptoms = a.getString("symptoms");
                                    r.diagnosis = a.getString("diagnosis");
                                    r.date = a.getString("createdon");
                                    r.attachment_link = a.getString("attatchmentlink");
                                    r.medication = a.getString("medication");
                                    r.addedby=a.getString("addedby");
                                    //   w.comment_count = a.getString("comment_count");
                                    data.add(r);
                                }
                                adapter.notifyDataSetChanged();


                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                    //startActivity(new Intent(manualRegistration.this, MainActivity.class));
                }
                else
                {
                    Log.d("aa",serverresponse);
                }
            }
        });


      /*  for(int i=0;i<4;i++)
        {
            recordData r = new recordData();
            r.diagnosis = "test_diagnosis";
            r.symptoms = "symptoms";
            r.date = "date";
            data.add(r);
            adapter.notifyDataSetChanged();
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }

    class recordData
    {
        public String diagnosis;
        public String symptoms;
        public String attachment_link;
        public String medication;
        public String date;
        public String addedby;
     //   public boolean isLiked;
     //   public boolean isBookmarked;
    }



    class recordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        TextToSpeech tts;
        private Context context;
        private LayoutInflater inflater;
        List<recordData> data = Collections.emptyList();
        recordData current;

        public recordAdapter(Context context, List<recordData> data)
        {
            this.context=context;
            inflater= LayoutInflater.from(context);
            this.data=data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.record_row, parent,false);
            MyHolder holder=new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final MyHolder myHolder= (MyHolder) holder;
            current=data.get(position);
            myHolder.diagnosis.setText(current.diagnosis);
            myHolder.symptoms.setText(current.symptoms);
            myHolder.date.setText(current.date);
            //     temp = current.comment_count + "comments";
            //    myHolder.comment_count.setText(temp);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            CardView record;
            TextView diagnosis, symptoms,date;
            public MyHolder(final View itemview)
            {
                super(itemview);
                diagnosis = (TextView)itemview.findViewById(R.id.record_diagnosis);
                symptoms = (TextView)itemview.findViewById(R.id.record_symptoms);
                date = (TextView)itemview.findViewById(R.id.record_date);
                //   comment_count = (TextView)itemview.findViewById(R.id.comment_text);
                record = itemview.findViewById(R.id.record_full_row);

                record.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recordData testObj = data.get(getAdapterPosition());
                        Log.d("Diagnosis is q",""+ testObj.date);

                    }
                });

            }
        }
    }
}