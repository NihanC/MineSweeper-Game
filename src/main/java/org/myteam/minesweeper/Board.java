package org.myteam.minesweeper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class Board {
    private Tile[][] grid;
    private boolean firstTile;
    private int nrOfMines;
    private ArrayList<Tile> minedTiles;
    private Tile first;
    private int rowNum;
    private int colNum;


    public Board(level){
        grid = new Tile[rowNum][colNum];
        ArrayList<Tile> minedTiles = new ArrayList<>();
        if(level = "easy"){
            rowNum = 4;
            colNum = 4;
        }
        else if(level = "medium"){
            rowNum = 8;
            colNum = 10;
        }
        else{
            rowNum = 16;
            colNum = 16;
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

    public boolean isInMinedTiles(int r, int c){
        if(grid[r][c] == Mine){
            return true;
        }
        return false;
    }

    public void populate(){
        Random r= new Random();
        int i = r.nextInt(rowNum);
        int j = r.nextInt(colNum);//if bound is 100, 100 is not included. from 0 to 99

        for(int mineGenerated=0; mineGenerated<nrOfMines; mineGenerated++){
            while((grid[i][j]==first)||(isInMinedTiles(i,j))) {
                    i = r.nextInt(rowNum);
                    j = r.nextInt(colNum);
                }
            grid[i][j] = Mine;}
    }

    public static Tile[] getGrid(){return grid;}
    public boolean getFirstTile(){return firstTile;}
    public int getNrOfMines(){return nrOfMines;}
    public Tile getFirst() {return first;}
    public int getRowNum(){return rowNum;}
    public int getColNum(){return colNum;}
    public ArrayList<Tile> getMinedTiles(){return minedTiles;}
}
