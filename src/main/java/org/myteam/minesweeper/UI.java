package org.myteam.minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseButton;


public class UI extends Application{

    private Label timer;
    private Label instructions;
    private Label flags;

    private Scene startScene;
    private Stage mainStage;

    private Button[][] theButtons;

    private Game game;
    private UserInput userInput;

    private GridPane boardG;


    @Override
    public void start(Stage primaryStage) {
        mainStage=primaryStage;
        userInput= new UserInput();

        GridPane firstGrid = new GridPane();
        firstGrid.setHgap(10);
        firstGrid.setVgap(10);
        firstGrid.setAlignment(Pos.CENTER);


        Label levelL= new Label("Level: ");
        TextField chooseL= new TextField();
        Button startButton= new Button("Start");

        firstGrid.add(levelL, 0, 0);
        firstGrid.add(chooseL, 1, 0);
        firstGrid.add(startButton, 1, 1);

        startScene = new Scene(firstGrid, 400, 200);

        startButton.setOnAction(e -> {
            String inputS= chooseL.getText().trim().toLowerCase();

            Level chosenLevel;

            if(inputS.equals("easy")){
                chosenLevel=Level.EASY;
            }
            else if(inputS.equals("medium")){
                chosenLevel=Level.MEDIUM;
            }
            else if(inputS.equals("hard")){
                chosenLevel=Level.HARD;
            }
            else{
                chooseL.setText("Write: easy, medium or hard");
                return;
            }

            game= new Game(chosenLevel);
            Scene gameScene=createGameScene();
            primaryStage.setScene(gameScene);
            updateBoard();
        });

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private Scene createGameScene(){
        BorderPane root= new BorderPane();

        timer= new Label("Time: 0");
        instructions= new Label("Left click: open   Right click: flag/unflag");
        flags= new Label("Flags left :"+game.getFlagsLeft());

        Button newGameButton= new Button("New Game");
        HBox topBox= new HBox(20, timer,flags, instructions, newGameButton);
        topBox.setAlignment(Pos.CENTER);

        newGameButton.setOnAction(e -> {
            mainStage.setScene(startScene);
        });

        boardG=new GridPane();
        boardG.setHgap(2);
        boardG.setVgap(2);
        boardG.setAlignment(Pos.CENTER);

        int rows= game.getBoard().getRowNum();
        int columns= game.getBoard().getColNum();

        theButtons= new Button[rows][columns];

        for(int i=0; i<rows; i++){
            for(int j=0; j<columns;j++){
                int row=i;
                int column=j;

                Button aBut= new Button();
                aBut.setPrefSize(35,35);

                aBut.setOnMouseClicked(event ->{
                    Command command;

                    if(event.getButton()==MouseButton.PRIMARY){
                        command=userInput.open(row, column);
                    }
                    else if(event.getButton()==MouseButton.SECONDARY){
                        command=userInput.flag(row, column);
                    }
                    else{
                        return;
                    }
                    processCommand(command);
                    updateBoard();
                });

                theButtons[i][j]=aBut;
                boardG.add(aBut, j, i);
            }
        }

        root.setTop(topBox);
        root.setCenter(boardG);

        return new Scene(root,900, 700);
    }

    private void processCommand(Command command){
        if(!command.isValid()){
            return;
        }
        if(command.getCommand().equals("open")){
            game.getBoard().click(false, command.getRow(), command.getColumn(), game);
        }
        else if(command.getCommand().equals("flag")){
            game.getBoard().click(true, command.getRow(), command.getColumn(), game);
        }

        flags.setText("Flags left: "+game.getFlagsLeft());
        timer.setText("Time: "+game.getTime());

        if(game.isGameOver()){
            instructions.setText("Game over!");
        }
        else if(game.isWon()){
            instructions.setText("You won!");
        }
    }

    private void updateBoard(){
        Tile[][] grid= game.getBoard().getGrid();

        for(int i=0; i<game.getBoard().getRowNum();i++){
            for(int j=0; j<game.getBoard().getColNum(); j++){
                Tile tile=grid[i][j];
                Button aButton=theButtons[i][j];

                if(tile.isRevealed()){
                    if(tile instanceof Mine){
                        aButton.setText("M");
                    }
                    else if(tile instanceof NumberTile){
                        NumberTile n=(NumberTile) tile;
                        aButton.setText(String.valueOf(n.getValue()));
                    }
                    else{
                        aButton.setText("");
                    }
                }
                else if(tile.isFlagged()){
                    aButton.setText("F");
                }
                else {
                    aButton.setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
