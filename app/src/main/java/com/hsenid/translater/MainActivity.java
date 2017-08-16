package com.hsenid.translater;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void yandexSelect(View view) {
        Spinner spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        Spinner spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        YandexService yandexService = new YandexService(spinnerFrom, spinnerTo, MainActivity.this);
        yandexService.execute();
    }

}

