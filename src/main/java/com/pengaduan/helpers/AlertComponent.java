package com.pengaduan.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertComponent {

    public void showAlert(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.show();
    }
}
