package itis.ru.homework4.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itis.ru.homework4.R;
import itis.ru.homework4.adapter.viewholder.RecyclerItemViewHolder;
import itis.ru.homework4.fragments.RecyclerViewItemFragment;
import itis.ru.homework4.model.Item;


/**
 * Created by yasina on 06.03.16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerItemViewHolder> implements View.OnClickListener{

    public static final String TAG = "RecyclerViewAdapter";
    private List<Item> mItemsList;
    private OnItemClickListener onItemClickListener;

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    private View v;

    public RecyclerViewAdapter(Activity a ,List<Item> list) {
        this.mItemsList = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        v = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, viewGroup, false);
        v.setOnClickListener(this);
        return RecyclerItemViewHolder.newInstance(v);
       }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerItemViewHolder holder =  viewHolder;
            final Item v = mItemsList.get(position - 1); // header
            holder.setTitle(v.getTitle());
            holder.setText(v.getText());
            holder.itemView.setTag(v);
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

    @Override public void onClick(final View v) {
        onItemClickListener.onItemClick((Item) v.getTag());
    }

    public interface OnItemClickListener {
        void onItemClick(Item viewModel);
    }

}

