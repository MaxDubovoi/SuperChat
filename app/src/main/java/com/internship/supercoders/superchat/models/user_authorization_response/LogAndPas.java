package com.internship.supercoders.superchat.models.user_authorization_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by islav on 14.01.2017.
 */

public class LogAndPas {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public LogAndPas(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword() {
        return password;
    }
}
