package com.hsenid.translater;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private EditText display;
    private String inputLang;
    private String outputLang;
    private RadioButton yandexBtn;
    private RadioButton googleBtn;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private YandexService yandexService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getLanguages(View view) {
        googleBtn = (RadioButton) findViewById(R.id.radioGoogle);
        googleBtn.setEnabled(false);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        yandexService = new YandexService(spinnerFrom, spinnerTo, MainActivity.this);
        yandexService.execute();
    }

    public void doTranslate(View view) {
        display = (EditText) findViewById(R.id.txtDisplay);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        inputLang = yandexService.getKeyFromValue(spinnerFrom.getSelectedItem()).toString();
        outputLang = yandexService.getKeyFromValue(spinnerTo.getSelectedItem()).toString();
        String dis = display.getText().toString();

        YandexTranslate translate = new YandexTranslate(inputLang, outputLang, dis, display);
        translate.execute();
    }
}

