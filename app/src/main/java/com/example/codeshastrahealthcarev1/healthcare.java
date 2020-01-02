package com.example.codeshastrahealthcarev1;

import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Aneri Shah on 10/02/2018.
 */

public class healthcare extends MultiDexApplication{
    private static healthcare application;
    SharedPreferences loginPreference;
    String baseUrl = "http://172.20.10.4:8000";
    SharedPreferences userDataPreference;
    boolean loggedIn = false;
    public healthcare(){
        baseUrl = "http://172.20.10.4:8000";
    }
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
      //  setUpLoginData();
    }


    public void setUpLoginData()
    {
        application.loginPreference = getSharedPreferences("loginPreference", MODE_PRIVATE);
        if (application.loginPreference.getString("isLoggedIn", "0").equals("1")) {
            application.loggedIn = true;
        }
    }
}
