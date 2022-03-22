package com.farras.sdit.perpussditanakshalihbogorabsensi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class EnrollmentApplication extends Application {
    private String enrollmentType;
    private static Stage enrollmentStage;

    @Override
    public void start(Stage stage) throws Exception {
        enrollmentStage = stage;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("strings", Locale.getDefault());

        // enrollment stage based on enroolmentType
        FXMLLoader fxmlLoader = new FXMLLoader(EnrollmentApplication.class.getResource("enrollment-view.fxml"),resourceBundle);


        Scene scene = new Scene(fxmlLoader.load());
        enrollmentStage.setTitle(resourceBundle.getString("title_enrollment_apps"));
        enrollmentStage.getIcons().add(new Image(getClass().getResource("/image/icon-ourbry.png").toExternalForm()));
        enrollmentStage.setMinHeight(600);
        enrollmentStage.setMinWidth(600);

        enrollmentStage.setScene(scene);
        enrollmentStage.resizableProperty().setValue(Boolean.FALSE);
        enrollmentStage.initModality(Modality.WINDOW_MODAL);
        enrollmentStage.initOwner(new MainApplication().getParentStage());

        enrollmentStage.setOnCloseRequest(e-> {
            // close tage jika tombol exit ditekan
            enrollmentStage.close();
            // menghapus stage
            enrollmentStage = null;
        });

    }

    public void launch(){
        enrollmentStage.show();
    }

    public String getEnrollmentType() {
        return enrollmentType;
    }

    public Stage getStage(){
        return enrollmentStage;
    }

    public void setEnrollmentType(String enrollmentType) {
        this.enrollmentType = enrollmentType;
    }
}
