package ru.itis.sqlbrite_homework.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.itis.sqlbrite_homework.R;
import ru.itis.sqlbrite_homework.data.DataManager;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by yasina on 17.05.16.
 */

public class PersonViewHolder extends RecyclerView.ViewHolder{

    TextView mNameText;
    ImageView mUpdateImageView;
    ImageView mRemoveImageView;

    public PersonViewHolder(View view, TextView mNameText, ImageView mUpdateImageView, ImageView mRemoveImageView) {
        super(view);
        this.mNameText = mNameText;
        this.mRemoveImageView = mRemoveImageView;
        this.mUpdateImageView = mUpdateImageView;
    }

    public static PersonViewHolder newInstance(View parent) {
        ImageView mRemoveImageView = (ImageView) parent.findViewById(R.id.iv_close);
        ImageView mUpdateImageView = (ImageView) parent.findViewById(R.id.iv_pencil);
        TextView mNameText = (TextView) parent.findViewById(R.id.text_name);
        return new PersonViewHolder(parent,mNameText, mUpdateImageView, mRemoveImageView);
    }

}
