package itis.ru.homework4.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import itis.ru.homework4.R;
import itis.ru.homework4.activities.MainActivity;
import itis.ru.homework4.adapter.RecyclerViewAdapter;
import itis.ru.homework4.listener.HidingScrollListener;
import itis.ru.homework4.model.Item;

/**
 * Created by yasina on 06.03.16.
 */
public class MainFragment extends Fragment {

    private final String TAG = "MainFragment";
    private View view;
    private Context mContext;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    private class ViewHolder{
        FloatingActionButton fab;
        RecyclerView recyclerView;
        Toolbar toolbar;
        ActionBarDrawerToggle toggle;
    }

    private ViewHolder vh;

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, true);
        mContext = getActivity().getBaseContext();
        setHasOptionsMenu(true);
        vh = new ViewHolder();
        initView();
        setAdapter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }



    private void initView(){

        vh.recyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        vh.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        vh.recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        vh.fab = (FloatingActionButton) view.findViewById(R.id.fab);
        vh.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        vh.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        vh.toolbar.setTitle(getResources().getString(R.string.navigator_drawer_item_main));
        ((AppCompatActivity)getActivity()).setSupportActionBar(vh.toolbar);

        vh.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       vh.toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, vh.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.setDrawerListener(vh.toggle);
        vh.toggle.syncState();

    }

    private void setAdapter(){

        // TODO: change list
        List<Item> mItemsList = new ArrayList<Item>();
        mItemsList.add(new Item("Item1", "It was beautiful day!"));
        mItemsList.add(new Item("Item2", "It was beautiful day!"));
        mItemsList.add(new Item("Item3", "It was beautiful day!"));
        mItemsList.add(new Item("Item4", "It was beautiful day!"));
        mItemsList.add(new Item("Item5", "It was beautiful day!"));
        mItemsList.add(new Item("Item6", "It was beautiful day!"));
        mItemsList.add(new Item("Item7", "It was beautiful day!"));
        mItemsList.add(new Item("Item8", "It was beautiful day!"));
        mItemsList.add(new Item("Item9", "It was beautiful day!"));

        mRecyclerViewAdapter = new RecyclerViewAdapter(mItemsList);
        vh.recyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void hideViews() {
        vh.toolbar.animate().translationY(-vh.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

      /*  CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) vh.fab.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        vh.fab.animate().translationY(vh.fab.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();*/
    }

    private void showViews() {
        vh.toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
       // vh.fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
}
