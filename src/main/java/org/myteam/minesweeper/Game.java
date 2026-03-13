package org.myteam.minesweeper;

public class Game {
    private Level level;
//    private Board board;
    private int flagsLeft;
    private boolean newGame;
    private boolean gameOver;
    private boolean won;
    private long startTimeMillis;

    public Game(Level level) {
        this.level = level;
//        this.board = new Board(level);
        this.flagsLeft = level.getMines();
        this.newGame = true;
        this.gameOver = false;
        this.won = false;
        this.startTimeMillis = System.currentTimeMillis();
    }

    public boolean win() {
        return false;
    }

    public void newGame() {

    }

    public void gameOver() {

    }

    public void getTime() {
        
    }
}
