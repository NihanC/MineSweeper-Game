package org.myteam.minesweeper;

public class UserInput {

    public Command open(int row, int column){
        return new Command("open", row, column, true);

    }

    public Command flag(int row, int column){
        return new Command("flag", row, column, true);

    }

    public Command newGame(){
        return new Command("new", -1, -1, true);

    }
}
