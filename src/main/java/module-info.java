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
    requires dpjavapos;
    requires dpuareu;

    opens com.farras.sdit.perpussditanakshalihbogorabsensi to javafx.fxml;
    exports com.farras.sdit.perpussditanakshalihbogorabsensi;
    exports com.farras.sdit.perpussditanakshalihbogorabsensi.controller;
    opens com.farras.sdit.perpussditanakshalihbogorabsensi.controller to javafx.fxml;
}