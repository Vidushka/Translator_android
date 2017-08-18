package com.hsenid.translater;

import android.os.AsyncTask;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hsenid on 18/08/17.
 */

public class YandexTranslate extends AsyncTask<Void, Void, String[]> {
    private String urlTemplate = "https://translate.yandex.net/api/v1.5/tr.json/%1$s?key=trnsl.1.1.20170503T091519Z.9f30c24402100dfb.91f7ddaca07e07cddb27fd1cd769dd2b43d5c765&%2$s%3$s%4$s%5$s%6$s%7$s";
    private String yandexUrl;
    private String inputLang;
    private String outputLang;
    private String display;
    private Words convertedWord = new Words();
    private Gson gson = new Gson();
    private EditText dis;

    public YandexTranslate(String inputLang, String outputLang, String display, EditText dis) {
        this.inputLang = inputLang;
        this.outputLang = outputLang;
        this.display = display;
        this.dis = dis;
    }

    public String generateUrl(String param1, String param2, String param3) {

        yandexUrl = String.format(urlTemplate, "translate", "text=", param1, "&lang=", param2, "-", param3);

        return yandexUrl;
    }

    public String[] doTranslate(String inputLang, String outputLang, String wordToConvert) {
        try {
            URL url = new URL(generateUrl(wordToConvert, inputLang, outputLang));
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

    @Override
    protected String[] doInBackground(Void... voids) {
        return doTranslate(inputLang, outputLang, display);
    }

    @Override
    protected void onPostExecute(String[] s) {
        dis.setText(s[0]);
    }
}
