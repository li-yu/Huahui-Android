package com.liyu.huahui;

/**
 * Created by liyu on 2017/3/2.
 */

public class Word {

    private String name;
    private String correct;
    private String wrong;
    private boolean hasVoice;

    public Word(String name, String correct, String wrong, boolean hasVoice) {
        this.name = name;
        this.correct = correct;
        this.wrong = wrong;
        this.hasVoice = hasVoice;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public boolean isHasVoice() {
        return hasVoice;
    }

    public void setHasVoice(boolean hasVoice) {
        this.hasVoice = hasVoice;
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
