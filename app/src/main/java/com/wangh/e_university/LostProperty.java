package com.wangh.e_university;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Galaxy on 2017/11/5.
 */

public class LostProperty extends BmobObject {
    private String title;
    private String user;
    private String phone;
    private String description;
    private BmobFile photo;
    private Boolean found;

    public LostProperty() {
    }

    public LostProperty(String title, String user, String phone, String description, BmobFile photo, Boolean found) {
        this.title = title;
        this.user = user;
        this.phone = phone;
        this.description = description;
        this.photo = photo;
        this.found = found;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    @Override
    public String toString() {
        return "LostProperty{" +
                "title='" + title + '\'' +
                ", user='" + user + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", photo=" + photo +
                ", found=" + found +
                '}';
    }
}
