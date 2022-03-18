package com.farras.sdit.perpussditanakshalihbogorabsensi.utils;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class PopupCustom {

    public static final String ALERT = "alert-text";
    public static final String SUCCESS = "success-text";
    public static final String WARNING = "warning-text";
    public static final String INFO = "info-text";

    private String code;
    private String message;
    private Popup myCustomPopup;
    private final StackPane stackPane = new StackPane();
    private Stage stage;

    public PopupCustom (String codePopUp, String message,Stage stage){
        this.code = codePopUp;
        this.message = message;
        this.stage = stage;
    }
    public PopupCustom(Stage stage){
        this.stage = stage;
    }

    protected void MadePopup (){
        Popup popup = new Popup(); // object popup
        Label label = new Label(this.message); // object label
        PauseTransition delay = new PauseTransition(Duration.seconds(2)); // object pause transition
        this.stackPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/popup-style.css")).toExternalForm());

        switch (this.code){
            case ALERT -> {
                this.code = ALERT;
                label.setTextFill(Color.WHITE);
            }
            case SUCCESS -> {
                this.code = SUCCESS;
                label.setTextFill(Color.WHITE);
            }
            case INFO -> {
                this.code = INFO;
                label.setTextFill(Color.WHITE);
            }
            case WARNING -> {
                this.code = WARNING;
            }
        }



        label.setWrapText(true); // set wrap text to true, avoid dots if text more than 300 (preferred width)
        label.setAlignment(Pos.CENTER); // set text to center of label
        this.stackPane.getStyleClass().add(code); // add style to box based on code given
        this.stackPane.setPadding(new Insets(10,10,10,10)); // set padding to 10
        this.stackPane.getChildren().add(label); // add label to stack pane
        this.stackPane.setPrefWidth(300); // set preferred width to 300
        this.stackPane.setMaxHeight(100); // set max width to 100
        double xPositon = this.stage.getX() + this.stage.getWidth() - this.stackPane.getPrefWidth() - 30;

        popup.setX(xPositon); // set x position to corner right of stage
        popup.setY(this.stage.getY()); // set popup y position to top of the stage
        popup.getContent().add(this.stackPane); // adding stack pane to popup
        delay.setOnFinished(e->popup.hide()); // Set on finished if time are time up
        delay.play();
        this.myCustomPopup = popup; // pass customize popup to property
    }

    public void showPopup (){
        MadePopup(); // run MadePopup function
        this.myCustomPopup.show(stage); // show ups the popup
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
