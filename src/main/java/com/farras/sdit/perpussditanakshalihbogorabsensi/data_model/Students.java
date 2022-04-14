package com.farras.sdit.perpussditanakshalihbogorabsensi.data_model;

import javafx.beans.property.SimpleStringProperty;

public class Students {
    private final SimpleStringProperty idNumber;
    private final SimpleStringProperty name;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty status;


    public Students(String idNumber, String name, String gender, String status) {
        this.idNumber = new SimpleStringProperty(idNumber);
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.status = new SimpleStringProperty(status);
    }

    public String getIdNumber() {
        return idNumber.get();
    }

    public SimpleStringProperty idNumberProperty() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber.set(idNumber);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
