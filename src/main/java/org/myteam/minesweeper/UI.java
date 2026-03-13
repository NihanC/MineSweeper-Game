package org.myteam.minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.event.EventHandler;


public class UI extends Application{

    private Label timer;
    private Label instructions;
    private String level;
    private Button[][] theButtons;
    private int rows;
    private int columns;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");

        GridPane firstGrid = new GridPane();
        firstGrid.setHgap(10);
        firstGrid.setVgap(10);
        firstGrid.setAlignment(Pos.CENTER);


        Label levelL= new Label("Level: ");
        TextField chooseL= new TextField();
        firstGrid.add(levelL, 0, 0);
        firstGrid.add(chooseL, 1, 0);


        Scene scene1 = new Scene(firstGrid, 400, 200);
        primaryStage.setScene(scene1);



        Scene easyScene= new Scene(easyGrid, 800, 800);
        Scene mediumScene= new Scene(mediumGrid, 800, 800);
        Scene hardScene= new Scene(hardGrid, 800, 800);


        chooseL.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("easy")){
                level="easy";
                primaryStage.setScene(easyScene);
            }
            else if(newValue.equals("medium")){
                level="medium";
                primaryStage.setScene(mediumScene);
            }
            else if(newValue.equals("hard")){
                level="hard";
                primaryStage.setScene(hardScene);
            }
        });

        timer = new Label("Time:");
        instructions = new Label("Left click: open\nRight click: flag");


        GridPane gridPane= new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(timer, 0, 0);
        gridPane.add(instructions, 0, 1);



        Button but= new Button();



        gridPane.add(highestHpaButton, 0, 1);
        gridPane.add(highestHpaLabel, 1, 1);

        gridPane.add(unitTextL, 0, 2);
        gridPane.add(unitTF, 1, 2);
        gridPane.add(specificUnitButton, 0, 3);
        gridPane.add(specificUnitLabel, 1, 3);

        gridPane.add(locationInsertL, 0, 5);
        gridPane.add(locationInsertTF, 1, 5);
        gridPane.add(unitInsertL, 0, 6);
        gridPane.add(unitInsertTF, 1, 6);
        gridPane.add(lastValueInsertL, 0, 7);
        gridPane.add(lastValueInsertTF, 1, 7);
        gridPane.add(max_ValueInsertL, 0, 8);
        gridPane.add(max_ValueInsertTF, 1, 8);
        gridPane.add(insertButton, 0, 9);
        gridPane.add(insertLabel, 1, 9);


        gridPane.add(sensorIdL, 0, 11);
        gridPane.add(sensorIdField, 1, 11);
        gridPane.add(sensorInfoButton, 0, 12);


        gridPane.add(idL, 0, 13);
        gridPane.add(theIdLabel, 1, 13);
        gridPane.add(locationL, 0, 14);
        gridPane.add(theLocationLabel, 1, 14);
        gridPane.add(measurementUnitsL, 0, 15);
        gridPane.add(theUnitLabel, 1, 15);
        gridPane.add(lastValueL, 0, 16);
        gridPane.add(theLastValueLabel, 1, 16);
        gridPane.add(max_ValueL, 0, 17);
        gridPane.add(theMax_ValueLabel, 1, 17);

        celsiusButton.setOnAction(e -> {
            String ids=api.getTemperatureSensorIds();
            celsiusIdsLabel.setText(ids);
        });

        highestHpaButton.setOnAction(e -> {
            String location=api.getHighestHPaLocation();
            highestHpaLabel.setText(location);
        });

        specificUnitButton.setOnAction(e -> {
            String unit=unitTF.getText();
            String location= api.getHighestLocationForUnit(unit);
            specificUnitLabel.setText(location);
        });

        insertButton.setOnAction(e -> {
            String location=locationInsertTF.getText();
            String unit= unitInsertTF.getText();
            String lvalue=lastValueInsertTF.getText();
            String mvalue= max_ValueInsertTF.getText();

            String result=api.insertSensor(location, unit, lvalue, mvalue);
            insertLabel.setText(result);
        });

        sensorInfoButton.setOnAction(e -> {
            String id=sensorIdField.getText();
            String[] info= api.getSensorInfo(id);

            theIdLabel.setText(info[0]);
            theLocationLabel.setText(info[1]);
            theUnitLabel.setText(info[2]);
            theLastValueLabel.setText(info[3]);
            theMax_ValueLabel.setText(info[4]);
        });

        Scene scene = new Scene(gridPane, 850, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void generateFromLevel(String level)
    {
        if(level.equals("easy")){
            rows=10;
            columns=10;
        }
        else if(level.equals("medium")){
            rows=20;
            columns=20;
        }
        else if(level.equals("hard")){
            rows=30;
            columns=30;
        }

        theButtons=new Button[rows][columns];
        for(int i=0; i<rows; i++){
            for(int e=0; i<columns;e++){
                Button aBut= new Button();
                aBut.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        if(event.getButton() == MouseButton.PRIMARY){
//
                        }
                        else if(event.getButton() == MouseButton.SECONDARY){
//                  Type code to set flag here
                        }
                    }
                });
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
//l
button.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton() == MouseButton.SECONDARY){
//                  Type code to set flag here
            }
        }
    });
}
