package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import com.farras.sdit.perpussditanakshalihbogorabsensi.EnrollmentApplication;
import com.farras.sdit.perpussditanakshalihbogorabsensi.data_model.Students;
import com.farras.sdit.perpussditanakshalihbogorabsensi.data_model_api.StudentMembersApi;
import com.google.gson.Gson;
import javafx.beans.DefaultProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberStudentController implements Initializable {
    private EnrollmentApplication enrollmentApplication;


    @FXML
    private Button add_anggota;

    @FXML
    TableView <Students> table_member_students;

    @FXML
    TableColumn <Students, String> idNumberTable;
    @FXML
    TableColumn <Students, String> nameTable;
    @FXML
    TableColumn <Students, String> genderTable;
    @FXML
    TableColumn <Students, String> statusTable;

    @FXML
    protected void onHelloButtonClick() {
        System.out.println("works ?");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set cell factory
        idNumberTable.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        nameTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderTable.setCellValueFactory(new PropertyValueFactory<>("gender"));
        statusTable.setCellValueFactory(new PropertyValueFactory<>("status"));


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


        // Ambil semua data dari API OUrBry
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "{}");
        Request request = new Request.Builder()
                .url("http://localhost:8000/api/get-all-member/?format=json")
                .method("POST", body)
                .addHeader("Authorization", "Token 41281b4a66cc21fe66abf4e14c1404a59a0aca36")
                .build();
        try {
            Response response = client.newCall(request).execute();


            if (response.code() == 200){
                String responeBody = response.body().string();

                // buat gson untuk convert json kedalam object
                Gson gson = new Gson();
                StudentMembersApi[] dataModels = gson.fromJson(responeBody, StudentMembersApi[].class);

                // Inisiasi class students (data model untuk table) dan besarana array
                Students[] students = new Students[dataModels.length];

                // Assign semua nilai dari StudentMembersAPi kedalam students data model
                for (int i = 0 , len = dataModels.length; i < len ; i++){
                    students[i] = new Students(dataModels[i].getNis(),dataModels[i].getName(), dataModels[i].getGender(), dataModels[i].getStatus());
                }

                // assign data terformat kedalam observablelist untuk tabel
                final ObservableList<Students> data = FXCollections.observableArrayList(students);
                table_member_students.setItems(data);
            }
            else {
                System.out.println("Tampilkan pesan server tidak dapat memuat data");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
