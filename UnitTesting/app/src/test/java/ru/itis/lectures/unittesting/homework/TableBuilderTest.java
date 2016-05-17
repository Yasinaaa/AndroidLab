package ru.itis.lectures.unittesting.homework;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.*;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class TableBuilderTest{

    private  TableBuilder mTableBuilder;
    private SQLiteDatabase mDatabase;

    @Before
    public void setUp() {
        mTableBuilder = TableBuilder.create("tabel2");
        mDatabase = mock(SQLiteDatabase.class);
    }

    @Test
    public void testEmptyColumns() throws Exception {
        try {
            mTableBuilder
                    .primaryKey()
                    .execute(mDatabase);
            fail();
        }catch (IllegalStateException e){

        }
    }


    @Test
    public void test1() throws Exception {

        mTableBuilder
                .primaryKey()
                .intColumn("int_column")
                .stringColumn("string_column")
                .execute(mDatabase);

       verify(mDatabase).execSQL("CREATE TABLE IF NOT EXISTS" +
               " tabel2(_id INTEGER PRIMARY KEY, string_column TEXT, int_column INTEGER);");

    }

    @Test
    public void testFalsePrimaryKey() throws Exception {

        mTableBuilder
                .intColumn("int_column")
                .stringColumn("string_column")
                .execute(mDatabase);

        verify(mDatabase).execSQL("CREATE TABLE IF NOT EXISTS tabel2(int_column INTEGER, string_column TEXT);");
    }


    @Test
    public void testFalsePrimaryKeyWithoutIntegerColumn() throws Exception {

        mTableBuilder
                .stringColumn("string_column")
                .execute(mDatabase);

        verify(mDatabase).execSQL("CREATE TABLE IF NOT EXISTS tabel2(string_column TEXT);");
    }



}
