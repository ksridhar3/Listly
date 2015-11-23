package com.codepath.listly;

import android.app.Application;
import android.database.Cursor;
import android.test.ApplicationTestCase;
import android.util.Log;

import junit.framework.Test;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = ApplicationTest.class.getSimpleName();
    public ApplicationTest() {
        super(Application.class);
    }

    protected void setUp() throws Exception{

        TestListlyDatabase();
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void TestListlyDatabase() {
        ListlyDatabase db = new ListlyDatabase(getContext());
        db.insertItem("mad");
        Cursor res = db.getData(1);
        if(res != null)
            res.moveToFirst();
        String listItem = res.getString(res.getColumnIndexOrThrow(ListlyDatabase.COL_ITEM));
        Log.e(TAG, "res str:"+listItem );
    }
}