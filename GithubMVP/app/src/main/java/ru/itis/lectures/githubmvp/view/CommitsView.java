package ru.itis.lectures.githubmvp.view;

import android.support.annotation.NonNull;

import java.util.List;

import ru.itis.lectures.githubmvp.content.Commit;
import ru.itis.lectures.githubmvp.content.CommitResponse;

/**
 * Created by yasina on 12.04.16.
 */
public interface CommitsView extends LoadingView {

    void showCommitsList(@NonNull List<Commit> commits);

    void showCommits(@NonNull Commit commit);

}