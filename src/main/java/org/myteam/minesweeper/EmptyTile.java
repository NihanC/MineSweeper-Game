package org.myteam.minesweeper;

public class EmptyTile extends Tile{

    public EmptyTile(int r, int c) {
        super(r, c);
    }

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
//            board.openAdjacentTiles(row, col, game);
        }
    }
}
