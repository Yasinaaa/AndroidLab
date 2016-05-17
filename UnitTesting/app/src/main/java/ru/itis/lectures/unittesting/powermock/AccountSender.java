package ru.itis.lectures.unittesting.powermock;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class AccountSender {

    public static void sendAccountToServer(@NonNull Context context, @NonNull ApiInterface api) {
        //noinspection ResourceType
        Account account = AccountManager.get(context).getAccounts()[0];
        api.sendAccount(account);
    }

}
