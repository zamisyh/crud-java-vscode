package com.pengaduan;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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

            if (fieldEmail.getText().toString().equals("zamzam@gmail.com") && fieldPassword.getText().toString().equals("zamzam")) {
                wrongLabel.setText("Succesfully login");

                app.changeScene("dashboard/home.fxml");
            }else if(fieldEmail.getText().isEmpty() || fieldPassword.getText().isEmpty()){
                wrongLabel.setText("Email or password is required");   
            }else{
                wrongLabel.setText("Invalid credentials");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        // Stage stage = (Stage) cancelButton.getScene().getWindow();
        // stage.close();
        
        DBConnection connection = new DBConnection();
        Connection connectDB = connection.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet rsQuery = statement.executeQuery("SELECT * FROM users");

            while (rsQuery.next()) {
                System.out.println(rsQuery.getString("email"));
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        
        
    }

}
