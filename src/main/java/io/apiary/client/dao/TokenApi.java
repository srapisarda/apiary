package io.apiary.client.dao;

/**
 * Created by Salvatore Rapisarda
 *
 */
public class TokenApi {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
