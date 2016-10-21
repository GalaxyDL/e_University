package com.wangh.e_university;

/**
 * Created by wangh on 2016/8/11.
 */
public class ScoreItem {
    private String title;
    private double score;
    private double credit;

    public ScoreItem(String title, double score, double credit) {
        this.title = title;
        this.score = score;
        this.credit = credit;
    }

    public ScoreItem(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "ScoreItem{" +
                "title='" + title + '\'' +
                ", score=" + score +
                ", credit=" + credit +
                '}';
    }
}
