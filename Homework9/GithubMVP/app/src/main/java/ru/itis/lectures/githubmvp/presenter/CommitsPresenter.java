package ru.itis.lectures.githubmvp.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.repository.CommitsRepository;
import ru.itis.lectures.githubmvp.rx.RxLoader;
import ru.itis.lectures.githubmvp.view.CommitsView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yasina on 12.04.16.
 */
public class CommitsPresenter {

    private final Context mContext;
    private final LoaderManager mLm;

    private final CommitsView mView;
    private final CommitsRepository mRepository;

    private String user, repo;

    public CommitsPresenter(@NonNull Context context, @NonNull LoaderManager lm,
                                 @NonNull CommitsView view,
                                 @NonNull CommitsRepository repository,
                                @NonNull String user, @NonNull String repo) {
        mContext = context;
        mLm = lm;
        mView = view;
        mRepository = repository;
    }

    public void dispatchCreated() {
        RxLoader.create(mContext, mLm, R.id.commits_loader,
                        mRepository.cachedCommits()
                        .subscribeOn(Schedulers.io())
                        .doOnTerminate(this::refresh)
                        .observeOn(AndroidSchedulers.mainThread()))
                .init(mView::showCommitsList, throwable -> mView.showError());
    }




    public void refresh() {
        RxLoader.create(mContext, mLm, R.id.commits_loader,
                ApiFactory.getProvider().provideGithubService()
                        .commits(user,repo)
                        .flatMap(mRepository::saveCommits)
                        .doOnSubscribe(mView::showLoading)
                        .doOnTerminate(mView::hideLoading)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()))
                .init(mView::showCommitsList, throwable -> mView.showError());
    }

}


