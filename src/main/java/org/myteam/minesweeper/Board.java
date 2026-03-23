package org.myteam.minesweeper;
import java.util.ArrayList;
import java.util.Random;

import static org.myteam.minesweeper.Level.*;

public class Board {
    private Tile[][] grid;
    private boolean firstTile;
    private int nrOfMines;
    private ArrayList<Mine> minedTiles;
    private Tile first;
    private int rowNum;
    private int colNum;
    private Level level;


    public Board(Level level){
        this.minedTiles = new ArrayList<>();
        rowNum = level.getRows();
        colNum = level.getCols();
        nrOfMines = level.getMines();
        this.level = level;
        grid = new Tile[rowNum][colNum];
        generate(level);
    }

    public void click(boolean rightClick,int row, int col, Game game){
        if(!rightClick){
            if(firstTile){
                first = grid[row][col];
                populate();
                fillSpecial();
                putEquation();
                fillNumbers();
                grid[row][col].open(this, game);
                firstTile = false;
            }
            else{
                Tile leftClickedTile = grid[row][col];
                leftClickedTile.open(this, game);
            }
        }
        else{
            Tile leftClickedTile = grid[row][col];
            leftClickedTile.toggleFlag(this, game);
        }

        if(!game.isGameOver() && checkWin()){
            game.win();
        }
    }

    //only activated when there is newGame(); creates an emptyTile Board
    public void generate(Level level){
            firstTile = true;
            for(int i=0; i<rowNum; i++){
                for(int j=0; j<colNum; j++){
                    grid[i][j]= new EmptyTile(i, j);
                }
            }
    }

    public boolean isInMinedTiles(int r, int c){
        return grid[r][c] instanceof Mine;
    }

    public void populate(){
        Random r= new Random();//if bound is 100, 100 is not included. from 0 to 99

        for(int mineGenerated=0; mineGenerated<nrOfMines; mineGenerated++){
            int i = r.nextInt(rowNum);
            int j = r.nextInt(colNum);
            while((isNeighbourOfFirst(i,j))||(isInMinedTiles(i,j))) {
                    i = r.nextInt(rowNum);
                    j = r.nextInt(colNum);
            }
            Mine mine = new Mine(i,j);
            grid[i][j] = mine;
            minedTiles.add(mine);
        }
    }

    public void fillSpecial(){
        int countSpecial = 0;
        if(level == EASY){
            countSpecial = 2;
        }
        else if(level == MEDIUM){
            countSpecial = 8;
        }
        else{
            countSpecial = 24;
        }
        Random r= new Random();
        for(int c=0; c<countSpecial; c++){
            int i = r.nextInt(rowNum);
            int j = r.nextInt(colNum);
            while(((grid[i][j] instanceof Mine) || (grid[i][j] instanceof RadarTile))|| (grid[i][j]==first)) {
                i = r.nextInt(rowNum);
                j = r.nextInt(colNum);
            }
            grid[i][j] = new RadarTile(i,j);
        }
    }

    public void putEquation(){
        Random r= new Random();
            int i = r.nextInt(rowNum);
            int j = r.nextInt(colNum);
            while(((grid[i][j] instanceof Mine) || (grid[i][j] instanceof RadarTile))||(grid[i][j]==first)) {
                i = r.nextInt(rowNum);
                j = r.nextInt(colNum);
            }
            grid[i][j] = new SpecialEquationTile(i,j);
    }


    public boolean isNeighbourOfFirst(int i, int j) {
        return Math.abs(i - first.getRow()) <= 1 && Math.abs(j - first.getColumn()) <= 1;
    }

    public void openAdjacentTiles(int row, int col, Game game) {
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r >= 0 && r < rowNum && c >= 0 && c < colNum) {
                    grid[r][c].open(this, game);
                }
            }
        }
    }

    public int countAdjacentMines(int row, int col){
        int count=0;

        for(int i=Math.max(0, row-1); i<=Math.min(rowNum-1, row+1); i++){
            for(int j=Math.max(0, col-1); j<=Math.min(colNum-1, col+1); j++){
                if(!(i==row && j==col)&& grid[i][j] instanceof Mine){
                    count++;
                }
            }
        }
        return count;
    }

    public void fillNumbers(){
        for(int i=0; i<rowNum; i++){
            for(int j=0; j<colNum; j++){
                if((!(grid[i][j] instanceof Mine) && !(grid[i][j] instanceof RadarTile) && !(grid[i][j] instanceof SpecialEquationTile))&&!(grid[i][j]==first)){
                    int count= countAdjacentMines(i,j);
                    if(count>0){
                        grid[i][j]= new NumberTile(i, j, count);
                    } else {
                        grid[i][j] = new EmptyTile(i, j);
                    }
                }
            }
        }
    }

    public void revealAllTiles(){
        for(int i=0; i<rowNum; i++){
            for(int j=0; j<colNum; j++){
                grid[i][j].setRevealed(true);
            }
        }
    }

    public boolean checkWin(){
        for(int i=0; i<rowNum; i++){
            for(int j=0; j<colNum; j++){
                if(!(grid[i][j] instanceof Mine) && !grid[i][j].isRevealed()){
                    return false;
                }
            }
        }
        return true;
    }

    public Tile[][] getGrid(){return grid;}
    public boolean getFirstTile(){return firstTile;}
    public int getNrOfMines(){return nrOfMines;}
    public Tile getFirst() {return first;}
    public int getRowNum(){return rowNum;}
    public int getColNum(){return colNum;}
    public ArrayList<Mine> getMinedTiles(){return minedTiles;}
    public Level getLevel(){return level;}
}
