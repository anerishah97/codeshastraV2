package com.example.codeshastrahealthcarev1;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Adib on 13-Apr-17.
 */

public class profileFragment extends Fragment {
    //private String name_s, date_s, height_s, weight_s, bg_s, allergies_s, familyhistory_s, ongoingmed_s, chronicillness_s;


    private OnFragmentInteractionListener listener;
    public TextView name, dob, weight, bloodgroup, ongoingmed, chronicillness, familyhistory, allergies, heightTV;
    healthcare application;
    public static profileFragment newInstance() {
        return new profileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PatientDetails patientDetails= new PatientDetails();
        View rootview =  inflater.inflate(R.layout.fragment_profile, container, false);
        application = new healthcare();
        name = rootview.findViewById(R.id.profileName);
        dob = rootview.findViewById(R.id.profileDob);
        heightTV = rootview.findViewById(R.id.height_prof);
        weight = rootview.findViewById(R.id.weight_prof);
        bloodgroup = rootview.findViewById(R.id.bloodgroup_prof);
        ongoingmed = rootview.findViewById(R.id.ongoingmedication_prof);
        chronicillness = rootview.findViewById(R.id.chronic_profile);
        familyhistory = rootview.findViewById(R.id.familyhistory_prof);
        allergies = rootview.findViewById(R.id.allergy_prof);


        application.userDataPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        patientDetails.setmPatientName(application.userDataPreference.getString("name",null));
        date_s = application.userDataPreference.getString("dob",null);
        height_s = application.userDataPreference.getString("height",null);
        weight_s = application.userDataPreference.getString("weight",null);
        bg_s = application.userDataPreference.getString("bloodgroup",null);
        allergies_s = application.userDataPreference.getString("allergies",null);
        familyhistory_s = application.userDataPreference.getString("familyhistory",null);
        ongoingmed_s = application.userDataPreference.getString("ongoingmedication",null);
        chronicillness_s = application.userDataPreference.getString("chronicillness",null);


        name.setText(name_s);
        dob.setText(date_s);
        heightTV.setText(height_s);
        weight.setText(weight_s);
        bloodgroup.setText(bg_s);
        ongoingmed.setText(ongoingmed_s);
        familyhistory.setText(familyhistory_s);
        chronicillness.setText(chronicillness_s);
        allergies.setText(allergies_s);
        return rootview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
   //     name.setText("hey");
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

}