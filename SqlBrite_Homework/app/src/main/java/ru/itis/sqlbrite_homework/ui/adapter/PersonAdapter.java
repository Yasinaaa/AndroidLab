package ru.itis.sqlbrite_homework.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import ru.itis.sqlbrite_homework.R;
import ru.itis.sqlbrite_homework.data.model.Person;

/**
 * Created by yasina on 17.05.16.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder>{

    public static final String TAG = "PersonAdapter";
    private List<Person> mItemList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private View view;

    public PersonAdapter(List<Person> listDictionaries) {
        this.mItemList = listDictionaries;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        view = LayoutInflater.from(mContext).inflate(R.layout.item_person, parent, false);

        return PersonViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, final int position) {

        final Person item = mItemList.get(position);
        holder.mNameText.setText(item.getName());

        holder.itemView.setTag(item);

        holder.mRemoveImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onRemoveClick(item, position);
                }
        });

        holder.mUpdateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onUpdateClick(item);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public interface OnItemClickListener {

        void onRemoveClick(Person viewModel, int position);
        void onUpdateClick(Person viewModel);

    }

}
