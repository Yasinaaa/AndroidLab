package ru.itis.lectures.githubmvp.repository.impl;

import android.support.annotation.NonNull;

import java.util.List;

import ru.arturvasilov.sqlite.SQLite;
import ru.itis.lectures.githubmvp.content.Commit;
import ru.itis.lectures.githubmvp.database.tables.CommitsTable;
import ru.itis.lectures.githubmvp.repository.CommitsRepository;
import rx.Observable;

/**
 * Created by yasina on 12.04.16.
 */
public class CommitsRepositoryImpl implements CommitsRepository {

    @NonNull
    @Override
    public Observable<List<Commit>> cachedCommits() {
        return SQLite.get()
                .query(CommitsTable.TABLE)
                .all()
                .asObservable();
    }

    @NonNull
    @Override
    public Observable<List<Commit>> saveCommits(List<Commit> commits) {
        SQLite.get().delete(CommitsTable.TABLE).execute();
        SQLite.get().insert(CommitsTable.TABLE).insert(commits);
        return Observable.just(commits);
    }



}
