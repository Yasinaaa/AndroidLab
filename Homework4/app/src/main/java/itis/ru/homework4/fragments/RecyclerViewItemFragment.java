package itis.ru.homework4.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import itis.ru.homework4.R;
import itis.ru.homework4.adapter.RecyclerViewAdapter;
import itis.ru.homework4.model.Item;

public class RecyclerViewItemFragment extends DialogFragment {

    public static final String TAG = "RecyclerViewItemFragment";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private OnFragmentInteractionListener mListener;

    public static void navigate() {

      //  ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        //ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static RecyclerViewItemFragment newInstance() {
        RecyclerViewItemFragment fragment = new RecyclerViewItemFragment();
        return fragment;
    }

    private RecyclerViewItemFragment(){

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppBarOverlay);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initActivityTransitions();
        View rootView = inflater.inflate(R.layout.dialog_detail, container, false);
       // getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ViewCompat.setTransitionName(rootView.findViewById(R.id.app_bar_layout), "http://lorempixel.com/500/500/animals/3");
        //supportPostponeEnterTransition();

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Lala");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final ImageView image = (ImageView) rootView.findViewById(R.id.image);

        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
        image.setImageBitmap(myBitmap);
        Log.d(TAG, "J");
        Palette.from(myBitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                applyPalette(palette);
            }
        });

        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText("Bla bla");

        return rootView;
    }

  /*  @Override public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }*/

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getActivity().getWindow().setEnterTransition(transition);
            getActivity().getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {

        //int primaryDark = getResources().getColor(R.color.cardview_dark_background);
        //int primary = getResources().getColor(R.color.colorAccent);
        int defaultCo = 0x000000;
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(defaultCo));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(defaultCo));

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
}
