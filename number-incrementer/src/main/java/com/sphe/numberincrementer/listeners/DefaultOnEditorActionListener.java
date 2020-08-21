package com.sphe.numberincrementer.listeners;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.sphe.numberincrementer.NumberIncrementer;
import com.sphe.numberincrementer.enums.IncrementerEnum;

public class DefaultOnEditorActionListener implements TextView.OnEditorActionListener {

  private NumberIncrementer layout;

    public DefaultOnEditorActionListener(NumberIncrementer layout) {
        this.layout = layout;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            try {
                int value = Integer.parseInt(v.getText().toString());

                layout.setValue(value);

                if (layout.getValue() == value) {
                    layout.getNumberChangedListener().numberChanged(value, IncrementerEnum.MANUAL);
                    return false;
                }
            } catch (NumberFormatException e) {
                layout.refresh();
            }
        }
        return true;
    }
}
