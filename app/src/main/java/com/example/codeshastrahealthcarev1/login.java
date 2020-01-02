package com.example.codeshastrahealthcarev1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login extends AppCompatActivity {

    ImageButton calendar, scan_login;
    MaterialEditText aadhar, dob;
    TextView login, register;
    DateFormat dateFormat;
    healthcare application;
    String aadharString, serverresponse;
    public static String formattedDate;
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        aadhar = findViewById(R.id.aadharNumberLogin);
        dob = findViewById(R.id.birthdate_login);
        calendar = findViewById(R.id.choose_date);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        scan_login = findViewById(R.id.scan_login);
        application = new healthcare();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, register.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoggedIn();
            }
        });

        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Scan the QR code on your Aadhar card");
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        scan_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize a new date picker dialog fragment
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");
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
                String aadhar_num = details.split("uid=\"")[1].split("\"")[0];
                aadhar.setText(aadhar_num);
                //  Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void checkLoggedIn()
    {
        String dobString = "";
        aadharString = aadhar.getText().toString();
        dobString = dob.getText().toString();
        if(aadharString.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter your Aadhar number",Toast.LENGTH_SHORT).show();

        } else if (dobString.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please enter your DOB",Toast.LENGTH_SHORT).show();

        }
        if(!aadharString.isEmpty() && !dobString.isEmpty())
        {
            //call a service to fetch data and verify
           fetchData();

        }
    }

    public void fetchData()
    {
        String url = application.baseUrl + "/FetchPatientDetails/?aadhaarid=";
        url = url +  aadharString ;
        Log.d("url for get", url);
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
                   try {
                       JSONObject jsonObject = new JSONObject(serverresponse);
                       JSONObject data = jsonObject.getJSONObject("data");
                       String dob1 = data.getString("dob");
                       Log.d("aaaaaaaaaaaaaaaaaaaa", dob1);
                       //Log.d("formatted date service", formattedDate);
                        formattedDate = dob.getText().toString();
                       if(dob1.equals(formattedDate))
                        {
                            Log.d("Successful ", "yayy");

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                       {
                           Toast.makeText(getApplicationContext(),"Please enter valid details", Toast.LENGTH_SHORT).show();
                       }

                   }
                   catch (JSONException e)
                   {
                       e.printStackTrace();
                   }
                    //startActivity(new Intent(manualRegistration.this, MainActivity.class));

                }
                else
                {
                    Log.d("aa",serverresponse);
                }
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            /*
                Initialize a new DatePickerDialog

                DatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callBack,
                    int year, int monthOfYear, int dayOfMonth)
             */
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date
            MaterialEditText dateOfBirth = getActivity().findViewById(R.id.birthdate_login);
            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style and locale
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            formattedDate = df.format(chosenDate);
            Log.d("formatted date dataset", formattedDate);
            // Display the chosen date to app interface
            dateOfBirth.setText(formattedDate);
        }
    }
}
