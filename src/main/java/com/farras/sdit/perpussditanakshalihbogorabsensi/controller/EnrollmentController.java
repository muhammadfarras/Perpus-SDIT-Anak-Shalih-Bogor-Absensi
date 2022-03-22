package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import com.farras.sdit.perpussditanakshalihbogorabsensi.EnrollmentApplication;
import com.farras.sdit.perpussditanakshalihbogorabsensi.utils.PopupCustom;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EnrollmentController implements Initializable {

    private Stage stage;

    @FXML
    Button button_enrollment;
    @FXML
    TextField member_name;
    @FXML
    TextField member_id;
    @FXML
    ChoiceBox member_gender;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        this.stage = new EnrollmentApplication().getStage();
        button_enrollment.setOnMouseClicked(mouseEvent -> {

            if (member_name.getText().isBlank() | member_id.getText().isBlank() | member_gender.getValue() == null){
                PopupCustom popupCustom = new PopupCustom(this.stage);
                popupCustom.setCode(PopupCustom.WARNING);
                popupCustom.setMessage("Test test test");

                popupCustom.showPopup();
            }

        });
    }
}
