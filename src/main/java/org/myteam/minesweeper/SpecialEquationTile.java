package org.myteam.minesweeper;

import java.util.Random;

//basically ends the game for anyone that doesn't know basic math
public class SpecialEquationTile extends Tile {

    public SpecialEquationTile(int r, int c) {
        super(r, c);
    }

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
            Random r= new Random();
            int i = r.nextInt(10);
            int j = r.nextInt(10);
            int sum = i+j;
            int subtraction = i-j;
        }
    }
}
