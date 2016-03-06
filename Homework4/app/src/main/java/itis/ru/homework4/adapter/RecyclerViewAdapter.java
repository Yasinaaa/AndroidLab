package itis.ru.homework4.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import itis.ru.homework4.R;
import itis.ru.homework4.adapter.viewholder.RecyclerHeaderViewHolder;
import itis.ru.homework4.adapter.viewholder.RecyclerItemViewHolder;
import itis.ru.homework4.model.Item;


/**
 * Created by yasina on 06.03.16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final String TAG = "RecyclerViewAdapter";
    private List<Item> mItemsList;

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;


    public RecyclerViewAdapter(List<Item> list) {
        this.mItemsList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        if (i == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, viewGroup, false);
            return RecyclerItemViewHolder.newInstance(view);
        } else if (i == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_header, viewGroup, false);
            return new RecyclerHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + i + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            final Item v = mItemsList.get(position - 1); // header
            holder.setTitle(v.getTitle());
            holder.setText(v.getText());

            holder.getV().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, v.getTitle() + " is clicked");
                }
            });
        }
    }

    public int getBasicItemCount() {
        return mItemsList == null ? 0 : mItemsList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1; // header
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


}

