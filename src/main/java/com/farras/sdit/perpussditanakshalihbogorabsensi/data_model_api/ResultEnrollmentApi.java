package com.farras.sdit.perpussditanakshalihbogorabsensi.data_model_api;

// https://stackoverflow.com/questions/19169754/parsing-nested-json-data-using-gson

import java.util.List;

public class ResultEnrollmentApi {
    String info;
    String message;
    DataInformation data;

    public ResultEnrollmentApi(){}

    public String getInfo() {
        return info;
    }

    public String getMessage() {
        return message;
    }

    public String getName(){
        return data.getName();
    }

    public String getNis(){
        return data.getNis();
    }


}

class DataInformation {
    public String name;
    public String nis;

    public String getName() {
        return name;
    }

    public String getNis() {
        return nis;
    }

}