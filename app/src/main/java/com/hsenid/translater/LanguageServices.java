package com.hsenid.translater;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.hsenid.model.LanguageData;
import com.hsenid.model.LangugeDetails;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hsenid on 11/08/17.
 */

public class LanguageServices extends AsyncTask<Void, Void, String[]> {
    protected HashMap<String, Object> result;
    private JSONObject obj;
    private String[] langArr;
    private Spinner fromLang;
    private Spinner toLang;
    private Context context;
    private String translateMode;
    private URL url;
    private HttpURLConnection conn;
    private BufferedReader br;
    private LanguageData langs;


    public LanguageServices(Spinner fromLang, Spinner toLang, Context context, String translateMode) {
        this.fromLang = fromLang;
        this.context = context;
        this.toLang = toLang;
        this.translateMode = translateMode;
    }

    public String[] getLanguages() {
        try {
            if ("google".equals(translateMode)) {
                url = new URL("https://translation.googleapis.com/language/translate/v2/languages?key=AIzaSyAvzIwL1PW3q3_TyKOCREHMV0sxGtmFpQQ&target=en");

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Gson gson = new Gson();
                langs = gson.fromJson(br, LanguageData.class);
                List<LangugeDetails> languages = langs.getData().getLanguages();

                result = new HashMap<String, Object>();
                for (int i = 0; i < languages.size(); i++) {
                    result.put(languages.get(i).getLanguage(), languages.get(i).getName());
                }
                langArr = result.values().toArray(new String[0]);

            } else {
                url = new URL("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170503T091519Z.9f30c24402100dfb.91f7ddaca" +
                        "07e07cddb27fd1cd769dd2b43d5c765&ui=en");

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                obj = new JSONObject(br.readLine()).getJSONObject("langs");
                result = new ObjectMapper().readValue(obj.toString(), HashMap.class);
                langArr = result.values().toArray(new String[0]);

            }
            conn.disconnect();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return langArr;
    }

    public Object getKeyFromValue(Object value) {
        for (Object o : result.keySet()) {
            if (result.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        return getLanguages();
    }


    @Override
    protected void onPostExecute(String[] langs) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, langs);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        fromLang.setAdapter(spinnerArrayAdapter);
        toLang.setAdapter(spinnerArrayAdapter);
    }
}
