package org.example.apps.mctg.entity;

public class Trade {
    String id;
    String userId;
    String cardToTrade;
    String type;
    int minimumDamage;
    public Trade() {}
    public Trade(String id, String user_id, String card_to_trade, String type, int minimum_damage) {
        this.id = id;
        this.userId = user_id;
        this.cardToTrade = card_to_trade;
        this.type = type;
        this.minimumDamage = minimum_damage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardToTrade() {
        return cardToTrade;
    }

    public void setCardToTrade(String cardToTrade) {
        this.cardToTrade = cardToTrade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinimumDamage() {
        return minimumDamage;
    }

    public void setMinimumDamage(int minimumDamage) {
        this.minimumDamage = minimumDamage;
    }
}
