package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import com.farras.sdit.perpussditanakshalihbogorabsensi.EnrollmentApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberStudentController implements Initializable {
    private EnrollmentApplication enrollmentApplication;


    @FXML
    private Button add_anggota;

    @FXML
    protected void onHelloButtonClick() {
        System.out.println("works ?");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enrollmentApplication = new EnrollmentApplication();

        add_anggota.setOnMouseClicked(e->{
            // create another stage to run enrollment student
            // new stage ditaruh agar menghapus kode a
            try {
                enrollmentApplication.start(new Stage());
            } catch (Exception es) {
                es.printStackTrace();
            }
            // jalankan stage untuk pendaftaran peserta baru
            enrollmentApplication.launch();

        });
    }
}
