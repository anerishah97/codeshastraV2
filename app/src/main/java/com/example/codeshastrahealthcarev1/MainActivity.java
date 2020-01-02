package com.example.codeshastrahealthcarev1;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.library.BottomBarHolderActivity;
import me.riddhimanadib.library.NavigationPage;

public class MainActivity extends BottomBarHolderActivity implements recordsFragment.OnFragmentInteractionListener, searchFragment.OnFragmentInteractionListener, profileFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationPage page1 = new NavigationPage("Records", ContextCompat.getDrawable(this, R.drawable.records), recordsFragment.newInstance());
        NavigationPage page2 = new NavigationPage("Search", ContextCompat.getDrawable(this, R.drawable.ic_search_black_24dp), searchFragment.newInstance());
        NavigationPage page3 = new NavigationPage("Add ", ContextCompat.getDrawable(this, R.drawable.records), searchFragment.newInstance());
        NavigationPage page4 = new NavigationPage("Me", ContextCompat.getDrawable(this, R.drawable.user_profile), profileFragment.newInstance());

        // add them in a list
        List<NavigationPage> navigationPages = new ArrayList<>();
        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
        navigationPages.add(page4);

        // pass them to super method
        super.setupBottomBarHolderActivity(navigationPages);

    }

}
