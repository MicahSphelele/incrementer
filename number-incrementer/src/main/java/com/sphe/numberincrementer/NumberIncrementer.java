package com.sphe.numberincrementer;

import android.content.Context;
import android.content.res.TypedArray;
import android.icu.text.NumberFormat;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sphe.numberincrementer.enums.IncrementerEnum;
import com.sphe.numberincrementer.interfaces.LimitExceededListener;
import com.sphe.numberincrementer.interfaces.NumberChangedListener;
import com.sphe.numberincrementer.listeners.ActionListener;
import com.sphe.numberincrementer.listeners.DefaultLimitExceededListener;
import com.sphe.numberincrementer.listeners.DefaultNumberChangedListener;
import com.sphe.numberincrementer.listeners.DefaultOnEditorActionListener;
import com.sphe.numberincrementer.listeners.DefaultOnFocusChangeListener;

public class NumberIncrementer extends LinearLayout {

    // default values
    private final int DEFAULT_MIN = 0;
    private final int DEFAULT_MAX = 999999;
    private final int DEFAULT_VALUE = 1;
    private final int DEFAULT_UNIT = 1;
    private final int DEFAULT_LAYOUT = R.layout.incrementer_layout;
    private final boolean DEFAULT_FOCUSABLE = false;

    // ui components
    private Context mContext;
    private Button decrementButton;
    private Button incrementButton;
    private EditText displayEditText;

    // required variables
    private int minValue;
    private int maxValue;
    private int unit;
    private int currentValue;
    private int layout;
    private boolean focusable;

    // listeners
    private LimitExceededListener limitExceededListener;
    private NumberChangedListener numberChangedListener;

    public NumberIncrementer(Context context) {
        super(context);
    }

    public NumberIncrementer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context,attrs);
    }

    public NumberIncrementer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Incrementer, 0, 0);

        // set required variables with values of xml layout attributes or default ones
        this.minValue = attributes.getInteger(R.styleable.Incrementer_min, this.DEFAULT_MIN);
        this.maxValue = attributes.getInteger(R.styleable.Incrementer_max, this.DEFAULT_MAX);
        this.currentValue = attributes.getInteger(R.styleable.Incrementer_value, this.DEFAULT_VALUE);
        this.unit = attributes.getInteger(R.styleable.Incrementer_unit, this.DEFAULT_UNIT);
        this.layout = attributes.getResourceId(R.styleable.Incrementer_custom_layout, this.DEFAULT_LAYOUT);
        this.focusable = attributes.getBoolean(R.styleable.Incrementer_focusable, this.DEFAULT_FOCUSABLE);
        this.mContext = context;

        // if current value is greater than the max. value, decrement it to the max. value
        this.currentValue = this.currentValue > this.maxValue ? maxValue : currentValue;

        // if current value is less than the min. value, decrement it to the min. value
        this.currentValue = this.currentValue < this.minValue ? minValue : currentValue;

        // set layout view
        LayoutInflater.from(this.mContext).inflate(layout, this, true);

        // init ui components
        this.decrementButton = findViewById(R.id.decrement);
        this.incrementButton = findViewById(R.id.increment);
        this.displayEditText = findViewById(R.id.display);

        // register button click and action listeners
        this.incrementButton.setOnClickListener(new ActionListener(this,  IncrementerEnum.INCREMENT,this.displayEditText));
        this.decrementButton.setOnClickListener(new ActionListener(this,  IncrementerEnum.DECREMENT,this.displayEditText));

        // init listener for exceeding upper and lower limits
        this.setLimitExceededListener(new DefaultLimitExceededListener());
        // init listener for increment&decrement
        this.setNumberChangedListener(new DefaultNumberChangedListener());
        // init listener for focus change
        this.setOnFocusChangeListener(new DefaultOnFocusChangeListener(this));
        // init listener for done action in keyboard
        this.setOnEditorActionListener(new DefaultOnEditorActionListener(this));

        // set default display mode
        this.setDisplayFocusable(this.focusable);

        // update ui view
        this.refresh();
    }

    public void refresh() {
        this.displayEditText.setText(String.valueOf(this.currentValue));
    }

    public void clearFocus() {
        this.displayEditText.clearFocus();
    }

    public boolean valueIsAllowed(int value) {
        return (value >= this.minValue && value <= this.maxValue);
    }

    public void setMin(int value) {
        this.minValue = value;
    }

    public void setMax(int value) {
        this.maxValue = value;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return this.unit;
    }

    public int getMin() {
        return this.minValue;
    }

    public int getMax() {
        return this.maxValue;
    }

    public void setValue(int value) {
        if (!this.valueIsAllowed(value)) {
            this.limitExceededListener.limitExceeded(value < this.minValue ? this.minValue : this.maxValue, value);
            return;
        }
        this.currentValue = value;
        this.refresh();
    }

    public int getValue() {
        return this.currentValue;
    }

    public void setLimitExceededListener(LimitExceededListener limitExceededListener) {
        this.limitExceededListener = limitExceededListener;
    }

    public LimitExceededListener getLimitExceededListener() {
        return this.limitExceededListener;
    }

    public void setNumberChangedListener(NumberChangedListener numberChangedListener) {
        this.numberChangedListener = numberChangedListener;
    }

    public NumberChangedListener getNumberChangedListener() {
        return this.numberChangedListener;
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener onEditorActionListener) {
        this.displayEditText.setOnEditorActionListener(onEditorActionListener);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.displayEditText.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void setActionEnabled(IncrementerEnum action, boolean enabled) {
        if (action == IncrementerEnum.INCREMENT) {
            this.incrementButton.setEnabled(enabled);
        } else if (action == IncrementerEnum.DECREMENT) {
            this.decrementButton.setEnabled(enabled);
        }
    }

    public void setDisplayFocusable(boolean focusable) {
        this.displayEditText.setFocusable(focusable);

        // required for making EditText focusable
        if (focusable) {
            this.displayEditText.setFocusableInTouchMode(true);
        }
    }

    public void increment() {
        this.changeValueBy(this.unit);
    }

    public void increment(int unit) {
        this.changeValueBy(unit);
    }

    public void decrement() {
        this.changeValueBy(-this.unit);
    }

    public void decrement(int unit) {
        this.changeValueBy(-unit);
    }



    private void changeValueBy(int unit) {
        int oldValue = this.getValue();

        this.setValue(this.currentValue + unit);

        if (oldValue != this.getValue()) {
            this.numberChangedListener.numberChanged(this.getValue(), unit > 0 ? IncrementerEnum.INCREMENT : IncrementerEnum.DECREMENT);
        }
    }

}
