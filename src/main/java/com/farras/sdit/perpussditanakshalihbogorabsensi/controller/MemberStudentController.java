package com.farras.sdit.perpussditanakshalihbogorabsensi.controller;

import com.farras.sdit.perpussditanakshalihbogorabsensi.EnrollmentApplication;
import com.farras.sdit.perpussditanakshalihbogorabsensi.data_model.Students;
import com.farras.sdit.perpussditanakshalihbogorabsensi.data_model_api.StudentMembersApi;
import com.farras.sdit.perpussditanakshalihbogorabsensi.utils.OurBryConfiguration;
import com.google.gson.Gson;
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

public class MemberStudentController implements Initializable, Runnable {
    private EnrollmentApplication enrollmentApplication;

    private volatile ObservableList<Students>  dataStudents;


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

        MemberStudentController memberStudentController = new MemberStudentController();

        Thread thread = new Thread(memberStudentController);
        thread.start();
        try {
            // Menunggg thread sampai dapat datanya dari OurBry server
            thread.join();
            this.table_member_students.setItems(memberStudentController.getDataStudents());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        // GET CONFIG XML
        OurBryConfiguration ourBryConfiguration = new OurBryConfiguration();

        // Ambil semua data dari API OUrBry
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "{}");
        Request request = new Request.Builder()
                .url(ourBryConfiguration.getFetchAllDataStudentFP())
                .method("POST", body)
                .addHeader("Authorization", ourBryConfiguration.getTokenOurBry())
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
                dataStudents = FXCollections.observableArrayList(students);
            }
            else {
                System.out.println("Tampilkan pesan server tidak dapat memuat data");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Students> getDataStudents(){
        return this.dataStudents;
    }
}
