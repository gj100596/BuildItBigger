package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.TimeUnit;


public class MainActivityTest extends AndroidTestCase {

    public void testAsyncTask(){
        try {
            EndpointsAsyncTask jokeTask = new EndpointsAsyncTask(getContext());
            jokeTask.execute();
            jokeTask.get(60, TimeUnit.SECONDS);
        } catch (Exception e){
            fail("Timed out");
        }
    }
}
