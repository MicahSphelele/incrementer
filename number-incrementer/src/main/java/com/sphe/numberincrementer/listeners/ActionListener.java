package com.sphe.numberincrementer.listeners;

import android.view.View;
import android.widget.EditText;

import com.sphe.numberincrementer.NumberIncrementer;
import com.sphe.numberincrementer.enums.IncrementerEnum;

public class ActionListener implements View.OnClickListener {

    NumberIncrementer layout;
    IncrementerEnum action;
    EditText display;

    public ActionListener(NumberIncrementer layout, IncrementerEnum action, EditText display) {
        this.layout = layout;
        this.action = action;
        this.display = display;
    }

    @Override
    public void onClick(View v) {
        try {
            int newValue = Integer.parseInt(this.display.getText().toString());

            if (!this.layout.valueIsAllowed(newValue)) {
                return;
            }

            this.layout.setValue(newValue);
        } catch (NumberFormatException e) {
            this.layout.refresh();
        }

        switch (this.action) {
            case INCREMENT:
                this.layout.increment();
                break;
            case DECREMENT:
                this.layout.decrement();
                break;
        }

    }
}
