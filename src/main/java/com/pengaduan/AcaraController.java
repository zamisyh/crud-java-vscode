package com.pengaduan;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import com.pengaduan.helpers.AlertComponent;
import com.pengaduan.services.AcaraService;
import com.pengaduan.services.RtService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class AcaraController implements Initializable {

    //Table controls
    @FXML
    private TableView<AcaraService> tableAcara;

    //table columns
    @FXML
    private TableColumn<AcaraService, Integer> acaraIdColumn;

    @FXML
    private TableColumn<AcaraService, Integer> noRtColumn;

    @FXML
    private TableColumn<AcaraService, String> judulAcaraColumn;

    @FXML
    private TableColumn<AcaraService, String> deskripsiAcaraColumn;

    @FXML
    private TableColumn<AcaraService, String> tanggalMulaiColumn;

    @FXML
    private TableColumn<AcaraService, String> mulaiColumn;

    @FXML
    private TableColumn<AcaraService, String> selesaiColumn;


    //input controls
    @FXML
    private TextField judulAcaraInput;

    @FXML
    private ComboBox<RtService> noRtCombobox;

    @FXML
    private DatePicker tanggalMulaiDatepicker;

    @FXML
    private TextArea deskripsiTextarea;

    @FXML
    private TextField jamMulaiInput;

    @FXML
    private TextField jamSelesaiInput;

    @FXML
    private ChoiceBox<String> searchChoice;

    @FXML
    private TextField searchInput;

    private int noRtId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAcara();
        showRtCombobox();
        searchChoice.getItems().addAll("Judul", "No RT");
        searchChoice.setValue("Judul");
      
    }

    @FXML
    public void acaraSaveOnAction(ActionEvent event){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        int noRt = getNoRtId();
        String judul = judulAcaraInput.getText();
        String deskripsi = deskripsiTextarea.getText();
        LocalDate tanggalEvent = tanggalMulaiDatepicker.getValue();
        String jamMulai = jamMulaiInput.getText();
        String jamSelesai = jamSelesaiInput.getText();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        
        String query = "INSERT INTO events VALUES(null, '"+ noRt +"', '"+ judul +"', '"+ deskripsi +"', '"+ tanggalEvent +"', '"+ jamMulai +"', '"+ jamSelesai +"', '"+ timeStamp +"', '"+ timeStamp +"')";

        try {
           
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            reset();
            showAcara();
            showRtCombobox();

            AlertComponent alert = new AlertComponent();
            alert.showAlert("Succesfully create data", "Yeaaay!", "Success");

            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void acaraResetOnAction(ActionEvent event){
        reset();
    }

    @FXML
    public void acaraUpdateOnAction(ActionEvent event){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        int noRt = getNoRtId();
        String judul = judulAcaraInput.getText();
        String deskripsi = deskripsiTextarea.getText();
        LocalDate tanggalEvent = tanggalMulaiDatepicker.getValue();
        String jamMulai = jamMulaiInput.getText();
        String jamSelesai = jamSelesaiInput.getText();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        AlertComponent al = new AlertComponent();
        

        try {
            Statement statement = connectDB.createStatement();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Update Data");
            alert.setContentText("Are you sure to update this data ?");
            Optional<ButtonType> result = alert.showAndWait();
    
            if (result.get() == ButtonType.OK) {
                AcaraService acara = tableAcara.getSelectionModel().getSelectedItem();
            
                String sql = "UPDATE events SET " 
                + "rt_id='" + noRt + "', " 
                + "judul_event='" + judul + "', "
                + "deskripsi='" + deskripsi + "', "
                + "tanggal_event='" + tanggalEvent + "', "
                + "jam_mulai='" + jamMulai + "', "
                + "jam_selesai='" + jamSelesai + "' WHERE id='"+ acara.getAcaraId() +"'";

                statement.executeUpdate(sql);
                
                reset();
                showAcara();
                showRtCombobox();
    
               
                al.showAlert("Succesfully update data", "Yeaaay!", "Success");

            }

        } catch (Exception e) {
           al.showAlert(e.getMessage(), "Oppsss, something went wrong", "Error");
        }
    }

    @FXML
    public void acaraDeleteOnAction(ActionEvent event){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        try {
            Statement statement = connectDB.createStatement();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Delete Data");
            alert.setContentText("Are you sure to delete this data ?");
            Optional<ButtonType> result = alert.showAndWait();
    
            if (result.get() == ButtonType.OK) {
                AcaraService acara = tableAcara.getSelectionModel().getSelectedItem();
                statement.executeUpdate("DELETE FROM events WHERE id = " + acara.getAcaraId());
                
                reset();
                showAcara();
                showRtCombobox();
    
                AlertComponent al = new AlertComponent();
                al.showAlert("Succesfully delete data", "Yeaaay!", "Success");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void rowClicked(MouseEvent event){
        AcaraService acara = tableAcara.getSelectionModel().getSelectedItem();
        judulAcaraInput.setText(String.valueOf(acara.getJudulAcara()));
        deskripsiTextarea.setText(String.valueOf(acara.getDeskripsiAcara()));
        jamMulaiInput.setText(String.valueOf(acara.getJamMulai()));
        jamSelesaiInput.setText(String.valueOf(acara.getJamSelesai()));
        tanggalMulaiDatepicker.setValue(LocalDate.parse(String.valueOf(acara.getTanggalMulai()))); 
    }


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
    

    public ObservableList<AcaraService> getAcaraList(){
        ObservableList<AcaraService> acaraList = FXCollections.observableArrayList();
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        Statement st;
        ResultSet rs;
        String query = "SELECT events.id AS acaraId, rts.nama as noRt, judul_event, deskripsi, tanggal_event, jam_mulai, jam_selesai FROM events INNER JOIN rts on events.rt_id = rts.id";

        try {
            st = connectDB.createStatement();
            rs = st.executeQuery(query);

            AcaraService acara;

            while (rs.next()) {
                acara = new AcaraService(
                    Integer.parseInt(rs.getString("acaraId")),
                    rs.getString("judul_event"),
                    Integer.parseInt(rs.getString("noRt")),
                    rs.getString("deskripsi"),
                    rs.getString("tanggal_event"),
                    rs.getString("jam_mulai"),
                    rs.getString("jam_selesai")
                );
               

                acaraList.add(acara);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return acaraList;
    }
    

    public void showAcara(){
        FilteredList<AcaraService> flAcara = new FilteredList<>(getAcaraList(), p -> true);

        acaraIdColumn.setCellValueFactory(new PropertyValueFactory<AcaraService, Integer>("acaraId"));
        noRtColumn.setCellValueFactory(new PropertyValueFactory<AcaraService, Integer>("noRtId"));
        judulAcaraColumn.setCellValueFactory(new PropertyValueFactory<AcaraService, String>("judulAcara"));
        deskripsiAcaraColumn.setCellValueFactory(new PropertyValueFactory<AcaraService, String>("deskripsiAcara"));
        tanggalMulaiColumn.setCellValueFactory(new PropertyValueFactory<AcaraService, String>("tanggalMulai"));
        mulaiColumn.setCellValueFactory(new PropertyValueFactory<AcaraService, String>("jamMulai"));
        selesaiColumn.setCellValueFactory(new PropertyValueFactory<AcaraService, String>("jamSelesai"));

        searchInput.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (searchChoice.getValue())
            {
                case "Judul":
                    flAcara.setPredicate(p -> p.getJudulAcara().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "No RT":
                    flAcara.setPredicate(p -> Integer.toString(p.getNoRtId()).toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
                    break;
            }
        });
        
        tableAcara.setItems(flAcara);

        searchChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//reset table and textfield when new choice is selected
            if (newVal != null) {
                searchInput.setText("");
            }
        });
    
    }

    public void reset(){
        judulAcaraInput.setText(null);
        deskripsiTextarea.setText(null);
        tanggalMulaiDatepicker.setValue(null);
        jamMulaiInput.setText(null);
        jamSelesaiInput.setText(null);
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

    public void btnWargaOnAction(ActionEvent event) throws IOException{
        App app = new App();
        app.changeScene("WargaFrame.fxml");
    }

    public void btnPengaduanOnAction(ActionEvent event) throws IOException{
        App app = new App();

        app.changeScene("PengaduanFrame.fxml");
    }

}
