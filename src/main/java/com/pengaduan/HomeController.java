package com.pengaduan;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button btnRt;

    @FXML
    private Button btnWarga;

    @FXML
    private Button btnPengaduan;

    @FXML
    private Label rtLabel;

    @FXML
    private Label wargaLabel;

    @FXML
    private Label acaraLabel;

    @FXML
    private Label laporanLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        try {
            countDataRt();
            countDataWarga();
            countDataPengaduan();
            countDataAcara();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        


    }

    public void countDataRt() throws SQLException{
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        String rt = "SELECT COUNT(*) as JUMLAH_RT FROM rts"; 

        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(rt);

        while (rs.next()) {
            rtLabel.setText(rs.getString("JUMLAH_RT"));
        }

        statement.close();

    }

    public void countDataWarga() throws SQLException{
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        String warga = "SELECT COUNT(*) as JUMLAH_WARGA FROM wargas"; 

        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(warga);

        while (rs.next()) {
            wargaLabel.setText(rs.getString("JUMLAH_WARGA"));
        }

        statement.close();

    }

    public void countDataPengaduan() throws SQLException{
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        String pengaduan = "SELECT COUNT(*) as JUMLAH_PENGADUAN FROM pengaduans"; 

        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(pengaduan);

        while (rs.next()) {
            laporanLabel.setText(rs.getString("JUMLAH_PENGADUAN"));
        }

        statement.close();
    }

    public void countDataAcara() throws SQLException{
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        String acara = "SELECT COUNT(*) as JUMLAH_ACARA FROM events"; 

        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(acara);

        while (rs.next()) {
            acaraLabel.setText(rs.getString("JUMLAH_ACARA"));
        }

        statement.close();

    }

    public void btnRtOnAction(ActionEvent event) throws IOException{
        App app = new App();

        app.changeScene("RtFrame.fxml");
    }

    public void btnWargaOnAction(ActionEvent event) throws IOException{
        App app = new App();

        app.changeScene("WargaFrame.fxml");
    }

    public void btnPengaduanOnAction(ActionEvent event) throws IOException{
        App app = new App();

        app.changeScene("PengaduanFrame.fxml");
    }

    public void btnAcaraOnAction(ActionEvent event) throws IOException{
        App app = new App();

        app.changeScene("AcaraFrame.fxml");
    }

    
}
