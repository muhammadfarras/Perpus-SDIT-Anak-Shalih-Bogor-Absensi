package com.farras.sdit.perpussditanakshalihbogorabsensi.utils;

import eu.hansolo.tilesfx.events.BoundsEventListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopupCustom {

    public static final String ALERT = "ALERT";

    private String code;
    private String message;
    private Boolean autoHide = true; // the default value is true
    private Popup myCustomPopup;

    public PopupCustom (String codePopUp, String message, Boolean autoHide){
        this.code = codePopUp;
        this.message = message;
    }

    public PopupCustom (String codePopUp, String message){
        this.code = codePopUp;
        this.message = message;
    }

    protected void MadePopup (){

        Popup popup = new Popup();
        popup.setAutoHide(autoHide);
        StackPane stackPane = new StackPane();
        Label label = new Label(this.message);
        stackPane.setPadding(new Insets(10,10,10,10));
        stackPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        stackPane.getChildren().add(label);
        popup.getContent().add(stackPane);
        this.myCustomPopup = popup;
    }

    public void showPopup (Stage stage){
        MadePopup();
        this.myCustomPopup.show(stage);
    }

    public Boolean getAutoHide() {
        return autoHide;
    }

    public void setAutoHide(Boolean autoHide) {
        this.autoHide = autoHide;
    }
}
