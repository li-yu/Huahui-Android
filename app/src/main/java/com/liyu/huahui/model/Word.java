package com.liyu.huahui.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by liyu on 2017/3/2.
 */

public class Word extends DataSupport implements Serializable {

    private String name;
    private String correctPhonetic;
    private String wrongPhonetic;
    private String voiceUrl;
    private int sourceFrom;

    public int getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(From sourceFrom) {
        this.sourceFrom = sourceFrom.getFrom();
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getCorrectPhonetic() {
        return correctPhonetic;
    }

    public void setCorrectPhonetic(String correctPhonetic) {
        this.correctPhonetic = correctPhonetic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWrongPhonetic() {
        return wrongPhonetic;
    }

    public void setWrongPhonetic(String wrongPhonetic) {
        this.wrongPhonetic = wrongPhonetic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return name != null ? name.toLowerCase().equals(word.name.toLowerCase()) : word.name == null;

    }

    public enum From {
        LOCAL(1),
        NETWORK(2);

        private int from;

        From(int from) {
            this.from = from;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }
    }


}
