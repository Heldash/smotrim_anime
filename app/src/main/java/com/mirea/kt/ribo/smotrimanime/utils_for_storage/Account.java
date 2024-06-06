package com.mirea.kt.ribo.smotrimanime.utils_for_storage;

public class Account {
    private String userName,mail,UID,avatarImgUrl;

    public Account(String userName, String mail, String UID, String avatarImgUrl) {
        this.userName = userName;
        this.mail = mail;
        this.UID = UID;
        this.avatarImgUrl = avatarImgUrl;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userName='" + userName + '\'' +
                ", mail='" + mail + '\'' +
                ", UID='" + UID + '\'' +
                ", avatarImgUrl='" + avatarImgUrl + '\'' +
                '}';
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

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
    }
}
