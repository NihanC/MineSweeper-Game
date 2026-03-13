package org.myteam.minesweeper;

public class NumberTile extends Tile {

    public NumberTile(int r, int c) {
        super(r, c);
    }

    @Override
    public void open() {
        revealed = true;
    }
}
