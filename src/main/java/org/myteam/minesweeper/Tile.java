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

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void toggleFlag(Board board, Game game) {
        if (!revealed && !flagged) {
            if(game.getFlagsLeft()>0){
                flagged = true;
                game.decreaseFlagsLeft();
            }
        }
        else if (flagged) {
            flagged = false;
            game.increaseFlagsLeft();
        }
    }

    public abstract void open(Board board, Game game);

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
