package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
    private double xOffset; // x offset value drag able undecorated stage
    private double yOffset; // y offset value drag able undecorated stage

    public void AllowdUndecoratedStageMove(Scene scene, Stage stage){
        scene.setOnMousePressed(mouseEvent -> {
            xOffset = stage.getX() - mouseEvent.getScreenX();
            yOffset = stage.getY() - mouseEvent.getScreenY();
        });

        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + xOffset);
            stage.setY(mouseEvent.getScreenY() + yOffset);
        });


    }
}
