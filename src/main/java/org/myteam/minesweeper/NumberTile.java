package org.myteam.minesweeper;

public class NumberTile extends Tile {

    private int value;

    public NumberTile(int r, int c, int adjMines) {
        super(r, c);
        value = adjMines;
    }

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
        }
    }
}
