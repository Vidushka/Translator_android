package com.hsenid.model;

/**
 * Created by hsenid on 28/08/17.
 */

public class TranslatedText {

    private String translatedText;

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    @Override
    public String toString() {
        return "TranslatedText{" +
                "translatedText='" + translatedText + '\'' +
                '}';
    }
}
