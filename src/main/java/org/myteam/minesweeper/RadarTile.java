package org.myteam.minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class RadarTile extends Tile{
    private Board board;
    private ArrayList<Mine> mineList;
    public RadarTile(int r, int c) {super(r, c); mineList = board.getMinedTiles();}

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
            Random r= new Random();
            int i = r.nextInt(mineList.size());
            Mine aMine = mineList.get(i);
            if(game.getFlagsLeft()>0){
                while (aMine.isFlagged()){
                    i = r.nextInt(mineList.size());
                    aMine = mineList.get(i);
                }
                aMine.toggleFlag(board, game);
            }
        }
    }
}
