package ru.itis.lectures.unittesting.powermock;

import android.accounts.Account;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public interface ApiInterface {

    void sendAccount(@NonNull Account account);

}
