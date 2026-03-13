package org.myteam.minesweeper;

public class NumberTile extends Tile {
    private int adjMines = 0;
    private int value;
    public NumberTile(int r, int c) {
        super(r, c);
        calculateAdjMines();
        value = adjMines;
    }

    public int calculateAdjMines(){
        for(int i=row-1; i<(row+2); i++){
            for(int j =column-1; j<(column+2); j++){
                grid = Board.getGrid();
                if((Board.isInMinedTiles(i,j))&&!((i==row)&&(j==column)){
                    adjMines ++;
                }
            }
        }
    }

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
        }
    }

}
