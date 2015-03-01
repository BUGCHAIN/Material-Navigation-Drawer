package com.bugchain.android.development.material_navigation_drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks{

    private static Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        mToolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);



        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer,(DrawerLayout)findViewById(R.id.drawer),mToolbar);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = null;

        if(position == 0){
            fragment = new Menu1Fragment();
        }else if(position == 1){
            fragment = new Menu2Fragment();
        }else if(position==2){
            fragment = new Menu3Fragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.animator.accordion_left_in,R.animator.accordion_left_out,
//                        R.animator.accordion_right_in,R.animator.accordion_right_out);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.container,fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public void onBackPressed() {
        if(mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}
