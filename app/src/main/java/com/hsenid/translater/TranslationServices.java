package com.hsenid.translater;

import android.os.AsyncTask;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hsenid.model.TranslationData;
import com.hsenid.model.Words;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hsenid on 18/08/17.
 */

public class TranslationServices extends AsyncTask<Void, Void, String[]> {
    private String inputLang;
    private String outputLang;
    private String display;
    private Words convertedWord = new Words();
    private TranslationData mapper;
    private Gson gson = new Gson();
    private EditText dis;
    private String translateMode;

    public TranslationServices(String inputLang, String outputLang, String display, EditText dis, String translateMode) {
        this.inputLang = inputLang;
        this.outputLang = outputLang;
        this.display = display;
        this.dis = dis;
        this.translateMode = translateMode;
    }

    public String[] doTranslateYandex(String inputLang, String outputLang, String wordToConvert) {
        try {
            URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170503T091519Z.9f30c24402100dfb.91f7ddaca07e07" +
                    "cddb27fd1cd769dd2b43d5c765&text=" + wordToConvert + "&lang=" + inputLang + "-" + outputLang);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            convertedWord = gson.fromJson(br.readLine(), Words.class);
            conn.disconnect();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedWord.getText();
    }

    public String[] doTranslateGoogle(String inputLang, String outputLang, String wordToConvert) {
        try {
            URL url = new URL("https://translation.googleapis.com/language/translate/v2?key=AIzaSyAvzIwL1PW3q3_TyKOCREHMV0sxGtmFpQQ&q=" + wordToConvert +
                    "&target=" + outputLang + "&source=" + inputLang);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            mapper = gson.fromJson(br, TranslationData.class);
            mapper.getData().getTranslations();

            conn.disconnect();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] strArray = new String[]{mapper.getData().getTranslations().get(0).getTranslatedText().toString()};
        return strArray;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        if ("yandex".equals(translateMode)) {
            return doTranslateYandex(inputLang, outputLang, display);
        } else {
            return doTranslateGoogle(inputLang, outputLang, display);
        }
    }

    @Override
    protected void onPostExecute(String[] s) {
        dis.setText(s[0]);
    }
}
