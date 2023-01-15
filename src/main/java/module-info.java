module com.pengaduan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    // requires mysql.connector.java;
   //requires mysql.connector.j;

    opens com.pengaduan;
    opens com.pengaduan.services;
    opens com.pengaduan.helpers;
    // opens com.pengaduan to javafx.fxml;
    // exports com.pengaduan;
}
