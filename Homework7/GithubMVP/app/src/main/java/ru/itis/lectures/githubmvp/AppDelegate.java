package ru.itis.lectures.githubmvp;

import android.app.Application;

import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.api.ApiProvider;
import ru.itis.lectures.githubmvp.api.DefaultApiProvider;
import ru.itis.lectures.githubmvp.content.Settings;

/**
 * @author Artur Vasilov
 */
public class AppDelegate extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String token = Settings.getToken(this);
        ApiProvider provider = new DefaultApiProvider(token);
        ApiFactory.setProvider(provider);
    }
}
