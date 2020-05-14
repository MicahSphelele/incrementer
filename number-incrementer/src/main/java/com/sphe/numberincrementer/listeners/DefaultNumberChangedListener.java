package com.sphe.numberincrementer.listeners;

import android.util.Log;

import com.sphe.numberincrementer.enums.IncrementerEnum;
import com.sphe.numberincrementer.interfaces.NumberChangedListener;

public class DefaultNumberChangedListener implements NumberChangedListener {

    @Override
    public void numberChanged(int value, IncrementerEnum action) {
        String actionText = action == IncrementerEnum.MANUAL ? "manually set" : (action == IncrementerEnum.INCREMENT ? "incremented" : "decremented");
        Log.v(this.getClass().getSimpleName(), String.format("NumberPicker is %s to %d", actionText, value));
    }
}
