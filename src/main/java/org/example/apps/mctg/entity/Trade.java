package org.example.apps.mctg.entity;

public class Trade {
    String id;
    String user_id;
    String card_to_trade;
    String type;
    int minimum_damage;
    public Trade() {}
    public Trade(String id, String user_id, String card_to_trade, String type, int minimum_damage) {
        this.id = id;
        this.user_id = user_id;
        this.card_to_trade = card_to_trade;
        this.type = type;
        this.minimum_damage = minimum_damage;
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

    public String getCard_to_trade() {
        return card_to_trade;
    }

    public void setCard_to_trade(String card_to_trade) {
        this.card_to_trade = card_to_trade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinimum_damage() {
        return minimum_damage;
    }

    public void setMinimum_damage(int minimum_damage) {
        this.minimum_damage = minimum_damage;
    }
}
