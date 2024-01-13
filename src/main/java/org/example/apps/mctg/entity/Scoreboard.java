package org.example.apps.mctg.entity;

public class Scoreboard {
    String id;
    String stats_id;
    Scoreboard(String id, String stats_id) {
        this.id = id;
        this.stats_id = stats_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStats_id() {
        return stats_id;
    }

    public void setStats_id(String stats_id) {
        this.stats_id = stats_id;
    }
}
