package org.myteam.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RadarTile extends Tile {

    public RadarTile(int r, int c) {
        super(r, c);
    }

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;

            if (game.getFlagsLeft() <= 0) return;

            ArrayList<Mine> mineList = board.getMinedTiles();
            if (mineList.isEmpty()) return;

            List<Mine> unflaggedMines = new ArrayList<>();
            for (Mine m : mineList) {
                if (!m.isFlagged()) {
                    unflaggedMines.add(m);
                }
            }

            if (unflaggedMines.isEmpty()) return;

            Random r = new Random();
            Mine aMine = unflaggedMines.get(r.nextInt(unflaggedMines.size()));
            aMine.toggleFlag(board, game);
        }
    }
}
