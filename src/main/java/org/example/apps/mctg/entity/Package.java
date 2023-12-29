package org.example.apps.mctg.entity;

public class Package {
    private String id;
    private String user_id;
    private String card1_id;
    private String card2_id;
    private String card3_id;
    private String card4_id;
    private String card5_id;

    public Package() {}

    public Package(String id, String user_id, String card1_id, String card2_id, String card3_id, String card4_id, String card5_id) {
        this.id = id;
        this.user_id = user_id;
        this.card1_id = card1_id;
        this.card2_id = card2_id;
        this.card3_id = card3_id;
        this.card4_id = card4_id;
        this.card5_id = card5_id;
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

    public String getCard1_id() {
        return card1_id;
    }

    public void setCard1_id(String card1_id) {
        this.card1_id = card1_id;
    }

    public String getCard2_id() {
        return card2_id;
    }

    public void setCard2_id(String card2_id) {
        this.card2_id = card2_id;
    }

    public String getCard3_id() {
        return card3_id;
    }

    public void setCard3_id(String card3_id) {
        this.card3_id = card3_id;
    }

    public String getCard4_id() {
        return card4_id;
    }

    public void setCard4_id(String card4_id) {
        this.card4_id = card4_id;
    }

    public String getCard5_id() {
        return card5_id;
    }

    public void setCard5_id(String card5_id) {
        this.card5_id = card5_id;
    }
}
