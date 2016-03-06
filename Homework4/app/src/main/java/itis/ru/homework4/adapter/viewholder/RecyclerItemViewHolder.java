package itis.ru.homework4.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import itis.ru.homework4.R;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private TextView tvText;
    private View v;

    public RecyclerItemViewHolder(final View parent, TextView tvTitle, TextView tvText) {
        super(parent);
        this.v = parent;
        this.tvTitle = tvTitle;
        this.tvText = tvText;
    }

    public static RecyclerItemViewHolder newInstance(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_titile_recycler_view);
        TextView tvText = (TextView) view.findViewById(R.id.tv_text_recycler_view);
        return new RecyclerItemViewHolder(view, tvTitle, tvText);
    }

    public View getV() {
        return v;
    }

    public void setText(CharSequence text) {
        tvText.setText(text);
    }
    public void setTitle(CharSequence text) {
        tvTitle.setText(text);
    }

}
