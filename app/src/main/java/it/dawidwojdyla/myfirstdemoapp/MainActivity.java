package it.dawidwojdyla.myfirstdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        Button mainWindowButton = findViewById(R.id.btn_next);
        mainWindowButton.setOnClickListener(l -> setContentView(R.layout.layout_main));
    }
}