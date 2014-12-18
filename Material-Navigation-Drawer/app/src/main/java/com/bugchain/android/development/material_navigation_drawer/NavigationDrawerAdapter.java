package com.bugchain.android.development.material_navigation_drawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BUG CHAIN on 18/12/2557.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>{

    private List<NavigationItem> navigationItems;
    private NavigationDrawerCallbacks navigationDrawerCallbacks;
    private int mSelecedPosition;
    private int mTouchPosition = -1;

    public NavigationDrawerAdapter(List<NavigationItem> navigationItems){
        this.navigationItems = navigationItems;
    }

    public  NavigationDrawerCallbacks getNavigationDrawerCallbacks(){
        return navigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks){
        this.navigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder,final int position) {
        viewHolder.textItem.setText(navigationItems.get(position).getTitle());
        viewHolder.textItem.setCompoundDrawablesWithIntrinsicBounds(navigationItems.get(position).getDrawable(),null,null,null);
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        touchPosition(position);
                        return false;
                    case MotionEvent.ACTION_CANCEL:
                        touchPosition(-1);
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_UP:
                        touchPosition(-1);
                        return false;
                }
                return true;
            }
        });
    }

    private void touchPosition(int position){
        int lastPosition = mTouchPosition;
        mTouchPosition = position;
        if(lastPosition >=0)
            notifyItemChanged(lastPosition);
        if (position>=0)
            notifyItemChanged(position);
    }

    public void selectionPosition(int position){
        int lastPosition = mSelecedPosition;
        mSelecedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return navigationItems != null ? navigationItems.size():0;
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textItem;

        public ViewHolder(View itemView){
            super(itemView);
            textItem = (TextView)itemView.findViewById(R.id.text_item_name);
        }
    }

}
