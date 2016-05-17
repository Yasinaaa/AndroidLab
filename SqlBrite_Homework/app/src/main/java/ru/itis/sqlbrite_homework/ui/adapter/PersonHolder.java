package ru.itis.sqlbrite_homework.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.InjectView;
import ru.itis.sqlbrite_homework.R;
import ru.itis.sqlbrite_homework.data.DataManager;
import ru.itis.sqlbrite_homework.data.local.DatabaseHelper;
import ru.itis.sqlbrite_homework.data.model.Person;
import ru.itis.sqlbrite_homework.ui.activity.MainActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.item_person)
public class PersonHolder extends ItemViewHolder<Person> {

    private final String TAG = "PersonHolder";
    @ViewId(R.id.text_name)
    TextView mNameText;
    @ViewId(R.id.iv_pencil)
    ImageView mUpdateImageView;
    @ViewId(R.id.iv_close)
    ImageView mRemoveImageView;

    private DataManager mDataManager;
    private Activity mActivity;
    private View view;

    /*public PersonHolder(Activity mActivity, DataManager mDataManager, View view) {
        super(view);
        /*this.view = view;
        this.mDataManager = mDataManager;
        this.mActivity  = mActivity;
    }*/

    public PersonHolder(View view, Person item) {
        super(view, item);
    }

    public PersonHolder(View view) {
        super(view);
    }
    @Override
    public void onSetValues(final Person person, PositionInfo positionInfo) {
        mNameText.setText(person.getName());

        mUpdateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRemoveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseClick(person.getName());
            }
        });
    }

    private void onCloseClick(final String name) {

        Subscription mSubscriptions =
                AppObservable.bindActivity(mActivity,
                mDataManager.removePerson(new Person(name)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "remove item " + name);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "There was a error saving the person " + e);
                    }

                    @Override
                    public void onNext(Person person) { }
                });
    }


}