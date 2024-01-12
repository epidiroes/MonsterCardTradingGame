package org.example.apps.mctg.entity;

public class Stat {
    String id;
    String user_id;
    int elo;
    int games_played;
    int games_won;

    public Stat() {}
    public Stat(String id, String user_id, int elo, int games_played, int games_won) {
        this.id = id;
        this.user_id = user_id;
        this.elo = elo;
        this.games_played = games_played;
        this.games_won = games_won;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getGames_played() {
        return games_played;
    }

    public void setGames_played(int games_played) {
        this.games_played = games_played;
    }

    public int getGames_won() {
        return games_won;
    }

    public void setGames_won(int games_won) {
        this.games_won = games_won;
    }
}
