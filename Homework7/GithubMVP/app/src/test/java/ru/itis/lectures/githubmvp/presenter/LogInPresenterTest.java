package ru.itis.lectures.githubmvp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import retrofit2.http.Body;
import retrofit2.http.Header;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.content.auth.Authorization;
import ru.itis.lectures.githubmvp.testutils.RxSchedulers;
import ru.itis.lectures.githubmvp.testutils.TestGithubService;
import ru.itis.lectures.githubmvp.testutils.TestProviderImpl;
import ru.itis.lectures.githubmvp.testutils.prefs.SharedPreferencesMapImpl;
import ru.itis.lectures.githubmvp.view.LogInView;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Artur Vasilov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Base64.class})
public class LogInPresenterTest {

    private static final String TEST_TOKEN = "test_token";

    private SharedPreferences mPreferences;
    private Context mContext;

    static {
        RxSchedulers.setupTestSchedulers();
    }

    @Before
    public void setUp() throws Exception {
        mockStatic(Base64.class);
        PowerMockito.when(Base64.encodeToString(any(byte[].class), any(Integer.class))).thenAnswer(invocation -> {
            byte[] bytes = (byte[]) invocation.getArguments()[0];
            return new String(bytes);
        });

        mPreferences = new SharedPreferencesMapImpl();
        mContext = mock(Context.class);
        when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mPreferences);
    }

    @Test
    public void testCheckAuthorizedTrue() throws Exception {
        Settings.putToken(mContext, TEST_TOKEN);
        LogInView view = createView();

        LogInPresenter presenter = new LogInPresenter(view, mContext);
        presenter.checkIfAuthorized();
        verify(view).openRepositoriesScreen();
        mPreferences.edit().clear().apply();
    }

    @Test
    public void testCheckAuthorizedFalse() throws Exception {
        mPreferences.edit().clear().apply();
        LogInView view = createView();

        LogInPresenter presenter = new LogInPresenter(view, mContext);
        presenter.checkIfAuthorized();
        verify(view, times(0)).openRepositoriesScreen();
    }

    @Test
    public void testEmptyLogin() throws Exception {
        mPreferences.edit().clear().apply();
        LogInView view = createView();

        LogInPresenter presenter = new LogInPresenter(view, mContext);
        presenter.tryLogIn("", "password");
        verify(view).showLoginError();
    }

    @Test
    public void testEmptyPassword() throws Exception {
        mPreferences.edit().clear().apply();
        LogInView view = createView();

        LogInPresenter presenter = new LogInPresenter(view, mContext);
        presenter.tryLogIn("login", "");
        verify(view).showPasswordError();
    }

    @Test
    public void testSuccessAuth() throws Exception {
        mPreferences.edit().clear().apply();
        LogInView view = createView();
        ApiFactory.setProvider(new TestProviderImpl(new GithubServiceSuccess()));

        LogInPresenter presenter = new LogInPresenter(view, mContext);
        presenter.tryLogIn("login", "password");
        verify(view).openRepositoriesScreen();

        assertEquals(TEST_TOKEN, Settings.getToken(mContext));
    }

    @Test
    public void testErrorAuth() throws Exception {
        mPreferences.edit().clear().apply();
        LogInView view = createView();
        ApiFactory.setProvider(new TestProviderImpl(new GithubServiceFail()));

        LogInPresenter presenter = new LogInPresenter(view, mContext);
        presenter.tryLogIn("login", "password");
        verify(view).showLoginError();

        assertTrue(mPreferences.getAll().isEmpty());
    }

    @NonNull
    private LogInView createView() {
        LogInView view = mock(LogInView.class);
        doNothing().when(view).openRepositoriesScreen();
        doNothing().when(view).showLoginError();
        doNothing().when(view).showPasswordError();
        return view;
    }

    private class GithubServiceSuccess extends TestGithubService {

        @Override
        public Observable<Authorization> authorize(@Header("Authorization") String authorization, @Body JsonObject params) {
            Authorization authorization1 = new Authorization();
            authorization1.setId(555);
            authorization1.setToken(TEST_TOKEN);
            return Observable.just(authorization1);
        }
    }

    private class GithubServiceFail extends TestGithubService {

        @Override
        public Observable<Authorization> authorize(@Header("Authorization") String authorization, @Body JsonObject params) {
            return Observable.error(new IOException());
        }
    }
}
