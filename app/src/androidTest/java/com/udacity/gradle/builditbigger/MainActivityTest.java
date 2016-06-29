package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.TimeUnit;

/**
 * Created by Gaurav on 29-06-2016.
 */

public class MainActivityTest extends AndroidTestCase {

    public void testAsyncTask(){
        try {
            EndpointsAsyncTask jokeTask = new EndpointsAsyncTask();
            jokeTask.execute(getContext());
            jokeTask.get(60, TimeUnit.SECONDS);
        } catch (Exception e){
            fail("Timed out");
        }
    }
}
