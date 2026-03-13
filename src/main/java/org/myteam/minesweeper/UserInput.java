package org.myteam.minesweeper;

public class UserInput {

    public Command open(int row, int column, boolean validity){
        return new Command("open", row, column, true);

    }

    public Command flag(int row, int column, boolean click){
        return new Command("flag", row, column, true);

    }

    public Command newGame(int row, int column, boolean click){
        return new Command("new", -1, -1, true);

    }
}
