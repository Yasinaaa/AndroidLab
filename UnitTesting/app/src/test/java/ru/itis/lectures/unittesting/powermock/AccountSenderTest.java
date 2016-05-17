package ru.itis.lectures.unittesting.powermock;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Artur Vasilov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AccountManager.class)
public class AccountSenderTest {

    private AccountManager mAccountManager;
    private Account mAccount;

    @Before
    public void setUp() throws Exception {
        mAccount = mock(Account.class);
        Account[] accounts = {mAccount};
        mAccountManager = mock(AccountManager.class);
        //noinspection ResourceType
        when(mAccountManager.getAccounts()).thenReturn(accounts);

        mockStatic(AccountManager.class);
        when(AccountManager.get(any(Context.class))).then(new Answer<AccountManager>() {
            @Override
            public AccountManager answer(InvocationOnMock invocation) throws Throwable {
                return mAccountManager;
            }
        });
    }

    @Test
    public void testSendAccount() throws Exception {
        Context context = mock(Context.class);
        ApiInterface api = mock(ApiInterface.class);
        doNothing().when(api).sendAccount(any(Account.class));

        AccountSender.sendAccountToServer(context, api);
        verify(api).sendAccount(mAccount);
    }
}
