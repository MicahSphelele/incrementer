package com.sphe.incrementer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        number_picker_default.max = 15
//        number_picker_default.min = 1
//        number_picker_default.unit = 2
//        number_picker_default.value = 10

        number_picker_default.setNumberChangedListener { value, _ ->
            Toast.makeText(this,""+value,Toast.LENGTH_SHORT).show()
        }
    }
}
