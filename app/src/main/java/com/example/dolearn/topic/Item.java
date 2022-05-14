package com.example.dolearn.topic;

import java.io.Serializable;

public class Item implements Serializable {
    private String engName;
    private String vieName;
    private String pronoun;
    private String exampleEn;
    private String exampleVi;

    private int note;
    private int speak;

    public Item(String engName, String vieName) {
        this.engName = engName;
        this.vieName = vieName;
        this.pronoun = "...";
        this.exampleEn = "...";
        this.exampleVi = "...";
    }

    public Item(String engName, String vieName, String pronoun, String exampleEn, String exampleVi) {
        this.engName = engName;
        this.vieName = vieName;
        this.pronoun = pronoun;
        this.exampleEn = exampleEn;
        this.exampleVi = exampleVi;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getVieName() {
        return vieName;
    }

    public void setVieName(String vieName) {
        this.vieName = vieName;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getSpeak() {
        return speak;
    }

    public void setSpeak(int speak) {
        this.speak = speak;
    }

    public String getPronoun() {
        return pronoun;
    }

    public void setPronoun(String pronoun) {
        this.pronoun = pronoun;
    }

    public String getExampleEn() {
        return exampleEn;
    }

    public void setExampleEn(String exampleEn) {
        this.exampleEn = exampleEn;
    }

    public String getExampleVi() {
        return exampleVi;
    }

    public void setExampleVi(String exampleVi) {
        this.exampleVi = exampleVi;
    }

}
