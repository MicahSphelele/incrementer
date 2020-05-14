package com.sphe.numberincrementer.interfaces;

import com.sphe.numberincrementer.enums.IncrementerEnum;

public interface NumberChangedListener {
    void numberChanged(int value, IncrementerEnum action);
}
