package com.example.e_democ.Model;

public class Candidate {
    String c_db_id,c_full_name,c_username,c_email,c_password,c_id,c_political_history,c_supporters,c_img_name,c_election_catagory,c_dob;

    public Candidate(String c_db_id, String c_full_name, String c_username, String c_email, String c_password, String c_id, String c_political_history, String c_supporters, String c_img_name, String c_election_catagory, String c_dob) {
        this.c_db_id = c_db_id;
        this.c_full_name = c_full_name;
        this.c_username = c_username;
        this.c_email = c_email;
        this.c_password = c_password;
        this.c_id = c_id;
        this.c_political_history = c_political_history;
        this.c_supporters = c_supporters;
        this.c_img_name = c_img_name;
        this.c_election_catagory = c_election_catagory;
        this.c_dob = c_dob;
    }

    public String getC_db_id() {
        return c_db_id;
    }

    public void setC_db_id(String c_db_id) {
        this.c_db_id = c_db_id;
    }

    public String getC_full_name() {
        return c_full_name;
    }

    public void setC_full_name(String c_full_name) {
        this.c_full_name = c_full_name;
    }

    public String getC_username() {
        return c_username;
    }

    public void setC_username(String c_username) {
        this.c_username = c_username;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_password() {
        return c_password;
    }

    public void setC_password(String c_password) {
        this.c_password = c_password;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_political_history() {
        return c_political_history;
    }

    public void setC_political_history(String c_political_history) {
        this.c_political_history = c_political_history;
    }

    public String getC_supporters() {
        return c_supporters;
    }

    public void setC_supporters(String c_supporters) {
        this.c_supporters = c_supporters;
    }

    public String getC_img_name() {
        return c_img_name;
    }

    public void setC_img_name(String c_img_name) {
        this.c_img_name = c_img_name;
    }

    public String getC_election_catagory() {
        return c_election_catagory;
    }

    public void setC_election_catagory(String c_election_catagory) {
        this.c_election_catagory = c_election_catagory;
    }

    public String getC_dob() {
        return c_dob;
    }

    public void setC_dob(String c_dob) {
        this.c_dob = c_dob;
    }
}
