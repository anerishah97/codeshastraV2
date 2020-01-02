package com.example.codeshastrahealthcarev1;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class addNewRecord extends AppCompatActivity {

    TextView addnewrec;
    ImageButton back;
    EditText symptoms, diagnosis, medicine;
    healthcare application;
    String symptoms_s, diagnosis_s, medicine_s, serverresponse, aadhar_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);
        addnewrec = findViewById(R.id.add_newrec);
        symptoms = findViewById(R.id.symptoms_newrec);
        diagnosis = findViewById(R.id.diagnosis_newrec);
        medicine = findViewById(R.id.medicine_newrec);
        back = findViewById(R.id.back);
        application = new healthcare();
        application.userDataPreference = PreferenceManager.getDefaultSharedPreferences(this);
        aadhar_s = application.userDataPreference.getString("aadharNum", null);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        addnewrec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                symptoms_s = symptoms.getText().toString();
                diagnosis_s = diagnosis.getText().toString();
                medicine_s = medicine.getText().toString();
                Log.d("medicine",medicine_s);

                if(symptoms_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter symptoms", Toast.LENGTH_SHORT).show();
                }
                else if(diagnosis_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter diagnosis", Toast.LENGTH_SHORT).show();
                }
                else if(medicine_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter the medicines", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    enterNewRecord();
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

    public void enterNewRecord()
    {
        String url = application.baseUrl;
        url = url + "/CreateNewPatientRecord/";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("aadhaarid", aadhar_s)
                .add("symptoms", symptoms_s)
                .add("diagnosis",diagnosis_s)
                .add("addedby", "-1")
                .add("medication", medicine_s)
                .add("attatchmentlink", "anish")
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
                    startActivity(new Intent(addNewRecord.this, MainActivity.class));
                }
                else
                {
                    Log.d("aa",serverresponse);
                }
            }
        });
    }
}
