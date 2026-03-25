package org.myteam.minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseButton;


public class UI extends Application{

    private Label timer;
    private Label instructions;
    private Label flags;

    private Scene startScene;
    private Stage mainStage;
    private Scene gameScene;

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
            gameScene = createGameScene(); // FIX 1: assign to field so equation scene can reference it
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

    private Scene createEquationScene(){
        Label challengeTimer = new Label("Time: 10");
        Label challengeInstructions = new Label("Solve this equation or die");
        Label num1 = new Label("What is " + eqNum1 + " ");
        Label symbol = new Label("");
        if (equation.equals("sum")) {
            symbol.setText("+");
        } else {
            symbol.setText("-");
        }

        Label num2 = new Label(" " + eqNum2);
        int sum = eqNum1 + eqNum2;
        int minus = eqNum1 - eqNum2;
        TextField answer = new TextField();

        HBox topBox = new HBox(20, challengeTimer, challengeInstructions);
        HBox middleBox = new HBox(num1, symbol, num2);
        HBox bottomBox = new HBox(answer);
        VBox vbox = new VBox(topBox, middleBox, bottomBox);
        topBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        int[] timeLeft = {10};
        javafx.animation.Timeline countdown = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
                    timeLeft[0]--;
                    challengeTimer.setText("Time: " + timeLeft[0]);
                    if (timeLeft[0] <= 0) {
                        eqNum1 = 0; eqNum2 = 0; equation = null; // reset so next S tile triggers correctly
                        game.gameOver();
                        mainStage.setScene(gameScene);
                        instructions.setText("Game over!");
                        game.getBoard().revealAllTiles();
                        updateBoard();
                    }
                })
        );
        countdown.setCycleCount(10);
        countdown.play();

        answer.textProperty().addListener((observable, oldValue, newValue) -> {
            if ((equation.equals("sum")) && (newValue.equals("" + sum))) {
                eqNum1 = 0; eqNum2 = 0; equation = null; // reset so next S tile triggers correctly
                countdown.stop();
                mainStage.setScene(gameScene);
            } else if ((equation.equals("minus")) && (newValue.equals("" + minus))) {
                eqNum1 = 0; eqNum2 = 0; equation = null; // reset so next S tile triggers correctly
                countdown.stop();
                mainStage.setScene(gameScene);
            }
        });

        return new Scene(root, 300, 200);
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

    private int eqNum1;
    private int eqNum2;
    private String equation;

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
                    } else if (tile instanceof EmptyTile) {
                        aButton.setText("/");
                    }
                    else if(tile instanceof SpecialEquationTile){
                        SpecialEquationTile s = (SpecialEquationTile) tile;
                        aButton.setText("S");
                        aButton.setOnAction(null);
                        if (eqNum1 == 0 && eqNum2 == 0  && !game.isGameOver()) {
                            eqNum1 = s.getNum1();
                            eqNum2 = s.getNum2();
                            equation = s.getEquation();
                            mainStage.setScene(createEquationScene());
                        }
                    }
                    else if(tile instanceof RadarTile){
                        aButton.setText("R");
                    }
                    else{
                        aButton.setText("");
                    }
                }
                else if(tile.isFlagged()){
                    if(game.getFlagsLeft()>=0){
                        aButton.setText("F");}
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