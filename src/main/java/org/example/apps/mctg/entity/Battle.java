package org.example.apps.mctg.entity;

public class Battle {
    private String id;
    private String player1;
    private String player2;
    private String winner;
    private String log;
    private boolean open;

    public Battle() {};
    public Battle(String id, String player1) {
        this.id = id;
        this.player1 = player1;
        this.player2 = null;
        this.winner = null;
        this.log = null;
        this.open = true;
    }
    public Battle(String id, String player1, String player2, String winner, String log, boolean open) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.log = log;
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
