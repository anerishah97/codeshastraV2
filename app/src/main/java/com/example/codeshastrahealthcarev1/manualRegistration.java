package com.example.codeshastrahealthcarev1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

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

public class manualRegistration extends AppCompatActivity {

    TextView next_register, final_register;
    MaterialEditText name, address, aadharNum,dob, contact;
    Boolean fromAadhar;
    String serverresponse;
    LinearLayout step1, step2;
    ImageButton choose_date;
    public healthcare application;
    public String name_s, aadhar_s, address_s, date_s, gender_s, contact_s;

    EditText height, weight, blood_group,emergency_name, emergeny_contact, chronic_med, ongoing_med, allergies, medical_history;
    public String height_s, weight_s, blood_group_s,emergency_name_s, emergeny_contact_s, chronic_med_s, ongoing_med_s, allergies_s, medical_history_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_registration);

        fromAadhar = getIntent().getExtras().getBoolean("fromAadhar");

        dob = findViewById(R.id.birthdate_reg);
        next_register = findViewById(R.id.next_register);
        choose_date = findViewById(R.id.choose_date_reg);
        name = findViewById(R.id.name_reg);
        aadharNum = findViewById(R.id.aadhar_num_reg);
        address = findViewById(R.id.address_reg);
        contact = findViewById(R.id.contact_reg);

        height = findViewById(R.id.height_reg);
        weight = findViewById(R.id.weight_reg);
        blood_group = findViewById(R.id.blood_group_reg);
        emergency_name = findViewById(R.id.emergency_name_reg);
        emergeny_contact = findViewById(R.id.emergency_contact_reg);
        chronic_med = findViewById(R.id.chronic_medical_condition_reg);
        ongoing_med = findViewById(R.id.ongoing_medication_reg);
        allergies = findViewById(R.id.allergy_reg);
        medical_history = findViewById(R.id.family_history_reg);

        final_register = findViewById(R.id.final_register);

        chronic_med_s = "";
        ongoing_med_s="";
        allergies_s = "";
        medical_history_s = "";

        step1 = findViewById(R.id.step1_register);
        step2 = findViewById(R.id.step2_register);
        application = new healthcare();


        if(fromAadhar)
        {
            application.userDataPreference = PreferenceManager.getDefaultSharedPreferences(this);
             aadhar_s = application.userDataPreference.getString("aadharNum", null);
            aadharNum.setText(aadhar_s);
            Log.d("aadhar", aadhar_s);

          gender_s = application.userDataPreference.getString("gender",null);

            name_s = application.userDataPreference.getString("name",null);
           name.setText(name_s);

           address_s = application.userDataPreference.getString("address", null);
          address.setText(address_s);


          aadharNum.setTextColor(getResources().getColor(R.color.grey));
          name.setTextColor(getResources().getColor(R.color.grey));
          address.setTextColor(getResources().getColor(R.color.grey));

          aadharNum.setEnabled(false);
          name.setEnabled(false);
       //   address.setEnabled(false);
        }

        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize a new date picker dialog fragment
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        final_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                height_s = height.getText().toString();
                weight_s = weight.getText().toString();
                blood_group_s = blood_group.getText().toString();
                emergency_name_s = emergency_name.getText().toString();
                emergeny_contact_s = emergeny_contact.getText().toString();
                if(height_s.isEmpty() || weight_s.isEmpty() || blood_group_s.isEmpty() || emergency_name_s.isEmpty() || emergeny_contact_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter the essential details/ emergency contacts",Toast.LENGTH_SHORT).show();
                }
                chronic_med_s = chronic_med.getText().toString();
                if(chronic_med_s.isEmpty())
                {
                    chronic_med_s = "N/A";
                }

                ongoing_med_s= ongoing_med.getText().toString();
                if(ongoing_med_s.isEmpty())
                {
                    ongoing_med_s = "N/A";
                }

                allergies_s = allergies.getText().toString();
                if(allergies_s.isEmpty())
                {
                    allergies_s = "N/A";
                }

                medical_history_s = medical_history.getText().toString();

                if(medical_history_s.isEmpty())
                {
                    medical_history_s = "N/A";
                }

                if(!height_s.isEmpty() && !weight_s.isEmpty() && !blood_group_s.isEmpty() && !emergency_name_s.isEmpty() && !emergeny_contact_s.isEmpty())
                {
                    application.userDataPreference.edit().putString("height", height_s).commit();
                    application.userDataPreference.edit().putString("weight", weight_s).commit();
                    application.userDataPreference.edit().putString("bloodgroup", blood_group_s).commit();
                    application.userDataPreference.edit().putString("allergies", allergies_s).commit();
                    application.userDataPreference.edit().putString("chronicillness", chronic_med_s ).commit();
                    application.userDataPreference.edit().putString("ongoingmedication",ongoing_med_s ).commit();
                    application.userDataPreference.edit().putString("familyhistory", medical_history_s).commit();

                    //Toast.makeText(getApplicationContext(),"Enter the essential details/ emergency contacts",Toast.LENGTH_SHORT).show();
                    createNewUser();

                }
            }
        });


        next_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean test = true;
                date_s = dob.getText().toString();
                contact_s = contact.getText().toString();
                if(!fromAadhar) {
                    aadhar_s = aadharNum.getText().toString();
                    address_s = address.getText().toString();
                    name_s = name.getText().toString();
                    if (aadhar_s.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter your Aadhar number", Toast.LENGTH_SHORT).show();
                    }
                    else if(name_s.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    }
                    else if (address_s.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter your address", Toast.LENGTH_SHORT).show();
                    }

                }

                if(contact_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter your Contact number", Toast.LENGTH_SHORT).show();

                }

                else if(date_s.isEmpty())
                {
                    test = false;
                    Toast.makeText(getApplicationContext(), "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                }
                else{
                    test = true;
                    Log.d("dob", date_s);
                    application.userDataPreference.edit().putString("dob", date_s).commit();
                    application.userDataPreference.edit().putString("contact", contact_s).commit();
                }



                if(test)
                if(!date_s.isEmpty()) {
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                }//    createNewUser();
                 //   startActivity(new Intent(getApplicationContext(), MainActivity.class));
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



    public void createNewUser(){
        String url = application.baseUrl + "/PatientRegister/";
      //  Log.d("aaaaaaaaaa",url);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("aadhaarid",aadhar_s)
                .add("name", name_s)
                .add("dob", date_s)
                .add("address", address_s)
                .add("gender", gender_s)
                .add("contact", contact_s)
                .add("height", height_s)
                .add("weight", weight_s)
                .add("bloodgroup", blood_group_s)
                .add("medicalconditions", ongoing_med_s)
                .add("alergiesandreactions", allergies_s)
                .add("ongoingmedications", ongoing_med_s)
                .add("familyhistory", medical_history_s)
                .add("emergencycontact", emergeny_contact_s)
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
                    startActivity(new Intent(manualRegistration.this, MainActivity.class));
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
            MaterialEditText dateOfBirth = getActivity().findViewById(R.id.birthdate_reg);
            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style and locale
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(chosenDate);
            manualRegistration ob = new manualRegistration();
            ob.date_s = formattedDate;
            // Display the chosen date to app interface

            dateOfBirth.setText(formattedDate);
        }
    }
}
