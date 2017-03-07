package com.liyu.huahui;

import org.litepal.crud.DataSupport;

/**
 * Created by liyu on 2017/3/2.
 */

public class Word extends DataSupport {

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
}
