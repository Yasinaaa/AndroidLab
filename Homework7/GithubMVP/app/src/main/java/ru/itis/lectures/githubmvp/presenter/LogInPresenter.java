package ru.itis.lectures.githubmvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.content.auth.AuthorizationUtils;
import ru.itis.lectures.githubmvp.utils.TextUtils;
import ru.itis.lectures.githubmvp.view.LogInView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class LogInPresenter {

    private final LogInView mView;
    private final Context mContext;

    public LogInPresenter(@NonNull LogInView view, Context context) {
        mView = view;
        mContext = context;
    }

    public void checkIfAuthorized() {
        String token = Settings.getToken(mContext);
        if (!TextUtils.isEmpty(token)) {
            mView.openRepositoriesScreen();
        }
    }

    public void tryLogIn(@NonNull String login, @NonNull String password) {
        if (TextUtils.isEmpty(login)) {
            mView.showLoginError();
        } else if (TextUtils.isEmpty(password)) {
            mView.showPasswordError();
        } else {
            String authorizationString = AuthorizationUtils.createAuthorizationString(login, password);
            ApiFactory.getProvider().provideGithubService()
                    .authorize(authorizationString, AuthorizationUtils.createAuthorizationParam())
                    .flatMap(authorization -> {
                        Settings.putToken(mContext, authorization.getToken());
                        return Observable.just(authorization);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(authorization -> {
                        mView.openRepositoriesScreen();
                    }, throwable -> {
                        mView.showLoginError();
                    });
        }
    }
}
