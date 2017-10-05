package com.wangh.e_university;

import org.jsoup.nodes.Element;

/**
 * Created by wangh on 2016/8/11.
 */
public class ExamItem extends BaseScheduleItem {
    private String teacher;
    private String modus;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;
    private int credit;

    public ExamItem(){
        setExam(true);
    }

    public ExamItem(String title, String teacher, String time, String location, String modus, int credit) {
        super(title, time.replaceAll("\\s", "\n"), location);
        this.teacher = teacher;
        this.modus = modus;
        this.credit = credit;
        getTimes();
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getModus() {
        return modus;
    }

    @Override
    public void setTime(String time) {
        super.setTime(time);
        getTimes();
    }

    public void setModus(String modus) {
        this.modus = modus;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public void parseExam(Element exam){
        setTitle(exam.child(2).text());
        teacher=exam.child(4).text();
        credit=Integer.parseInt(exam.child(5).text());
        setTime(exam.child(6).text());
        setLocation(exam.child(7).text());
        modus=exam.child(8).text();
        getTimes();
    }

    private void getTimes(){
        setMonth(Integer.parseInt(getTime().split("\\s")[0].split("-")[1]) - 1);
        setDay(Integer.parseInt(getTime().split("\\s")[0].split("-")[2]));
        startHour=(Integer.parseInt(getTime().split("\\s")[1].split("-")[0].split(":")[0]));
        endHour=(Integer.parseInt(getTime().split("\\s")[1].split("-")[1].split(":")[0]));
        startMinute=(Integer.parseInt(getTime().split("\\s")[1].split("-")[0].split(":")[1]));
        endMinute=(Integer.parseInt(getTime().split("\\s")[1].split("-")[1].split(":")[1]));
    }

    @Override
    public String toString() {
        return "ExamItem{" +
                "title='" + getTitle() + '\'' +
                ", teacher='" + teacher + '\'' +
                ", time='" + getTime() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", modus='" + modus + '\'' +
                ", credit=" + credit + '\'' +
                ", month=" + getMonth() + '\'' +
                ", day=" + getDay() + '\'' +
                ", startHour=" + startHour + '\'' +
                ", endHour=" + endHour + '\'' +
                ", startMinute=" + startMinute + '\'' +
                ", endMinute=" + endMinute + '\'' +
                '}';
    }
}
