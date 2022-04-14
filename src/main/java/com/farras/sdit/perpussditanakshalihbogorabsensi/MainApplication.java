package com.farras.sdit.perpussditanakshalihbogorabsensi;


import com.farras.sdit.perpussditanakshalihbogorabsensi.controller.MainController;
import com.farras.sdit.perpussditanakshalihbogorabsensi.controller.MemberStudentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainApplication extends Application {

    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        ResourceBundle myBundle = ResourceBundle.getBundle("strings", Locale.getDefault());
        stage.initStyle(StageStyle.DECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"),myBundle);

//        Set another controller just in case it needed
//        fxmlLoader.setController( new FXMLLoader(MemberStudentController.class.getResource("mm-student-view.fxml"),myBundle).getController());

        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        MainController mainController = (MainController) fxmlLoader.getController();


        stage.setOnShown(e->{
            // crate a line after stage are shown, cause we need value heigh from Vbox
            // but the value are established after stage show
            double rationOfLine = 0.75;
            mainController.line_db.setEndY(mainController.dashboard_child.getHeight() * rationOfLine);
            mainController.line_db_enrollment.setEndY(mainController.dashboard_child_enrollment.getHeight() * rationOfLine);
            mainController.line_db_book.setEndY(mainController.dashboard_child_book.getHeight() * rationOfLine);
            mainController.line_db_haa.setEndY(mainController.dashboard_child_haa.getHeight() * rationOfLine);
        });

        mainStage.getIcons().add(new Image(getClass().getResource("/image/icon-ourbry.png").toExternalForm()));
        mainStage.setMinHeight(200);
        mainStage.setMinWidth(600);
        mainStage.setTitle(myBundle.getString("application_name"));
        mainStage.setScene(scene);
        mainStage.show();
    }

    public Stage getParentStage(){
        return mainStage;
    }

    public static void main(String[] args) {
        launch();
    }
}