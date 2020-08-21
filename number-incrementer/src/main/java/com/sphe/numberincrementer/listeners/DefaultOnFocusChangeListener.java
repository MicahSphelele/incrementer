package com.sphe.numberincrementer.listeners;

import android.view.View;
import android.widget.EditText;

import com.sphe.numberincrementer.NumberIncrementer;
import com.sphe.numberincrementer.enums.IncrementerEnum;

public class DefaultOnFocusChangeListener implements View.OnFocusChangeListener {

   private NumberIncrementer layout;

    public DefaultOnFocusChangeListener(NumberIncrementer layout) {
        this.layout = layout;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;

        if (!hasFocus) {
            try {
                int value = Integer.parseInt(editText.getText().toString());
                layout.setValue(value);

                if (layout.getValue() == value) {
                    layout.getNumberChangedListener().numberChanged(value, IncrementerEnum.MANUAL);
                } else {
                    layout.refresh();
                }
            } catch (NumberFormatException e) {
                layout.refresh();
            }
        }
    }
}
