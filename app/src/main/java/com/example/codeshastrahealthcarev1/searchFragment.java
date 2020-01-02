package com.example.codeshastrahealthcarev1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Adib on 13-Apr-17.
 */

public class searchFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    public LinearLayout hospital, doctor, speciality, emergency;
    TextView titlebar;
    EditText searchbar;
    InputMethodManager imm;
    ImageButton imageButton,back;

    public static searchFragment newInstance() {
        return new searchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_search, container, false);
        hospital = rootview.findViewById(R.id.hospital_name_search);
        doctor = rootview.findViewById(R.id.doctor_search);
        speciality = rootview.findViewById(R.id.speciality_search);
        emergency = rootview.findViewById(R.id.emergency_search);
        titlebar = rootview.findViewById(R.id.search_textview);
        imageButton = rootview.findViewById(R.id.search_img_button);
        searchbar = rootview.findViewById(R.id.search_edit_text);
        back = rootview.findViewById(R.id.back_search);

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hos", "aa");
                titlebar.setVisibility(View.GONE);
                searchbar.setVisibility(View.VISIBLE);
                searchbar.setGravity(0);
                back.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.VISIBLE);
                searchbar.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                 imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        back.setVisibility(View.GONE);
                        searchbar.setText("");

                        searchbar.setVisibility(View.GONE);
                        titlebar.setVisibility(View.VISIBLE);
                        imageButton.setVisibility(View.GONE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
                    }
                });

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String searchString = searchbar.getText().toString();
                        Log.d("sea", searchString);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
                        searchHospital(searchString);
                    }
                });
            }
        });


        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hos", "aa");
                titlebar.setVisibility(View.GONE);
                searchbar.setVisibility(View.VISIBLE);
                searchbar.setGravity(0);
                back.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.VISIBLE);
                searchbar.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchbar.setText("");

                        back.setVisibility(View.GONE);
                        searchbar.setVisibility(View.GONE);
                        titlebar.setVisibility(View.VISIBLE);
                        imageButton.setVisibility(View.GONE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
                    }
                });

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String searchString = searchbar.getText().toString();
                        Log.d("sea", searchString);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
                        searchDoctor(searchString);
                    }
                });
            }
        });


        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hos", "aa");
                searchEmergency(" ");
            }
        });

        speciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hos", "aa");
                titlebar.setVisibility(View.GONE);
                searchbar.setVisibility(View.VISIBLE);
                searchbar.setGravity(0);
                back.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.VISIBLE);
                searchbar.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        back.setVisibility(View.GONE);
                        searchbar.setVisibility(View.GONE);
                        titlebar.setVisibility(View.VISIBLE);
                        searchbar.setText("");
                        imageButton.setVisibility(View.GONE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
                    }
                });

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String searchString = searchbar.getText().toString();
                        Log.d("sea", searchString);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
                        searchSpeciality(searchString);
                    }
                });
            }
        });

        return rootview;
    }

    public void searchHospital(String searchString)
    {
        Intent i = new Intent(getActivity(),listOfHospitals.class);
        i.putExtra("searchstring", searchString);
        startActivity(i);
    }

    public void searchSpeciality(String searchString)
    {
        Intent i = new Intent(getActivity(),listOfSpecialists.class);
        i.putExtra("searchstring", searchString);
        startActivity(i);
    }
    public void searchDoctor(String searchString)
    {
        Intent i = new Intent(getActivity(),listOfDoctors.class);
        i.putExtra("searchstring", searchString);
        startActivity(i);
    }
    public void searchEmergency(String searchString)
    {
        Intent i = new Intent(getActivity(),listOfEmergency.class);
        i.putExtra("searchstring", searchString);
        startActivity(i);
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

}