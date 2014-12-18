package com.bugchain.android.development.material_navigation_drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;



public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks{

    private Toolbar mToolbar;
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

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer,(DrawerLayout)findViewById(R.id.drawer),mToolbar);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Toast.makeText(getApplicationContext(),"Menu item selected --> " + position,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        if(mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}
