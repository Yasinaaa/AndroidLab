package ru.itis.lectures.githubmvp.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Path;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.content.Commit;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.repository.CommitsRepository;
import ru.itis.lectures.githubmvp.rx.RxUtils;
import ru.itis.lectures.githubmvp.testutils.TestGithubService;
import ru.itis.lectures.githubmvp.testutils.TestProviderImpl;
import ru.itis.lectures.githubmvp.view.CommitsView;
import rx.Observable;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by yasina on 12.04.16.
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class CommitsPresenterTest {

        private CommitsView mView;
        private CommitsPresenter mPresenter;

        static {
            RxUtils.setupTestSchedulers();
        }

        @Before
        public void setUp() throws Exception {
            Context context = RxUtils.rxContext();
            LoaderManager loaderManager = RxUtils.rxLoaderManager();

            mView = mock(CommitsView.class);
            doNothing().when(mView).showLoading();
            doNothing().when(mView).hideLoading();
            doNothing().when(mView).showError();
            doNothing().when(mView).showCommitsList(anyList());
            doNothing().when(mView).showCommits(any(Commit.class));

            CommitsRepository repository = new TestRepositoryImpl(generateRepositories(15));
            mPresenter = new CommitsPresenter(context, loaderManager, mView, repository, "one","two");
        }

        @Test
        public void testCreated() throws Exception {
            assertNotNull(mPresenter);
        }


        @Test
        public void testSuccessResponse() throws Exception {
            ApiFactory.setProvider(new TestProviderImpl(new SuccessGithubService(generateRepositories(12))));

            mPresenter.dispatchCreated();

            verify(mView).showLoading();
            verify(mView).hideLoading();
            //verify(mView).showCommitsList(anyList());
           verify(mView, times(2)).showCommitsList(anyList());
        }

        @Test
        public void testErrorResponse() throws Exception {
            ApiFactory.setProvider(new TestProviderImpl(new FailGithubService()));

            mPresenter.dispatchCreated();

            verify(mView).showLoading();
            verify(mView).hideLoading();
            verify(mView).showError();
        }

        @NonNull
        private List<Commit> generateRepositories(int count) {
            List<Commit> repositories = new ArrayList<Commit>(count);
            for (int i = 1; i <= count; i++) {
                Commit commit = new Commit("fff" + i,"gg" + i,"rr" + i);;
                repositories.add(commit);
            }
            return repositories;
        }

        private class SuccessGithubService extends TestGithubService {

            private final List<Commit> mRepositories;

            public SuccessGithubService(@NonNull List<Commit> repositories) {
                mRepositories = repositories;
            }

            @Override
            public Observable<List<Commit>> commits(@Path("user") String user, @Path("repo") String repo) {
                return Observable.just(mRepositories);
            }
        }

        private class FailGithubService extends TestGithubService {

            @Override
            public Observable<List<Commit>> commits(@Path("user") String user, @Path("repo") String repo) {
                return Observable.error(new IOException());
            }
        }

        private class TestRepositoryImpl implements CommitsRepository {

            private final List<Commit> mRepositories;

            public TestRepositoryImpl(@NonNull List<Commit> repositories) {
                mRepositories = repositories;
            }


            @NonNull
            @Override
            public Observable<List<Commit>> cachedCommits() {
                return Observable.just(mRepositories);
            }

            @NonNull
            @Override
            public Observable<List<Commit>> saveCommits(List<Commit> commits) {
                return Observable.just(commits);
            }


        }

}
