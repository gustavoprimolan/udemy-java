package com.barosanu.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginWindowController {

    @FXML
    private TextField emailAddress;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    @FXML
    void loginButtonAction(ActionEvent event) {
        System.out.println("Click");
    }

}

