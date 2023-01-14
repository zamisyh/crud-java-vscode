package com.pengaduan;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeController {

    @FXML
    private Button btnRt;

    @FXML
    private Button btnWarga;

    public void btnRtOnAction(ActionEvent event) throws IOException{
        App app = new App();

        app.changeScene("RtFrame.fxml");
    }

    public void btnWargaOnAction(ActionEvent event) throws IOException{
        App app = new App();

        app.changeScene("WargaFrame.fxml");

    }
}
