package org.myteam.minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class RadarTile extends Tile {

    public RadarTile(int r, int c) {
        super(r, c);
    }

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
            ArrayList<Mine> mineList = board.getMinedTiles();
            if (mineList.isEmpty()) return;
            Random r = new Random();
            int i = r.nextInt(mineList.size());
            Mine aMine = mineList.get(i);
            if (game.getFlagsLeft() > 0) {
                // Guard against infinite loop if all mines are already flagged
                long unflaggedCount = mineList.stream().filter(m -> !m.isFlagged()).count();
                if (unflaggedCount == 0) return;

                while (aMine.isFlagged()) {
                    i = r.nextInt(mineList.size());
                    aMine = mineList.get(i);
                }
                aMine.toggleFlag(board, game);
            }
        }
    }
}