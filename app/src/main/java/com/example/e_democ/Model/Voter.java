package com.example.e_democ.Model;

public class Voter {

    String v_fullname,v_username,v_email,v_password,v_id;

    public Voter(String v_fullname, String v_username, String v_email, String v_password, String v_id) {
        this.v_fullname = v_fullname;
        this.v_username = v_username;
        this.v_email = v_email;
        this.v_password = v_password;
        this.v_id = v_id;
    }

    public String getV_fullname() {
        return v_fullname;
    }

    public void setV_fullname(String v_fullname) {
        this.v_fullname = v_fullname;
    }

    public String getV_username() {
        return v_username;
    }

    public void setV_username(String v_username) {
        this.v_username = v_username;
    }

    public String getV_email() {
        return v_email;
    }

    public void setV_email(String v_email) {
        this.v_email = v_email;
    }

    public String getV_password() {
        return v_password;
    }

    public void setV_password(String v_password) {
        this.v_password = v_password;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }
}
