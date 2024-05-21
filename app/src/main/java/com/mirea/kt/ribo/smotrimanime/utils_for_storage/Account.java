package com.mirea.kt.ribo.smotrimanime.utils_for_storage;

public class Account {
    private String userName,mail,UID;

    public Account(String userName, String mail, String UID) {
        this.userName = userName;
        this.mail = mail;
        this.UID = UID;
    }

    public Account() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
