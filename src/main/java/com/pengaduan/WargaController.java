package com.pengaduan;

import java.io.IOException;

import com.pengaduan.services.WargaService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class WargaController {
    
    @FXML
    private TableView<WargaService> tableWarga;

    @FXML
    private TextField namaInput;

    @FXML
    private ComboBox noRtComboBox;

    @FXML
    private TextField umurInput;

    @FXML
    private TextField agamaInput;

    @FXML
    private ComboBox jkComboBox;

    @FXML
    private TextField noTelpInput;

    @FXML
    private DatePicker tglLahirDate;

    public void btnHomeOnAction(ActionEvent event) throws IOException{
        App app = new App();
        app.changeScene("HomeFrame.fxml");
    }

    public void btnRtOnAction(ActionEvent event) throws IOException{
        App app = new App();
        app.changeScene("RtFrame.fxml");
    }

}
