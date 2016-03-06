package itis.ru.homework4.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import itis.ru.homework4.R;

/**
 * Created by yasina on 06.03.16.
 */
public class TabsFragment extends Fragment {

    private final String TAG = "TabsFragment";
    private View view;

    private class ViewHolder{
        RecyclerView rvComplaintList;
    }

    private ViewHolder vh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabs, container, true);
        Log.d(TAG, "i'm in tab fragment");
        setHasOptionsMenu(true);
        vh = new ViewHolder();
        initView();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView(){

    }
}
