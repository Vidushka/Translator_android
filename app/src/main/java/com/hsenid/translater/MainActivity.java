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
    private LanguageServices languageServices;
    private String translateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getLanguages(View view) {
        googleBtn = (RadioButton) findViewById(R.id.radioGoogle);
        yandexBtn = (RadioButton) findViewById(R.id.radioYandX);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        if (yandexBtn.isChecked()) {
            translateMode = "yandex";
        } else {
            translateMode = "google";
        }
        googleBtn.setEnabled(false);

        languageServices = new LanguageServices(spinnerFrom, spinnerTo, MainActivity.this, translateMode);
        languageServices.execute();
    }

    public void doTranslate(View view) {
        display = (EditText) findViewById(R.id.txtDisplay);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        inputLang = languageServices.getKeyFromValue(spinnerFrom.getSelectedItem()).toString();
        outputLang = languageServices.getKeyFromValue(spinnerTo.getSelectedItem()).toString();
        String dis = display.getText().toString();

        TranslationServices translate = new TranslationServices(inputLang, outputLang, dis, display, translateMode);
        translate.execute();
    }
}

