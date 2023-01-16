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
import com.pengaduan.services.PengaduanService;
import com.pengaduan.services.WargaService;

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

public class PengaduanController implements Initializable {

    //table view scene

    @FXML
    private TableView<PengaduanService> tablePengaduan;

    @FXML
    private TableColumn<PengaduanService, Integer> pengaduanIdColumn;

    @FXML
    private TableColumn<PengaduanService, String> wargaIdColumn;

    @FXML
    private TableColumn<PengaduanService, String> judulColumn;

    @FXML
    private TableColumn<PengaduanService, String> deskripsiColumn;

    @FXML
    private TableColumn<PengaduanService, String> tanggalColumn;


    //controls scene

    @FXML
    private TextField judulInput;

    @FXML
    private ComboBox<WargaService> wargaIdCombobox;

    @FXML
    private DatePicker tglDatePicker;

    @FXML
    private TextArea deskripsiTextarea;

    @FXML
    private ChoiceBox<String> searchChoiceBox;

    @FXML
    private TextField searchInput;

    private int noWargaId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPengaduan();
        showWargaCombobox();
       
        searchChoiceBox.getItems().addAll("Judul", "Nama Warga");
        searchChoiceBox.setValue("Judul");


      
    }


    @FXML
    public void btnPengaduanSaveOnAction(ActionEvent event){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        String judul = judulInput.getText();
        int wargaId = getNoWargaId();
        LocalDate tglPengaduan = tglDatePicker.getValue();
        String deskripsi = deskripsiTextarea.getText();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        String sql = "INSERT INTO pengaduans VALUES(null, '"+wargaId+"', '"+judul+"', '"+deskripsi+"', '"+tglPengaduan+"', '"+timeStamp+"', '"+timeStamp+"')";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(sql);
            reset();
            showPengaduan();
            showWargaCombobox();
            
            AlertComponent alert = new AlertComponent();
            alert.showAlert("Succesfully create data", "Yeaaay!", "Success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnPengaduanResetOnAction(ActionEvent event){
        reset();
    }

    @FXML
    public void btnPengaduanUpdateOnAction(ActionEvent event){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        AlertComponent al = new AlertComponent();
        String judul = judulInput.getText();
        int wargaId = getNoWargaId();
        LocalDate tglPengaduan = tglDatePicker.getValue();
        String deskripsi = deskripsiTextarea.getText();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());


        try {
            Statement statement = connectDB.createStatement();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Update Data");
            alert.setContentText("Are you sure to update this data ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                PengaduanService pengaduan = tablePengaduan.getSelectionModel().getSelectedItem();
            
                String sql = "UPDATE pengaduans SET " 
                + "warga_id='" + wargaId + "', " 
                + "judul_pengaduan='" + judul + "', "
                + "deskripsi_pengaduan='" + deskripsi + "', "
                + "tanggal_pengaduan='" + tglPengaduan + "' WHERE id='"+ pengaduan.getPengaduanId() +"'";

                statement.executeUpdate(sql);
                
                reset();
                showPengaduan();
                showWargaCombobox();
    
               
                al.showAlert("Succesfully update data", "Yeaaay!", "Success");

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

    @FXML
    public void btnPengaduanDeleteOnAction(ActionEvent event){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        try {
            Statement statement = connectDB.createStatement();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Delete Data");
            alert.setContentText("Are you sure to delete this data ?");
            Optional<ButtonType> result = alert.showAndWait();
    
            if (result.get() == ButtonType.OK) {
                PengaduanService pengaduan = tablePengaduan.getSelectionModel().getSelectedItem();
                statement.executeUpdate("DELETE FROM pengaduans WHERE id = " + pengaduan.getPengaduanId());
                
                reset();
                showPengaduan();
                showWargaCombobox();
    
                AlertComponent al = new AlertComponent();
                al.showAlert("Succesfully delete data", "Yeaaay!", "Success");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void showWargaCombobox(){
        wargaIdCombobox.setConverter(new StringConverter<WargaService>() {

            @Override
            public WargaService fromString(String str) {
                return null;
            }
        
            @Override
            public String toString(WargaService obj) {
                return obj.getNama();
            }
            
        });

        ObservableList<WargaService> wargaList = getWargaList();
        wargaIdCombobox.setItems(wargaList);
        wargaIdCombobox.getSelectionModel().selectFirst();


        wargaIdCombobox.valueProperty().addListener((obs, oldVal, newVal) -> {
            setNoWargaId(newVal.getWargaId());
        });
    }

    public ObservableList<PengaduanService> getPengaduanList(){
        ObservableList<PengaduanService> pengaduanList = FXCollections.observableArrayList();
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        Statement st;
        ResultSet rs;
        String query = "SELECT pengaduans.id AS pengaduanId, nama, judul_pengaduan, deskripsi_pengaduan, tanggal_pengaduan FROM pengaduans INNER JOIN wargas on pengaduans.warga_id = wargas.id";

        try {
            st = connectDB.createStatement();
            rs = st.executeQuery(query);

            PengaduanService pengaduan;

            while (rs.next()) {
                pengaduan = new PengaduanService(
                    Integer.parseInt(rs.getString("pengaduanId")),
                    rs.getString("judul_pengaduan"),
                    rs.getString("nama"),
                    rs.getString("tanggal_pengaduan"),
                    rs.getString("deskripsi_pengaduan")
                );

                pengaduanList.add(pengaduan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pengaduanList;
    }
    
    public void showPengaduan(){
        FilteredList<PengaduanService> flPengaduan = new FilteredList<>(getPengaduanList(), p -> true);

        pengaduanIdColumn.setCellValueFactory(new PropertyValueFactory<PengaduanService, Integer>("pengaduanId"));
        wargaIdColumn.setCellValueFactory(new PropertyValueFactory<PengaduanService, String>("namaWarga"));
        judulColumn.setCellValueFactory(new PropertyValueFactory<PengaduanService, String>("judulPengaduan"));
        deskripsiColumn.setCellValueFactory(new PropertyValueFactory<PengaduanService, String>("deskripsiPengaduan"));
        tanggalColumn.setCellValueFactory(new PropertyValueFactory<PengaduanService, String>("tanggalPengaduan"));
      

        searchInput.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (searchChoiceBox.getValue())
            {
                case "Judul":
                    flPengaduan.setPredicate(p -> p.getJudulPengaduan().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Nama Warga":
                    flPengaduan.setPredicate(p -> p.getNamaWarga().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
            }
        });
        
        tablePengaduan.setItems(flPengaduan);

        searchChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//reset table and textfield when new choice is selected
            if (newVal != null) {
                searchInput.setText("");
            }
        });
    
    }  

    @FXML
    public void rowClicked(MouseEvent event){
        PengaduanService pengaduan = tablePengaduan.getSelectionModel().getSelectedItem();
        judulInput.setText(String.valueOf(pengaduan.getJudulPengaduan()));
        tglDatePicker.setValue(LocalDate.parse(String.valueOf(pengaduan.getTanggalPengaduan())));
        deskripsiTextarea.setText(String.valueOf(pengaduan.getDeskripsiPengaduan()));
       
    }
    
    public int getNoWargaId(){
        return noWargaId;
    }

    public void setNoWargaId(int noWargaId){
        this.noWargaId = noWargaId;
    }

    public void reset(){
        judulInput.setText(null);
        tglDatePicker.setValue(null);
        deskripsiTextarea.setText(null);
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

   
}
