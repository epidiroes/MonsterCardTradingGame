package org.example.apps.mctg.enums;

public enum Status {
    CARD1("Card1 won"),
    CARD2("Card2 won"),
    DRAW("Draw");
    private final String status;
    Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
