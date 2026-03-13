package org.myteam.minesweeper;

public class Command {
   private String command;
   private int row;
   private int column;
   private boolean validity;

    public Command(String command, int row, int column, boolean validity) {
        this.command = command;
        this.row = row;
        this.column = column;
        this.validity = validity;
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

    public boolean isValidity() {
        return validity;
    }
}
