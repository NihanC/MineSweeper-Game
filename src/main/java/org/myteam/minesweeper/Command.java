package org.myteam.minesweeper;

public class Command {
   private String command;
   private int row;
   private int column;
   private boolean valid;

    public Command(String command, int row, int column, boolean valid) {
        this.command = command;
        this.row = row;
        this.column = column;
        this.valid = valid;
    }

    public String getCommand() {
        return command;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isValid() {
        return valid;
    }
}
