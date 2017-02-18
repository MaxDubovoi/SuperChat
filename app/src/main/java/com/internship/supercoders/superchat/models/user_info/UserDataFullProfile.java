package com.internship.supercoders.superchat.models.user_info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Max on 17.02.2017.
 */
public class UserDataFullProfile {
    @SerializedName("id")
    int id;
    @SerializedName("full_name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("login")
    String login;
    @SerializedName("phone")
    String phone;
    @SerializedName("website")
    String website;
    @SerializedName("created_at")
    String createdTime;
    @SerializedName("updated_at")
    String updatedTime;
    @SerializedName("last_request_at")
    String lastRequesrTime;
    @SerializedName("external_user_id")
    int externalUserId;
    @SerializedName("facebook_id")
    String facebookId;
    @SerializedName("twitter_id")
    String twitterId;
    @SerializedName("twitter_digits_id")
    int twitterDigitId;
    @SerializedName("blob_id")
    String blobId;
    @SerializedName("user_tags")
    String tags;


}
