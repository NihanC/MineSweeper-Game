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

            int unflaggedCount = 0;
            for(Mine m: mineList){
                if(!m.isFlagged()){
                    unflaggedCount++;
                }
            }

            if(unflaggedCount==0) return;


            Mine aMine = mineList.get(r.nextInt(mineList.size()));

            while(aMine.isFlagged()){
                aMine=mineList.get(r.nextInt(mineList.size()));
            }

            if(game.getFlagsLeft()>0){
                aMine.toggleFlag(board, game);
            }
        }
    }


}