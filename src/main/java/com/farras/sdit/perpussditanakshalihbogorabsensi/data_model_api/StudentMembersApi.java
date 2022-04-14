package com.farras.sdit.perpussditanakshalihbogorabsensi.data_model_api;

public class StudentMembersApi {
    public String nis;
    public String name;
    public String gender;
    public String status;
    public String first_fmd;
    public String second_fmd;

    public StudentMembersApi(){}

    public String getNis() {
        return nis;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public byte[] getFirst_fmd() {
        // parse string to byte
        String[] byteValues = first_fmd.substring(1, first_fmd.length()-1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0; i < bytes.length ; i++){
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        return bytes;
    }

    public byte[] getSecond_fmd() {
        // parse string to byte
        String[] byteValues = second_fmd.substring(1, second_fmd.length()-1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0; i < bytes.length ; i++){
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        return bytes;
    }
}
