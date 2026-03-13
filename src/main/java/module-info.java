module org.myteam.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.myteam.minesweeper to javafx.fxml;
    exports org.myteam.minesweeper;
}