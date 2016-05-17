package ru.itis.lectures.githubmvp.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import ru.itis.lectures.githubmvp.view.WalkView;

/**
 * Created by yasina on 29.03.16.
 */
public class WalkthroughPresenterTest {

    private WalkView walkView;
    private WalkthroughPresenter walkthroughPresenter;

    @Before
    public void init() {
        walkView = Mockito.mock(WalkView.class);
        Mockito.doNothing().when(walkView).finishWalkthrough();
        Mockito.doNothing().when(walkView).showBenefit(Matchers.anyInt(), Matchers.anyBoolean());
        walkthroughPresenter = new WalkthroughPresenter(walkView);
    }

    @Test
    public void testNextBenefit() throws Exception {
        walkthroughPresenter.nextBenefit();
        Mockito.verify(walkView, Mockito.times(1)).showBenefit(Matchers.anyInt(), Matchers.anyBoolean());
    }

    @Test
    public void testLastBenefit() throws Exception {

        for(int i=0; i<3; i++)
            walkthroughPresenter.nextBenefit();
        
        Mockito.verify(walkView, Mockito.times(2)).showBenefit(Matchers.anyInt(), Matchers.anyBoolean());
        Mockito.verify(walkView, Mockito.times(1)).finishWalkthrough();
    }

    @Test
    public void testSelectAndNextBenefit() throws Exception {
        walkthroughPresenter.selectPage(0);
        walkthroughPresenter.nextBenefit();
        Mockito.verify(walkView,Mockito.times(2)).showBenefit(Matchers.anyInt(), Matchers.anyBoolean());
    }

    @Test
    public void testSelectAndLastBenefit() throws Exception {

        walkthroughPresenter.selectPage(2);
        walkthroughPresenter.nextBenefit();

        Mockito.verify(walkView).showBenefit(Matchers.anyInt(), Matchers.anyBoolean());
        Mockito.verify(walkView).finishWalkthrough();
    }

}
