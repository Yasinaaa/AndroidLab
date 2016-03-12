package itis.ru.homework4.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import itis.ru.homework4.R;
import itis.ru.homework4.activities.MainActivity;
import itis.ru.homework4.adapter.RecyclerViewAdapter;
import itis.ru.homework4.model.Item;

/**
 * Created by yasina on 06.03.16.
 */
public class MainFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener{


    private final String TAG = "MainFragment";
    private View view;
    private Context mContext;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private OnFragmentInteractionListener mListener;


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

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    private MainFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = getActivity().getBaseContext();
        setHasOptionsMenu(true);
        vh = new ViewHolder();
        initView();
        setAdapter();
        return view;
    }


    private void initView(){

        vh.recyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        vh.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        vh.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        vh.toolbar.setTitle(getResources().getString(R.string.navigator_drawer_item_main));
        ((AppCompatActivity)getActivity()).setSupportActionBar(vh.toolbar);

        vh.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vh.fab = (FloatingActionButton) view.findViewById(R.id.fab);
        vh.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello Snackbar", Snackbar.LENGTH_LONG).show();
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

        mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mItemsList);
        mRecyclerViewAdapter.setOnItemClickListener(this);
        vh.recyclerView.setAdapter(mRecyclerViewAdapter);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemClick(Item v) {
        Log.d(TAG, v.getTitle() + " is clicked");
        FragmentManager fm = getActivity().getFragmentManager();
        DialogFragment dialogFragment = RecyclerViewItemFragment.newInstance();
        dialogFragment.show(fm, "Sample Fragment");
    }


}
