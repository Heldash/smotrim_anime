package com.mirea.kt.ribo.smotrimanime.utils_internet;

public class TokenShiki {
    private String accesToken,userAgent,authorization;
    private String client_secret,client_id;
    private String refresh_token;

    public TokenShiki() {
    }

    public TokenShiki(String accesToken, String userAgent, String authorization, String client_secret, String client_id, String refresh_token) {
        this.accesToken = accesToken;
        this.userAgent = userAgent;
        this.authorization = authorization;
        this.client_secret = client_secret;
        this.client_id = client_id;
        this.refresh_token = refresh_token;
    }

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
