package com.farras.sdit.perpussditanakshalihbogorabsensi;


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
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle myBundle = ResourceBundle.getBundle("strings", Locale.getDefault());
        stage.initStyle(StageStyle.DECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.getIcons().add(new Image(getClass().getResource("/image/icon-ourbry.png").toExternalForm()));
        stage.setMinHeight(900);
        stage.setMinWidth(1200);
        stage.setTitle(myBundle.getString("application_name"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}