package com.example.codeshastrahealthcarev1;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class register extends AppCompatActivity {
    public String name, gender, address;

    TextView takePic, manually;
    //qr code scanner object
    public healthcare application;
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         application = new healthcare();
        takePic = findViewById(R.id.register_with_picture);
        manually = findViewById(R.id.register_manually);
        application.userDataPreference = PreferenceManager.getDefaultSharedPreferences(this);

        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Scan the QR code on your Aadhar card");
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });

        manually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),manualRegistration.class);
                intent.putExtra("fromAadhar", false);
                startActivity(intent);
            }
        });
    }
    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                    String details = result.getContents().toString();
                    Log.d("Details", details);
                    String aadharNum = details.split("uid=\"")[1].split("\"")[0];
                    name = details.split("name=\"")[1].split("\"")[0];
                    gender = details.split("gender=\"")[1].split("\"")[0];
                    address = details.split("lm=\"")[1].split("\"")[0];

                    address = address + "," + details.split("vtc=\"")[1].split("\"")[0];
                    address = address + " \n," + details.split("dist=\"")[1].split("\"")[0];
                    address = address + "," + details.split("state=\"")[1].split("\"")[0];
                    address = address + "," + details.split("pc=\"")[1].split("\"")[0];
                application.userDataPreference.edit().putString("name", name).commit();
                application.userDataPreference.edit().putString("gender", gender).commit();
                application.userDataPreference.edit().putString("address", address).commit();
                application.userDataPreference.edit().putString("aadharNum", aadharNum).commit();
                   Log.d("UID", application.userDataPreference.getString("aadharNum", null));
                    Log.d("Gender", application.userDataPreference.getString("gender",null));
                    Log.d("name", application.userDataPreference.getString("name",null));
                    Log.d("address", application.userDataPreference.getString("address", null));
                    Intent i = new Intent(getApplicationContext(),manualRegistration.class);
                    i.putExtra("fromAadhar", true);
                    startActivity(i);
                    //  Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
