package com.sphe.numberincrementer.listeners;

import android.util.Log;

import com.sphe.numberincrementer.interfaces.LimitExceededListener;

public class DefaultLimitExceededListener implements LimitExceededListener {

    @Override
    public void limitExceeded(int limit, int exceededValue) {
        Log.v(this.getClass().getSimpleName(), String.format("NumberPicker cannot set to %d because the limit is %d.", exceededValue, limit));
    }
}
