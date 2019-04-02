package com.twoorthree.graceevangelical;

/**
 * Created by williamhessert on 9/8/18.
// */
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import com.firebase.jobdispatcher.JobParameters;
//import com.firebase.jobdispatcher.JobService;
//
///** A very simple JobService that merely stores its result and immediately finishes. */
//public class JobService extends com.firebase.jobdispatcher.JobService {
//
//    @Override
//    public boolean onStartJob(@NonNull JobParameters job) {
//        //Log.i(JobFormActivity.TAG, "onStartJob called");
//
//        Bundle extras = job.getExtras();
//        assert extras != null;
//
//        int result = extras.getInt("return");
//
//        CentralContainer.getStore(getApplicationContext()).recordResult(job, result);
//
//        return false; // No more work to do
//    }
//
//    @Override
//    public boolean onStopJob(@NonNull JobParameters job) {
//        return false; // No more work to do
//    }
//}

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {

    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}