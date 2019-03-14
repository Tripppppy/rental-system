package com.suke.RentalSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

public class User {

    private int id;
    private String login_name;
    private String name;
    private String mobile;
    private String email;
    private String image_url;
    private String status;
    private String password;
    private int created_by;
    @JsonFormat(pattern="yyyy-MM-dd")
    private  Date created_date;
    private int last_modified_by;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date last_modified_date;
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getLast_modified_by() {
        return last_modified_by;
    }

    public void setLast_modified_by(int last_modified_by) {
        this.last_modified_by = last_modified_by;
    }

    public Date getLast_modified_date() {
        return last_modified_date;
    }

    public void setLast_modified_date(Date last_modified_date) {
        this.last_modified_date = last_modified_date;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login_name='" + login_name + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", image_url='" + image_url + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                ", created_by=" + created_by +
                ", created_date=" + created_date +
                ", last_modified_by=" + last_modified_by +
                ", last_modified_date=" + last_modified_date +
                ", version=" + version +
                '}';
    }
}
