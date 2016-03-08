package itis.ru.homework4.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import itis.ru.homework4.R;
import itis.ru.homework4.adapter.RecyclerViewAdapter;
import itis.ru.homework4.model.Item;

/**
 * Created by yasina on 07.03.16.
 */
public class TabFragment1 extends Fragment {

    private RecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private View view;
    private Context mContext;
    private String text;

    public TabFragment1(String text){
        this.text = text;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        mContext = getActivity().getBaseContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        setAdapter(text);
        return view;
    }

    private void setAdapter(String text){

        // TODO: change list
        List<Item> mItemsList = new ArrayList<Item>();
        for(int i=0; i<10; i++){
            mItemsList.add(new Item(text, "It was beautiful day!"));
        }
        mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mItemsList);
        recyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
