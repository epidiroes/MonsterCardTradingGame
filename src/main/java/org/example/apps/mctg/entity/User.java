package org.example.apps.mctg.entity;

public class User {
    private String id;
    private String username;
    private String password;
    private int coins;
    private String name;
    private String bio;
    private String image;
    public User() {}

    public User(String id, String username, String password, int coins) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.name = "";
        this.bio = "";
        this.image = "";
    }

    public User(String id, String username, String password, int coins, String name, String bio, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
