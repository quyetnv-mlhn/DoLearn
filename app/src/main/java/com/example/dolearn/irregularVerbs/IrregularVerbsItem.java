package com.example.dolearn.irregularVerbs;

public class IrregularVerbsItem {
    private String infinitive;
    private String past;
    private String pastParticiple;
    private String vieName;

    public IrregularVerbsItem(String infinitive, String past, String pastParticiple, String vieName) {
        this.infinitive = infinitive;
        this.past = past;
        this.pastParticiple = pastParticiple;
        this.vieName = vieName;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public void setInfinitive(String infinitive) {
        this.infinitive = infinitive;
    }

    public String getPast() {
        return past;
    }

    public void setPast(String past) {
        this.past = past;
    }

    public String getPastParticiple() {
        return pastParticiple;
    }

    public void setPastParticiple(String pastParticiple) {
        this.pastParticiple = pastParticiple;
    }

    public String getVieName() {
        return vieName;
    }

    public void setVieName(String vieName) {
        this.vieName = vieName;
    }
}
