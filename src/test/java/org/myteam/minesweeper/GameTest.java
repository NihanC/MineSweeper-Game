package org.myteam.minesweeper;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    //Generate the correct world according to customised parameters,
    // namely, row, column, and number of mines: no test because we don't use a
    // constructor o method like: new Board(rows, cols, mines)


    //Generate the correct world according to difficulty
    @Test
    public void testWorldGeneration() {
        Board easyBoard = new Board(Level.EASY);
        assertEquals(8, easyBoard.getRowNum());
        assertEquals(8, easyBoard.getColNum());

        Board mediumBoard = new Board(Level.MEDIUM);
        assertEquals(12, mediumBoard.getRowNum());
        assertEquals(12, mediumBoard.getColNum());

        Board hardBoard = new Board(Level.HARD);
        assertEquals(16, hardBoard.getRowNum());
        assertEquals(16, hardBoard.getColNum());
    }


    private void testMinesForLevel(Level level) {
        Random rand = new Random();

        for (int attempt = 0; attempt < 100; attempt++) {
            Game game= new Game(level);
            Board board =game.getBoard();

            int rows = board.getRowNum();
            int cols = board.getColNum();
            int firstRow = rand.nextInt(board.getRowNum());
            int firstCol = rand.nextInt(board.getColNum());

            board.click(false,firstRow,firstCol,game);

            int mineCount = 0;

            for (int i = 0; i < rows; i++){
                for (int j = 0; j < cols; j++){
                    if (board.getGrid()[i][j] instanceof Mine){
                        mineCount++;
                    }
                }
            }

            assertEquals(board.getNrOfMines(), mineCount);
        }
    }

    //Allocate mines (1)
    @Test
    public void testMines() {
        testMinesForLevel(Level.EASY);
        testMinesForLevel(Level.MEDIUM);
        testMinesForLevel(Level.HARD);
    }


    private void testMineAllocationForLevel(Level level) {
        Random rand = new Random();

        for (int attempt = 0; attempt < 100; attempt++) {
            Game game= new Game(level);
            Board board = game.getBoard();

            int firstRow = rand.nextInt(board.getRowNum());
            int firstCol = rand.nextInt(board.getColNum());

            board.click(false,firstRow,firstCol,game);

            for (int i = firstRow - 1; i <= firstRow + 1; i++) {
                for (int j = firstCol - 1; j <= firstCol + 1; j++) {
                    if (i >= 0 && i < board.getRowNum() && j >= 0 && j < board.getColNum()) {
                        assertFalse(board.getGrid()[i][j] instanceof Mine);
                    }
                }
            }
        }
    }

    //Allocate mines (2); First tile rule (2)
    //This test verifies that the first clicked tile is not a mine,
    //and that no mines are placed in its immediate neighbours (a feature we chose
    //to implement)
    @Test
    public void testMineAllocation() {
        testMineAllocationForLevel(Level.EASY);
        testMineAllocationForLevel(Level.MEDIUM);
        testMineAllocationForLevel(Level.HARD);
    }


    //Update numbered tiles
    @Test
    public void testUpdateNumberedTiles(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);

        boolean foundNum= false;

        outer:
        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(board.getGrid()[i][j] instanceof NumberTile){
                    NumberTile nTile= (NumberTile) board.getGrid()[i][j];
                    assertEquals(board.countAdjacentMines(i, j), nTile.getValue());
                    foundNum=true;
                    break outer;
                }
            }
        }
        assertTrue(foundNum);
    }

    //Flag, unflag an unopened tile
    @Test
    public void testFlagAndUnflag(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        int flagsBefore= game.getFlagsLeft();

        board.click(true, 1, 1, game);

        assertTrue(board.getGrid()[1][1].isFlagged());
        assertEquals(flagsBefore-1, game.getFlagsLeft());

        board.click(true, 1, 1, game);
        assertFalse(board.getGrid()[1][1].isFlagged());
        assertEquals(flagsBefore, game.getFlagsLeft());

    }

    //Output the board: no test because we use JavaFX


    //Open a tile with explosive neighbours
    @Test
    public void testOpenTileWithExplosiveNeighbours(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);

        boolean foundNum=false;

        outer:
        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(board.getGrid()[i][j] instanceof NumberTile && !board.getGrid()[i][j].isRevealed()){
                    board.click(false, i, j, game);
                    assertTrue(board.getGrid()[i][j].isRevealed());
                    assertFalse(game.isGameOver());
                    foundNum=true;
                    break outer;
                }
            }
        }
        assertTrue(foundNum);
    }

    //Open a tile without explosive neighbours
    @Test
    public void testOpenTileWithoutExplosiveNeighbours(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);

        boolean foundEmpty=false;

        outer:
        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(board.getGrid()[i][j] instanceof EmptyTile && !board.getGrid()[i][j].isRevealed()){
                    board.click(false, i, j, game);
                    assertTrue(board.getGrid()[i][j].isRevealed());
                    assertFalse(game.isGameOver());
                    foundEmpty=true;
                    break outer;
                }
            }
        }
        assertTrue(foundEmpty);
    }


    //Open a mine; Game lost (2)
    @Test
    public void testOpenMineEndsGame(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);

        boolean mineClicked= false;

        outer:
        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(board.getGrid()[i][j] instanceof Mine && !board.getGrid()[i][j].isFlagged()){
                    board.click(false, i, j, game);
                    mineClicked=true;
                    break outer;
                }
            }
        }
        assertTrue(mineClicked);
        assertTrue(game.isGameOver());
    }

    //Flag an opened tile
    @Test
    public void testFlagOpenedTile(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);
        board.click(true, 0, 0, game);

        assertFalse(board.getGrid()[0][0].isFlagged());
    }

    //First tile rule (1)
    @Test
    public void testFirstTileRule(){
        Random rand= new Random();

        for(int attempt=0; attempt<100; attempt++){
            Game game= new Game(Level.EASY);
            Board board= game.getBoard();

            int row= rand.nextInt(board.getRowNum());
            int col= rand.nextInt(board.getColNum());

            board.click(false, row, col, game);

            assertFalse(board.getGrid()[row][col] instanceof Mine);
            assertFalse(game.isGameOver());
        }


    }

    //Game lost (1)
    @Test
    public void testGameLostRevealsAllTiles(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);

        boolean mineClicked= false;

        outer:
        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(board.getGrid()[i][j] instanceof Mine && !board.getGrid()[i][j].isFlagged()){
                    board.click(false, i, j, game);
                    mineClicked=true;
                    break outer;
                }
            }
        }

        assertTrue(mineClicked);
        assertTrue(game.isGameOver());

        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                assertTrue(board.getGrid()[i][j].isRevealed());
            }
        }
    }

    //Game win
    @Test
    public void testGameWin(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);

        int lastRow=-1;
        int lastCol= -1;

        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(!(board.getGrid()[i][j] instanceof Mine)){
                    lastRow=i;
                    lastCol=j;
                }
            }
        }

        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(!(board.getGrid()[i][j] instanceof Mine)){
                    if(!(i==lastRow && j==lastCol)){
                        board.getGrid()[i][j].setRevealed(true);
                    }
                }
            }
        }
        board.click(false, lastRow,lastCol,game);

        assertTrue(game.isWon());
        assertFalse(game.isGameOver());
    }


    //Interaction based on text commands: no test because we use JavaFX

    //--------------------------------------------

    //Other tests:
    @Test
    public void testRadarTile(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);

        int flagsBefore= game.getFlagsLeft();
        boolean foundRadar= false;

        outer:
        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(board.getGrid()[i][j] instanceof RadarTile){
                    board.click(false, i, j, game);
                    assertTrue(board.getGrid()[i][j].isRevealed());
                    assertTrue(game.getFlagsLeft()<=flagsBefore);
                    assertFalse(game.isGameOver());
                    foundRadar=true;
                    break outer;
                }
            }
        }
        assertTrue(foundRadar);
    }

    @Test
    public void testSpecialTileGeneratesEquation(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(false, 0, 0, game);

        boolean foundSpecial=false;

        outer:
        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(board.getGrid()[i][j] instanceof SpecialEquationTile){
                    SpecialEquationTile s= (SpecialEquationTile) board.getGrid()[i][j];
                    board.click(false, i, j, game);

                    assertTrue(board.getGrid()[i][j].isRevealed());
                    assertNotNull(s.getEquation());
                    assertTrue(s.getEquation().equals("sum") || s.getEquation().equals("minus"));
                    foundSpecial=true;
                    break outer;
                }
            }
        }
        assertTrue(foundSpecial);
    }

    @Test
    public void testEmptyTileOpens(){
        Game game= new Game(Level.EASY);
        EmptyTile tile= new EmptyTile(0,0);
        tile.open(game.getBoard(), game);
        assertTrue(tile.isRevealed());
    }

    @Test
    public void testFlaggedTileDoesNotOpen(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        board.click(true, 2, 2, game);
        board.click(false, 2, 2, game);

        assertTrue(board.getGrid()[2][2].isFlagged());
        assertFalse(board.getGrid()[2][2].isRevealed());
    }

    @Test
    public void testCannotPlaceFlagWhenNoFlagsLeft(){
        Game game= new Game(Level.EASY);
        Board board= game.getBoard();

        int maxFlags= game.getFlagsLeft();
        int count=0;

        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(count<maxFlags){
                    board.click(true, i, j, game);
                    if(board.getGrid()[i][j].isFlagged()){
                        count++;
                    }
                }
            }
        }

        int flagsLeftBefore= game.getFlagsLeft();

        outer:
        for(int i=0; i<board.getRowNum(); i++){
            for(int j=0; j<board.getColNum();j++){
                if(!(board.getGrid()[i][j].isFlagged())){
                    board.click(true, i, j, game);
                    break outer;
                }
            }
        }

        assertEquals(flagsLeftBefore, game.getFlagsLeft());
    }
}
