package org.myteam.minesweeper;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TextUI {
    private Game game;
    private Scanner scanner;
    private Set<SpecialEquationTile> handledEquations;

    public TextUI() {
        this.scanner = new Scanner(System.in);
        this.handledEquations = new HashSet<>();
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("=== MINESWEEPER ===");
            System.out.println("Choose a level: easy, medium, hard (or 'quit' to exit)");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit")) {
                running = false;
                continue;
            }

            Level level;
            if (input.equals("easy")) {
                level = Level.EASY;
            } else if (input.equals("medium")) {
                level = Level.MEDIUM;
            } else if (input.equals("hard")) {
                level = Level.HARD;
            } else {
                System.out.println("Invalid level. Try again.");
                continue;
            }

            game = new Game(level);
            handledEquations.clear();
            playGame();
        }
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private void playGame() {
        while (!game.isGameOver() && !game.isWon()) {
            printBoard();
            System.out.println("Flags left: " + game.getFlagsLeft() + "  |  Time: " + game.getTime() + "s");
            System.out.println("Enter command: 'o row col' to open, 'f row col' to flag (or 'quit')");
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit")) {
                return;
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 3) {
                System.out.println("Invalid input. Use format: o row col  or  f row col");
                continue;
            }

            String command = parts[0];
            int row, col;
            try {
                row = Integer.parseInt(parts[1]);
                col = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Row and column must be numbers.");
                continue;
            }

            if (row < 0 || row >= game.getBoard().getRowNum()
                    || col < 0 || col >= game.getBoard().getColNum()) {
                System.out.println("Coordinates out of bounds.");
                continue;
            }

            if (command.equals("o")) {
                game.getBoard().click(false, row, col, game);
                checkForEquationTiles();
            } else if (command.equals("f")) {
                game.getBoard().click(true, row, col, game);
            } else {
                System.out.println("Unknown command. Use 'o' to open or 'f' to flag.");
            }
        }

        printBoard();
        if (game.isWon()) {
            System.out.println("You won! Time: " + game.getTime() + "s");
        } else {
            System.out.println("Game over.");
        }
        System.out.println();
    }

    private void checkForEquationTiles() {
        if (game.isGameOver()) return;

        Tile[][] grid = game.getBoard().getGrid();
        for (int i = 0; i < game.getBoard().getRowNum(); i++) {
            for (int j = 0; j < game.getBoard().getColNum(); j++) {
                Tile tile = grid[i][j];
                if (tile instanceof SpecialEquationTile && tile.isRevealed()) {
                    SpecialEquationTile s = (SpecialEquationTile) tile;
                    if (s.getEquation() != null && !handledEquations.contains(s)) {
                        handledEquations.add(s);
                        handleEquationChallenge(s);
                    }
                }
            }
        }
    }

    private void handleEquationChallenge(SpecialEquationTile s) {
        int num1 = s.getNum1();
        int num2 = s.getNum2();
        String equation = s.getEquation();
        String symbol = equation.equals("sum") ? "+" : "-";
        int correctAnswer = equation.equals("sum") ? num1 + num2 : num1 - num2;

        System.out.println();
        System.out.println("EQUATION CHALLENGE! Solve to continue!");
        System.out.println("What is " + num1 + " " + symbol + " " + num2 + " ?");
        System.out.print("> ");

        String answer = scanner.nextLine().trim();

        try {
            int playerAnswer = Integer.parseInt(answer);
            if (playerAnswer == correctAnswer) {
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong! Game over.");
                game.gameOver();
                game.getBoard().revealAllTiles();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number! Game over.");
            game.gameOver();
            game.getBoard().revealAllTiles();
        }
    }

    private void printBoard() {
        Tile[][] grid = game.getBoard().getGrid();
        int rows = game.getBoard().getRowNum();
        int cols = game.getBoard().getColNum();

        // Column headers
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.printf("%3d", j);
        }
        System.out.println();

        // Separator
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.print("---");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.printf("%3d|", i);
            for (int j = 0; j < cols; j++) {
                Tile tile = grid[i][j];
                String display;

                if (tile.isRevealed()) {
                    if (tile instanceof Mine) {
                        display = " * ";
                    } else if (tile instanceof SpecialEquationTile) {
                        display = " S ";
                    } else if (tile instanceof RadarTile) {
                        display = " R ";
                    } else if (tile instanceof NumberTile) {
                        display = " " + ((NumberTile) tile).getValue() + " ";
                    } else {
                        display = "   ";
                    }
                } else if (tile.isFlagged()) {
                    display = " F ";
                } else {
                    display = " . ";
                }

                System.out.print(display);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new TextUI().run();
    }
}
