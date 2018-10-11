package vn.backshop.github.model;

import com.google.gson.annotations.SerializedName;

public class UserEntity {
    @SerializedName("id")
    private long id;
    @SerializedName("login")
    private String login;
    @SerializedName("avatar_url")
    private String avatar;
    @SerializedName("site_admin")
    private boolean isBadge;

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isBadge() {
        return isBadge;
    }
}
