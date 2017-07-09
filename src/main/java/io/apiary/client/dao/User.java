package io.apiary.client.dao;

/**
 * Created by
 *
 *  Salvatore Rapisarda on 08/07/2017.
 */
public class User {

    public User(String username, String key ){
        this.username = username;
        this.apikey = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    private String username;
    private String apikey;

}
