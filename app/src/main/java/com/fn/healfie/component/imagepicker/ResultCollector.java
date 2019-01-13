package com.fn.healfie.component.imagepicker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ipusic on 12/28/16.
 */

class ResultCollector {
    private IResultCollector promise;
    private int waitCount;
    private boolean multiple;
    private AtomicInteger waitCounter;
    private JSONArray arrayResult;
    private boolean resultSent;

    synchronized void setup(IResultCollector promise, boolean multiple) {
        this.promise = promise;
        this.multiple = multiple;

        this.resultSent = false;
        this.waitCount = 0;
        this.waitCounter = new AtomicInteger(0);

        if (multiple) {
            this.arrayResult = new JSONArray();
        }
    }

    // if user has provided "multiple" option, we will wait for X number of result to come,
    // and also return result as an array
    synchronized void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
        this.waitCounter = new AtomicInteger(0);
    }

    synchronized private boolean isRequestValid() {
        if (resultSent) {
            Log.w("image-crop-picker", "Skipping result, already sent...");
            return false;
        }

        if (promise == null) {
            Log.w("image-crop-picker", "Trying to notify success but promise is not set");
            return false;
        }

        return true;
    }

    synchronized void notifySuccess(JSONObject result) {
        if (!isRequestValid()) {
            return;
        }

        if (multiple) {
            arrayResult.put(result);
            int currentCount = waitCounter.addAndGet(1);

            if (currentCount == waitCount) {
                promise.resolve(arrayResult);
                resultSent = true;
            }
        } else {
            promise.resolve(result);
            resultSent = true;
        }
    }

    synchronized void notifyProblem(String code, String message) {
        if (!isRequestValid()) {
            return;
        }

        Log.e("image-crop-picker", "Promise rejected. " + message);
        promise.reject(code, message);
        resultSent = true;
    }

    synchronized void notifyProblem(String code, Throwable throwable) {
        if (!isRequestValid()) {
            return;
        }

        Log.e("image-crop-picker", "Promise rejected. " + throwable.getMessage());
        promise.reject(code, throwable.toString());
        resultSent = true;
    }
}
