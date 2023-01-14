package com.pengaduan;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginController {
    
    @FXML
    private Button cancelButton;

    @FXML
    private Button btnLogin;

    @FXML
    private Label wrongLabel;

    @FXML
    private TextField fieldEmail;

    @FXML
    private PasswordField fieldPassword;
    

    public void userLogin(ActionEvent event){
        checkLogin();
    }

    public void checkLogin(){
        try {

            App app = new App();

            DBConnection connection = new DBConnection();
            Connection connectDB = connection.getConnection();

            Statement statement = connectDB.createStatement();
            ResultSet rsQuery = statement.executeQuery("SELECT * FROM users WHERE email = '"+ fieldEmail.getText() +"' AND password = '" + fieldPassword.getText() +"'");
            
            if (rsQuery.next()) {
                if (fieldEmail.getText().toString().equals(rsQuery.getString("email")) && fieldPassword.getText().toString().equals(rsQuery.getString("password"))) {
                    wrongLabel.setText("Succesfully login");
                    app.changeScene("HomeFrame.fxml");

                    // infoBox("Login Berhasil", null, "Success");

                    
                }
            }else if(fieldEmail.getText().isEmpty() || fieldPassword.getText().isEmpty()){
                infoBox("Email or password is required", null, "Failed");

            }else{
                infoBox("Please enter correct Email and Password", null, "Failed");

            }

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

}
