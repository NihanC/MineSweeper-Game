import org.junit.jupiter.api.Test;
import org.myteam.minesweeper.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void testMines() {
        Random rand = new Random();
        for (int attempt = 0; attempt < 100; attempt++) {
            Board board = new Board(Level.EASY);
            int rows = board.getRowNum();
            int cols = board.getColNum();
            int firstRow = rand.nextInt(board.getRowNum());
            int firstCol = rand.nextInt(board.getColNum());
            board.click(false,firstRow,firstCol,new Game(Level.EASY));
            int mineCount = 0;
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    if (board.getGrid()[i][j] instanceof Mine)
                        mineCount++;

            assertEquals(board.getNrOfMines(), mineCount);
        }
    }
}
