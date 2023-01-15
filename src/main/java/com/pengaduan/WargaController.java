package com.pengaduan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import com.pengaduan.services.RtService;
import com.pengaduan.services.WargaService;




public class WargaController implements Initializable {
    
    //tableview
    @FXML
    private TableView<WargaService> tableWarga;

    @FXML
    private TableColumn<WargaService, Integer> wargaIdColumn;

    @FXML
    private TableColumn<WargaService, String> namaColumn;

    @FXML
    private TableColumn<WargaService, Integer> noRtColumn;

    @FXML
    private TableColumn<WargaService, Integer> umurColumn;

    @FXML
    private TableColumn<WargaService, String> agamaColumn;
    
    @FXML
    private TableColumn<WargaService, String> jenisKelaminColumn;

    @FXML
    private TableColumn<WargaService, String> noTelpColumn;

    @FXML
    private TableColumn<WargaService, String> tanggalLahirColumn;


    //controls
    @FXML
    private TextField namaInput;

    @FXML
    private ComboBox<RtService> noRtCombobox;

    @FXML
    private TextField umurInput;

    @FXML
    private TextField agamaInput;

    @FXML
    private ComboBox<String> jkCombobox;

    @FXML
    private TextField noTelpInput;

    @FXML
    private DatePicker tglLahirDate;

    @FXML
    private TextField searchInput;

    @FXML
    private ChoiceBox<String> searchChoice;

    private int noRtId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showWarga();
        showRtCombobox();
        jkCombobox.getItems().addAll("L", "P");
        jkCombobox.setValue("L");

      
    }

    @FXML
    public void wargaSaveOnAction(ActionEvent event){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        try {
           
            Statement statement = connectDB.createStatement();
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void wargaResetOnAction(ActionEvent event){}

    @FXML
    public void wargaUpdateOnAction(ActionEvent event){}

    @FXML
    public void wargaDeleteOnAction(ActionEvent event){}



    public ObservableList<RtService> getRtList(){
        ObservableList<RtService> rtList = FXCollections.observableArrayList();
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        Statement st;
        ResultSet rs;
        String query = "SELECT * FROM rts ORDER BY id DESC";

        try {
           st = connectDB.createStatement();
           rs = st.executeQuery(query);
           RtService rt;

           while (rs.next()) {
                rt = new RtService(Integer.parseInt(rs.getString("id")), Integer.parseInt(rs.getString("nama")));
                rtList.add(rt);
           }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtList;

    }

    public void showRtCombobox(){
        noRtCombobox.setConverter(new StringConverter<RtService>() {

            @Override
            public RtService fromString(String str) {
                return null;
            }
        
            @Override
            public String toString(RtService obj) {
                return Integer.toString(obj.getNoRt());
            }
            
        });

        ObservableList<RtService> rtList = getRtList();
        noRtCombobox.setItems(rtList);
        noRtCombobox.getSelectionModel().selectFirst();


        noRtCombobox.valueProperty().addListener((obs, oldVal, newVal) -> {
            setNoRtId(newVal.getRtId());
        });
    }
    
    public ObservableList<WargaService> getWargaList(){
        ObservableList<WargaService> wargaList = FXCollections.observableArrayList();
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        Statement st;
        ResultSet rs;
        String query = "SELECT wargas.id AS wargaId, rts.nama AS noRt, wargas.nama AS nama_warga, umur, agama, jenis_kelamin, no_telp, tanggal_lahir FROM wargas INNER JOIN rts ON wargas.rt_id = rts.id";

        try {
            st = connectDB.createStatement();
            rs = st.executeQuery(query);

            WargaService warga;

            while (rs.next()) {
                warga = new WargaService(
                        Integer.parseInt(rs.getString("wargaId")),
                        rs.getString("nama_warga"),
                        Integer.parseInt(rs.getString("noRt")),
                        Integer.parseInt(rs.getString("umur")),
                        rs.getString("agama"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("no_telp"),
                        rs.getString("tanggal_lahir")
                    );

                    wargaList.add(warga);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wargaList;
    }
    

    public void showWarga(){
        FilteredList<WargaService> flWarga = new FilteredList<>(getWargaList(), p -> true);

        wargaIdColumn.setCellValueFactory(new PropertyValueFactory<WargaService, Integer>("wargaId"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<WargaService, String>("nama"));
        noRtColumn.setCellValueFactory(new PropertyValueFactory<WargaService, Integer>("noRt"));
        umurColumn.setCellValueFactory(new PropertyValueFactory<WargaService, Integer>("umur"));
        agamaColumn.setCellValueFactory(new PropertyValueFactory<WargaService, String>("agama"));
        jenisKelaminColumn.setCellValueFactory(new PropertyValueFactory<WargaService, String>("jenisKelamin"));
        noTelpColumn.setCellValueFactory(new PropertyValueFactory<WargaService, String>("noTelp"));
        tanggalLahirColumn.setCellValueFactory(new PropertyValueFactory<WargaService, String>("tanggalLahir"));

        searchChoice.getItems().addAll("Nama", "No RT", "Jenis Kelamin");
        searchChoice.setValue("Nama");

        searchInput.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (searchChoice.getValue())
            {
                case "Nama":
                    flWarga.setPredicate(p -> p.getNama().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "No RT":
                    flWarga.setPredicate(p -> Integer.toString(p.getNoRt()).toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
                    break;
                case "Jenis Kelamin":
                    flWarga.setPredicate(p -> p.getJenisKelamin().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by email
                    break;
            }
        });
        
        tableWarga.setItems(flWarga);

        searchChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//reset table and textfield when new choice is selected
            if (newVal != null) {
                searchInput.setText("");
            }
        });
    
    }   
    

    public int getNoRtId(){
        return noRtId;
    }

    public void setNoRtId(int noRtId){
        this.noRtId = noRtId;
    }

    public void btnHomeOnAction(ActionEvent event) throws IOException{
        App app = new App();
        app.changeScene("HomeFrame.fxml");
    }

    public void btnRtOnAction(ActionEvent event) throws IOException{
        App app = new App();
        app.changeScene("RtFrame.fxml");
    }

}
