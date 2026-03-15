package org.myteam.minesweeper;

public class Game {
    private Level level;
    private Board board;
    private int flagsLeft;
    private boolean newGame;
    private boolean gameOver;
    private boolean won;
    private long startTimeMillis;

    public Game(Level level) {
        this.level = level;
        this.board = new Board(level);  //Board class should accept a Level type param
        this.flagsLeft = level.getMines();
        this.newGame = true;
        this.gameOver = false;
        this.won = false;
        this.startTimeMillis = System.currentTimeMillis();
    }

    public void win() {
        won = true;
    }

    public void newGame(Level level) {
        this.level = level;
        this.board = new Board(level); //Board class should accept a Level type param
        this.flagsLeft = level.getMines();
        this.newGame = true;
        this.gameOver = false;
        this.won = false;
        this.startTimeMillis = System.currentTimeMillis();
    }

    public void gameOver() {
        gameOver = true;
    }

    public int getTime() {
        long now = System.currentTimeMillis();
        return (int) ((now - startTimeMillis) / 1000);
    }

    public Level getLevel() {
        return level;
    }

    public Board getBoard() {
        return board;
    }

    public int getFlagsLeft() {
        return flagsLeft;
    }

    public void decreaseFlagsLeft() {
        if (flagsLeft > 0) {
            flagsLeft--;
        }
    }

    public void increaseFlagsLeft() {
        flagsLeft++;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWon() {
        return won;
    }
}
