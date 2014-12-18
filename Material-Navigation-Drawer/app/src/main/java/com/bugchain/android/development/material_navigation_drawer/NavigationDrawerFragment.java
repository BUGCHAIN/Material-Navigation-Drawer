package com.bugchain.android.development.material_navigation_drawer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BUG CHAIN on 18/12/2557.
 */
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREFERENCES_FILE = "my_app_settings";

    private NavigationDrawerCallbacks mCallbacks;
    private RecyclerView mDrawerList;
    private View mFragmentContentView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private boolean mUserLearnedDrawer;
    private boolean mFromSaveInstanceState;
    private int mCurrentSelectPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        mDrawerList = (RecyclerView) rootView.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);

        final List<NavigationItem> navigationItems = getMenu();
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationItems);
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
        selectItem(mCurrentSelectPosition);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(getActivity(),PREF_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState != null){
            mCurrentSelectPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSaveInstanceState = true;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallbacks = (NavigationDrawerCallbacks)activity;
        }catch (ClassCastException e){
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks");
        }
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle(){
        return mActionBarDrawerToggle;
    }

    public void setmActionBarDrawerToggle(ActionBarDrawerToggle actionBarDrawerToggle){
        this.mActionBarDrawerToggle = actionBarDrawerToggle;
    }

    public void setup(int fragmentId,DrawerLayout drawerLayout,Toolbar toolbar){
        mFragmentContentView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveSharedSetting(getActivity(),PREF_USER_LEARNED_DRAWER,"true");
                }
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && !mFromSaveInstanceState)
            mDrawerLayout.openDrawer(mFragmentContentView);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    public void openDrawer(){
        mDrawerLayout.openDrawer(mFragmentContentView);
    }

    public void closeDrawer(){
        mDrawerLayout.closeDrawer(mFragmentContentView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    public List<NavigationItem> getMenu(){
        List<NavigationItem> items = new ArrayList<NavigationItem>();
        items.add(new NavigationItem("Item 1",getResources().getDrawable(R.drawable.ic_menu_check)));
        items.add(new NavigationItem("Item 2",getResources().getDrawable(R.drawable.ic_menu_check)));
        items.add(new NavigationItem("Item 3",getResources().getDrawable(R.drawable.ic_menu_check)));
        return items;
    }

    void selectItem(int position){
        mCurrentSelectPosition = position;
        if(mDrawerLayout != null){
            mDrawerLayout.closeDrawer(mFragmentContentView);
        }
        if(mCallbacks != null){
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        ((NavigationDrawerAdapter)mDrawerList.getAdapter()).selectionPosition(position);
    }

    public boolean isDrawerOpen(){
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContentView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public DrawerLayout getDrawerLayout(){
        return mDrawerLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION,mCurrentSelectPosition);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    public void setDrawerLayout(DrawerLayout drawerLayout){
        mDrawerLayout = drawerLayout;
    }

    public static void saveSharedSetting(Context context,String settingName,String settingVales){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(settingName,settingVales);
        editor.commit();
    }
    public static String readSharedSetting(Context context,String settingName,String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

}
