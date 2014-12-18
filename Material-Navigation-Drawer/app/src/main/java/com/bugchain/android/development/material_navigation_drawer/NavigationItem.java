package com.bugchain.android.development.material_navigation_drawer;

import android.graphics.drawable.Drawable;

/**
 * Created by BUG CHAIN on 18/12/2557.
 */
public class NavigationItem {

    private String title;
    private Drawable drawable;

    public NavigationItem(String title,Drawable drawable){
        this.title = title;
        this.drawable =drawable;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public Drawable getDrawable(){
        return drawable;
    }
    public void setDrawable(Drawable drawable){
       this.drawable = drawable;
    }
}
