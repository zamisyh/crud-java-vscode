package com.pengaduan;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import com.pengaduan.services.RtService;

public class RtController implements Initializable {
    
    @FXML
    private Button btnHome;

    @FXML
    private Button btnRt;

    @FXML
    private Label labelAlert;

    @FXML
    private TableView<RtService> tableView;

    @FXML
    private TableColumn<RtService, Integer> noRtColumn;

    @FXML
    private TableColumn<RtService, Integer> noIdColumn;

    @FXML
    private TextField noRtInput;

    @FXML
    private TextField searchRt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       showRts();
    }


    @FXML
    public void saveOnAction(ActionEvent event){
        
        try {
            DBConnection connection = new DBConnection();
            Connection connectDB = connection.getConnection();

            Statement statement = connectDB.createStatement();

            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            statement.executeUpdate("INSERT INTO rts VALUES (null, '"+ Integer.parseInt(noRtInput.getText()) +"', '" + timeStamp + "', '" + timeStamp + "')");
            showRts();
            labelAlert.setText("Succesfully insert data");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent event){

        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        try {
           
            Statement statement = connectDB.createStatement();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Delete Data");
            alert.setContentText("Are you sure to delete this data ?");
            Optional<ButtonType> result = alert.showAndWait();
    
            if (result.get() == ButtonType.OK) {
                RtService rt = tableView.getSelectionModel().getSelectedItem();
                statement.executeUpdate("DELETE FROM rts WHERE id = " + rt.getRtId());
                showRts();
                labelAlert.setText("Succesfully delete data");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void editOnAction(ActionEvent event){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            RtService rt = tableView.getSelectionModel().getSelectedItem();
            statement.executeUpdate("UPDATE rts SET nama = '"+ Integer.parseInt(noRtInput.getText()) +"' WHERE id = '" + rt.getRtId() + "'");
            showRts();
            labelAlert.setText("Succesfully update data");
            noRtInput.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void rowClicked(MouseEvent event){
        RtService rt = tableView.getSelectionModel().getSelectedItem();
        noRtInput.setText(String.valueOf(rt.getNoRt()));
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
    
    public void showRts(){
       
        FilteredList<RtService> flRt = new FilteredList<>(getRtList(), p -> true);
        noIdColumn.setCellValueFactory(new PropertyValueFactory<RtService, Integer>("rtId"));
        noRtColumn.setCellValueFactory(new PropertyValueFactory<RtService, Integer>("noRt"));
        
        searchRt.textProperty().addListener((obs, newValue, oldValue) -> {
            flRt.setPredicate(p -> Integer.toString(p.getNoRt()).toLowerCase().contains(newValue.toLowerCase().trim()));
        });

        tableView.setItems(flRt);

    }

    public void executeQuery(String query){
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();
        Statement st;

        try {
            st = connectDB.createStatement();
            st.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
