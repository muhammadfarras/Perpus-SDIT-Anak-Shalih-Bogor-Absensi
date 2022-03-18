package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import com.farras.sdit.perpussditanakshalihbogorabsensi.MainApplication;
import com.farras.sdit.perpussditanakshalihbogorabsensi.utils.PopupCustom;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private double xOffset; // x offset value drag able undecorated stage
    private double yOffset; // y offset value drag able undecorated stage
    private Stage dashBoardStage; // inisias dashboard stage
    private MainApplication mainApplication; // initiation main application, new stage
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("strings", Locale.getDefault());
    @FXML
    private Button buttonLogin;
    @FXML
    private TextField textUName;
    @FXML
    private PasswordField textPasswd;



//    Lebih indah gk gerak2
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

    public void CloseStageAfterLoginSucceededAnOpenAnotherStage(Stage loginStage){

        this.buttonLogin.setOnMouseClicked(e ->{
            String uname = this.textUName.getText();
            String passwd = this.textPasswd.getText();
            PopupCustom popupCustom = new PopupCustom(loginStage);
            if (uname.isBlank() | passwd.isBlank()){
                popupCustom.setMessage(resourceBundle.getString("error_from_is_blank"));
                popupCustom.setCode(PopupCustom.ALERT);
                popupCustom.showPopup(); // show popup custom if required field is blank
            }
            else {
                // Is username and password are existing in Data base
                if (uname.equals("farras") & passwd.equals("farras2712")){
                    loginStage.close(); // close current stage

                    try {
                        mainApplication = new MainApplication(); // Open main application
                        dashBoardStage = new Stage(); // Creating new stage
                        mainApplication.start(dashBoardStage); // start another stage

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    popupCustom.setMessage(resourceBundle.getString("error_login_user_doesnt_exist"));
                    popupCustom.setCode(PopupCustom.WARNING);
                    popupCustom.showPopup(); // show popup custom if required field is blank
                }



            }



        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
