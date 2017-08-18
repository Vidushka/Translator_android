package com.hsenid.translater;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by hsenid on 11/08/17.
 */

public class YandexService extends AsyncTask<Void, Void, String[]> {
    protected HashMap<String, Object> result;
    private String urlTemplate = "https://translate.yandex.net/api/v1.5/tr.json/%1$s?key=trnsl.1.1.20170503T091519Z.9f30c24402100dfb.91f7ddaca07e07cddb27fd1cd769dd2b43d5c765&%2$s%3$s%4$s%5$s%6$s%7$s";
    private String yandexUrl;
    private JSONObject obj;
    private Boolean langLoded = false;
    private String[] langArr;
    private Spinner fromLang;
    private Spinner toLang;
    private Context context;


    public YandexService(Spinner fromLang, Spinner toLang, Context context) {
        this.fromLang = fromLang;
        this.context = context;
        this.toLang = toLang;
    }

    public String[] getLanguages() {
        try {
            URL url = new URL(generateUrl("", "", ""));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            obj = new JSONObject(br.readLine()).getJSONObject("langs");
            result = new ObjectMapper().readValue(obj.toString(), HashMap.class);
            langArr = result.values().toArray(new String[0]);

            conn.disconnect();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return langArr;
    }

    public String generateUrl(String param1, String param2, String param3) {
        if (!langLoded) {
            yandexUrl = String.format(urlTemplate, "getLangs", "ui=en", "", "", "", "", "");
            langLoded = true;
        } else {
            yandexUrl = String.format(urlTemplate, "translate", "text=", param1, "&lang=", param2, "-", param3);
        }
        return yandexUrl;
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
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, result.values().toArray(new String[0]));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        fromLang.setAdapter(spinnerArrayAdapter);
        toLang.setAdapter(spinnerArrayAdapter);
    }
}
