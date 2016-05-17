package ru.itis.lectures.githubmvp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.content.Benefit;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.presenter.WalkthroughPresenter;
import ru.itis.lectures.githubmvp.view.WalkView;
import ru.itis.lectures.githubmvp.widget.PageChangeViewPager;
import ru.itis.lectures.githubmvp.widget.WalkthroughAdapter;

import static ru.itis.lectures.githubmvp.utils.Views.findById;

/**
 * @author Artur Vasilov
 */
public class WalkthroughActivity extends AppCompatActivity implements View.OnClickListener,
        PageChangeViewPager.PagerStateListener, WalkView {

    private WalkthroughPresenter presenter;
    private PageChangeViewPager mPager;
    private Button mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        mPager = findById(this, R.id.pager);
        mActionButton = findById(this, R.id.btn_walkthrough);
        presenter = new WalkthroughPresenter(this);
        List<Benefit> benefits = new ArrayList<Benefit>() {
            {
                add(Benefit.WORK_TOGETHER);
                add(Benefit.CODE_HISTORY);
                add(Benefit.PUBLISH_SOURCE);
            }
        };
        mPager.setAdapter(new WalkthroughAdapter(getFragmentManager(), benefits));
        mActionButton.setText(R.string.next_uppercase);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionButton.setOnClickListener(this);
        mPager.setOnPageChangedListener(this);
    }

    @Override
    protected void onPause() {
        mActionButton.setOnClickListener(null);
        mPager.setOnPageChangedListener(null);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_walkthrough) {
            presenter.nextBenefit();
        }
    }

    @Override
    public void onPageScrollStarted(int currentPage) {
        // Do nothing
    }

    @Override
    public void onPageChanged(int selectedPage, boolean fromUser) {
        if (fromUser) {
            presenter.selectPage(selectedPage);
        }
    }


    public void showBenefit(int index, boolean isLastBenefit) {
        mActionButton.setText(isLastBenefit ? R.string.finish_uppercase : R.string.next_uppercase);
        if (index == mPager.getCurrentItem()) {
            return;
        }
        mPager.smoothScrollNext(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    public void finishWalkthrough() {
        Settings.saveWalkthroughPassed(this);
        LogInActivity.start(this);
        finish();
    }

}

