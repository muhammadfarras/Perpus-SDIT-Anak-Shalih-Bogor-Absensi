package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import com.farras.sdit.perpussditanakshalihbogorabsensi.EnrollmentApplication;
import com.farras.sdit.perpussditanakshalihbogorabsensi.MainApplication;
import com.farras.sdit.perpussditanakshalihbogorabsensi.data_model.DataEnrollment;
import com.farras.sdit.perpussditanakshalihbogorabsensi.data_model_api.ResultEnrollmentApi;
import com.farras.sdit.perpussditanakshalihbogorabsensi.utils.Enrollment;
import com.farras.sdit.perpussditanakshalihbogorabsensi.utils.PopupCustom;
import com.farras.sdit.perpussditanakshalihbogorabsensi.utils.ReaderFP;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EnrollmentController implements Initializable {

    private Stage stage;
    private Stage mainStage;
    private boolean isFilledName = false;
    private boolean isFilledIDNumber = false;
    private boolean isChoosenGender;
    private ReaderFP readerFP;

    @FXML
    Button button_enrollment;
    @FXML
    TextField member_name;
    @FXML
    TextField member_id;
    @FXML
    ChoiceBox member_gender;
    @FXML
    Circle circle_reader_status;
    @FXML
    Label status_ready_capture_finger;
    @FXML
    CheckBox checkbox_enrollment_is_ready;
    @FXML
    HBox hbox_checker_enrollment;

    @FXML
    public Label enrollment_status;

    @FXML
    public ImageView first_image_finger_print;

    @FXML
    public ImageView second_image_finger_print;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        readerFP = new ReaderFP();
        this.stage = new EnrollmentApplication().getStage();
        this.mainStage = new MainApplication().getParentStage();
        readerFP.readerStatusByCircleColor(circle_reader_status, readerFP.getReaderFpStatus());

        member_name.setOnKeyTyped(e-> {
            isFilledName = !member_name.getText().isBlank();
            statusReadyForCaptureFingerPrint(resourceBundle);
        });

        member_id.setOnKeyTyped(e-> {
            isFilledIDNumber = !member_id.getText().isBlank();
            statusReadyForCaptureFingerPrint(resourceBundle);
        });

        checkbox_enrollment_is_ready.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                statusReadyForCaptureFingerPrint(resourceBundle);
            }
        });

        member_gender.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                isChoosenGender = member_gender.getItems().get((int) t1) != null;
                statusReadyForCaptureFingerPrint(resourceBundle);
            }
        });

        button_enrollment.setOnMouseClicked(mouseEvent -> {
            System.out.println(readerFP.getReaderFpStatus());
//            System.out.println();
            if (member_name.getText().isBlank() | member_id.getText().isBlank() | member_gender.getValue() == null | Enrollment.bothFmd == null){
                PopupCustom popupCustom = new PopupCustom(this.stage);
                popupCustom.setCode(PopupCustom.WARNING);
                popupCustom.setMessage(resourceBundle.getString("enrollment_status_form"));
                popupCustom.showPopup();

            }
            else {
//                Masukan data base disini
                try {
                    // assign value from provided form to DataEnrollmentO bject
                    DataEnrollment dataEnrollment = new DataEnrollment(member_name.getText(),
                            member_id.getText(),member_gender.getValue().toString(),Enrollment.bothFmd[0].getData(),Enrollment.bothFmd[1].getData());

                    // create client
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("text/plain");


                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart(DataEnrollment.NAME,dataEnrollment.getName())
                            .addFormDataPart(DataEnrollment.GENDER, dataEnrollment.getGender())
                            .addFormDataPart(DataEnrollment.ID, dataEnrollment.getIdNumber())
                            .addFormDataPart(DataEnrollment.FIRST_FMD, Arrays.toString(dataEnrollment.getFirstFmd()))
                            .addFormDataPart(DataEnrollment.SECOND_FMD, Arrays.toString(dataEnrollment.getSecondFmd()))
                            .build();
                    Request request = new Request.Builder()
                            .url("http://localhost:8000/api/?format=json")
                            .method("POST", body)
                            .addHeader("Authorization", "Token 41281b4a66cc21fe66abf4e14c1404a59a0aca36")
                            .build();
                    Response response = client.newCall(request).execute();

                    int responeCode = response.code();


                    switch (responeCode) {
                        // Status berhasil
                        case 200 -> {
                            String resultJson = response.body().string();
                            System.out.println(resultJson);
                            Gson gson = new Gson();
                            ResultEnrollmentApi resultEnrollmentApi = gson.fromJson(resultJson, ResultEnrollmentApi.class);


                            PopupCustom popupCustomSuccess = new PopupCustom(this.mainStage);
                            popupCustomSuccess.setCode(PopupCustom.SUCCESS);
                            popupCustomSuccess.setMessage(resultEnrollmentApi.getName()+" "+resourceBundle.getString("enrollment_status_success"));
                            popupCustomSuccess.showPopup();

                            // close stage
                            stage.close();
                        }
                        // not authrized
                        case 401 -> System.out.println("not oke");
                        default -> {
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });
    }


    public void statusReadyForCaptureFingerPrint (ResourceBundle resourceBundle) {
        if (isFilledIDNumber && isFilledName && isChoosenGender){
            hbox_checker_enrollment.setVisible(true);
            status_ready_capture_finger.setText(resourceBundle.getString("ready_to_capture"));

            if (checkbox_enrollment_is_ready.isSelected()){
                member_gender.setDisable(true);
                member_id.setDisable(true);
                member_name.setDisable(true);


                System.out.println("\u001B[35m"+ "FP DIMULAI ......."+ "\u001B[0m");
                Enrollment.Run(readerFP.realReader(), first_image_finger_print, second_image_finger_print, enrollment_status, resourceBundle);
                checkbox_enrollment_is_ready.setDisable(true);
            }
            else {

                member_gender.setDisable(false);
                member_id.setDisable(false);
                member_name.setDisable(false);
            }
        }
        else {
            status_ready_capture_finger.setText("");
            hbox_checker_enrollment.setVisible(false);
        }

    }
}
