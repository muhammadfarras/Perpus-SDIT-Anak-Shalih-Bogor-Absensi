package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public VBox dashboard_child;
    @FXML
    public Line line_db;

    @FXML
    public Line line_db_enrollment;
    @FXML
    public VBox dashboard_child_enrollment;

    @FXML
    public Line line_db_book;
    @FXML
    public VBox dashboard_child_book;

    @FXML
    public Line line_db_haa;
    @FXML
    public VBox dashboard_child_haa;

    @FXML
    private Label enrollment_student;

    @FXML VBox main_form;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        enrollment_student.setOnMouseClicked(e->{
//            main_form.getChildren().add(FXMLLoader.load(getClass().getResource("/a"))
//            try {
//                Pane enrollmentStudent = FXMLLoader.load(getClass().getResource("/com/farras/sdit/perpussditanakshalihbogorabsensi/mm-student-view.fxml"));
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
            URL enroll = getClass().getResource("/com/farras/sdit/perpussditanakshalihbogorabsensi/mm-student-view.fxml");
            try {
                assert enroll != null;
                Pane enrollPane = FXMLLoader.load(enroll,resourceBundle);
                main_form.getChildren().removeAll(main_form.getChildren());
                main_form.getChildren().add(enrollPane);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

    }


}