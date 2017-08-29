package com.hsenid.model;

import java.util.List;

/**
 * Created by hsenid on 28/08/17.
 */

public class Translations {
    private List<TranslatedText> translations;

    public List<TranslatedText> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslatedText> translations) {
        this.translations = translations;
    }

    @Override
    public String toString() {
        return "Translations{" +
                "translations=" + translations +
                '}';
    }
}
