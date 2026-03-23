package org.myteam.minesweeper;

public enum Level {
    EASY(8, 8, 10),
    MEDIUM(12, 12, 30),
    HARD(16, 16, 60);

    private final int rows;
    private final int cols;
    private final int mines;

    Level(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMines() {
        return mines;
    }
}
