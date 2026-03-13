package org.myteam.minesweeper;

public class tile {
    private boolean flagged;
    private boolean revealed;
    private int row;
    private int column;

    public tile(int r, int c) {
        this.flagged = false;
        this.revealed = false;
        this.row = r;
        this.column = c;
    }

    public void clicked(boolean bool) {

    }
}
