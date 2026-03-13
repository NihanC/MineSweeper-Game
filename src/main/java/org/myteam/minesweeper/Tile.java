package org.myteam.minesweeper;

public abstract class Tile {
    protected boolean flagged;
    protected boolean revealed;
    protected int row;
    protected int column;

    public Tile(int r, int c) {
        this.flagged = false;
        this.revealed = false;
        this.row = r;
        this.column = c;
    }

    public void toggleFlag() {
        if (!revealed) {
            flagged = true;
        }
    }

    public abstract void open();

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
