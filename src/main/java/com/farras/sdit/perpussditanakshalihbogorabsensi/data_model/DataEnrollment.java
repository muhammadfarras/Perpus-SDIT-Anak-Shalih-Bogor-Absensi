package com.farras.sdit.perpussditanakshalihbogorabsensi.data_model;

public class DataEnrollment {
    public static String NAME = "name";
    public static String ID = "nis";
    public static String GENDER = "gender";
    public static String FIRST_FMD = "first_fmd";
    public static String SECOND_FMD = "second_fmd";

    private final String name;
    private final String idNumber;
    private final String gender;
    private final byte[] firstFmd;
    private final byte[] secondFmd;

    public DataEnrollment(String name, String idNumber, String gender, byte[] firsFmd, byte[] secondFmd){
        this.name = name;
        this.idNumber = idNumber;
        this.gender = gender;
        this.firstFmd = firsFmd;
        this.secondFmd = secondFmd;
    }


    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getGender() {
        return gender;
    }

    public byte[] getFirstFmd() {
        return firstFmd;
    }

    public byte[] getSecondFmd() {
        return secondFmd;
    }
}
