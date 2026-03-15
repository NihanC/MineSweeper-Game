package org.myteam.minesweeper;

public class Mine extends Tile {

    public Mine(int r, int c) {
        super(r, c);
    }

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
            game.gameOver();
            board.revealAllTiles();
        }
    }
}
