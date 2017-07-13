package com.liyu.huahui.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by liyu on 2017/3/2.
 */

public class Word extends DataSupport implements Serializable {

    private String name;
    private String correct;
    private String wrong;
    private String voice;

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return name != null ? name.toLowerCase().equals(word.name.toLowerCase()) : word.name == null;

    }

}
