package com.saveetha.hirebridge;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("username")
    private String username;

    @SerializedName("college")
    private String college;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("languages")
    private String languages;

    public String getUsername() { return username; }
    public String getCollege() { return college; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLanguages() { return languages; }
}
