package vn.backshop.github.model;

import com.google.gson.annotations.SerializedName;

public class DetailEntity extends UserEntity{
    @SerializedName("name")
    private String name;
    @SerializedName("bio")
    private String bio;
    @SerializedName("location")
    private String location;
    @SerializedName("blog")
    private String blog;

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getBlog() {
        return blog;
    }
}
