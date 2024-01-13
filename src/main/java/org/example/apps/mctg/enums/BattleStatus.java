package org.example.apps.mctg.enums;

public enum BattleStatus {
    CARD1("Card1 won"),
    CARD2("Card2 won"),
    DRAW("Draw");
    private final String status;
    BattleStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
