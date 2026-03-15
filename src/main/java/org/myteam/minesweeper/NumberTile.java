package org.myteam.minesweeper;

public class NumberTile extends Tile {
    private Board board;
    private int adjMines = 0;

    private int value;

    public NumberTile(int r, int c, int value) {
        super(r, c);
        this.value = value;
    }

//    public void calculateAdjMines(){
//        for(int i=Math.max(0, row-1); i<Math.min(board.getRowNum(), row+2); i++){
//            for(int j =Math.max(0, column-1); j<Math.max(board.getColNum(), column-2); j++){
//                if((board.isInMinedTiles(i,j))&&!((i==row)&&(j==column))){
//                    adjMines ++;
//                }
//            }
//        }
//    }

    public int getValue(){
        return value;
    }

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
        }
    }

}
