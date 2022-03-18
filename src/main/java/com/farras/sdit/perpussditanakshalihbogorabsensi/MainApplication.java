package com.farras.sdit.perpussditanakshalihbogorabsensi;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle myBundle = ResourceBundle.getBundle("strings", Locale.getDefault());

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle(myBundle.getString("text-login-page"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}