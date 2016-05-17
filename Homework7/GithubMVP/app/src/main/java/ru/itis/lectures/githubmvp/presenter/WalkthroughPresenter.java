package ru.itis.lectures.githubmvp.presenter;

import android.support.annotation.NonNull;

import ru.itis.lectures.githubmvp.view.WalkView;

/**
 * Created by yasina on 29.03.2016.
 */
public class WalkthroughPresenter {

    private static final int BENEFITS_COUNT = 3;
    private WalkView walkView;
    private int mCurrentIndex = 0;

    public WalkthroughPresenter(@NonNull WalkView walkView) {
        this.walkView = walkView;
    }

    public void nextBenefit() {
        if (isLastBenefit()) {
            walkView.finishWalkthrough();
        } else {
            mCurrentIndex++;
            walkView.showBenefit(mCurrentIndex, isLastBenefit());
        }
    }

    public void selectPage(int selectedPage) {
        mCurrentIndex = selectedPage;
        walkView.showBenefit(mCurrentIndex, isLastBenefit());
    }

    public boolean isLastBenefit() {
        return mCurrentIndex == BENEFITS_COUNT - 1;
    }

}
