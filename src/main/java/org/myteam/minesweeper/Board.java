package org.myteam.minesweeper;
import java.util.Arrays;
public class Board {
    private Tile[][] grid;
    private boolean firstTile;
    private int nrOfMines;
    private Tile first;
    //again, for now
    private int rowNum;
    private int colNum;
    //for now, row and col will be 4 and 4
    public Board(level){
        grid = new Tile[rowNum][colNum];
        if(level = "easy"){
            rowNum
        }
        generate(String level);
        firstTile = true;
    }

    public void click(boolean rightClick,int row, int col){
        if(!rightClick){
            if(firstTile){
                first = grid[row][col];
                populate();
                first.open(); //method from Tile class
                firstTile = false;
            }
            else{
                Tile leftClickedTile = grid[row][col];
                leftClickedTile.open();
            }
        }
        else{
            Tile leftClickedTile = grid[row][col];
            leftClickedTile.toggleFlag();
        }
    }

    //only activated when there is newGame(); creates an emptyTile Board
    public void generate(String level){
            firstTile = true;
            for(int i=0; i<rowNum; i++){
                for(int j=0; j<colNum; j++){
                    grid[i][j]= EmptyTile;
                }
            }
    }

    public void populate(){

    }
}
