package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.digitalpersona.uareu.*;
import com.digitalpersona.javapos.*;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}