package ru.itis.lectures.githubmvp.repository;

import android.support.annotation.NonNull;

import java.util.List;

import ru.itis.lectures.githubmvp.content.Repository;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface RepositoriesRepository {

    @NonNull
    Observable<List<Repository>> cachedRepositories();

    @NonNull
    Observable<List<Repository>> saveRepositories(List<Repository> repositories);

}
