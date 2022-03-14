package com.farras.sdit.perpussditanakshalihbogorabsensi;

import com.farras.sdit.perpussditanakshalihbogorabsensi.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Locale;
import java.util.ResourceBundle;


public class LoginApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("strings", Locale.getDefault());


        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller = (LoginController)fxmlLoader.getController();



        stage.initStyle(StageStyle.UNDECORATED); // remove decorated stage
        stage.setTitle(resourceBundle.getString("text-login-page"));
        controller.AllowdUndecoratedStageMove(scene,stage);
        stage.setScene(scene);
        stage.show();
    }
}
