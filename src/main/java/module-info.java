module com.farras.sdit.perpussditanakshalihbogorabsensi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires dpuareu;
    requires dpjavapos;
    requires java.desktop;
    requires javafx.media;
    requires com.google.gson;
    requires okhttp3;


    opens com.farras.sdit.perpussditanakshalihbogorabsensi to javafx.fxml;
    opens com.farras.sdit.perpussditanakshalihbogorabsensi.data_model_api to com.google.gson;
    exports com.farras.sdit.perpussditanakshalihbogorabsensi;
    exports com.farras.sdit.perpussditanakshalihbogorabsensi.data_model_api;
    exports com.farras.sdit.perpussditanakshalihbogorabsensi.controller;
    exports com.farras.sdit.perpussditanakshalihbogorabsensi.data_model;
    opens com.farras.sdit.perpussditanakshalihbogorabsensi.controller to javafx.fxml;
    opens com.farras.sdit.perpussditanakshalihbogorabsensi.data_model to javafx.base;
}