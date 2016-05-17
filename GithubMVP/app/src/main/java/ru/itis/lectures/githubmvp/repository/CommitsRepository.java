package ru.itis.lectures.githubmvp.repository;

import android.support.annotation.NonNull;

import java.util.List;

import ru.itis.lectures.githubmvp.content.Commit;
import ru.itis.lectures.githubmvp.content.CommitResponse;
import ru.itis.lectures.githubmvp.content.Repository;
import rx.Observable;

/**
 * Created by yasina on 12.04.16.
 */
public interface CommitsRepository {
    @NonNull
    Observable<List<Commit>> cachedCommits();

    @NonNull
    Observable<List<Commit>> saveCommits(List<Commit> commits);

}
