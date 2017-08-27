package com.wangh.e_university;

import org.jsoup.nodes.Element;

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

    public void parseScore(Element score){
        title=score.child(4).text();
        credit=Double.parseDouble(score.child(8).text().replace("\n", "").replace(" ", ""));
        this.score=Double.parseDouble(score.child(9).child(0).child(0).text().replace("&nbsp;", "").replace("\n", "").replace(" ", ""));
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
