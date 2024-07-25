package com.gaotianchi.controllers;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import com.gaotianchi.App;

public class HomeController {

    @FXML public Button purchase_books;
    @FXML public Button inventoryButton;
    @FXML private ScrollPane scrollPane;

    @FXML
    public void initialize() throws IOException {
        scrollPane.setContent(App.loadFXML("views/components/stock"));
        Platform.runLater(() -> inventoryButton.requestFocus());
    }

    @FXML
    private void displayBookInventory() throws IOException {
        scrollPane.setContent(App.loadFXML("views/components/stock"));
    }
    @FXML
    private void displayTransactionRecords() throws IOException {
        scrollPane.setContent(App.loadFXML("views/components/transaction_records"));
    }
    @FXML
    private void popUpThePurchaseForm() throws IOException {
        Parent root = App.loadFXML("views/popups/purchase_books");
        Stage stage = new Stage();
        stage.setTitle("填写进货信息");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("assets/icons/icon.png"))));
        stage.showAndWait();
    }
    @FXML
    private void popUpTransactionForm() throws IOException {
        Parent root = App.loadFXML("views/popups/selling_books");
        Stage stage = new Stage();
        stage.setTitle("填写交易信息");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("assets/icons/icon.png"))));
        stage.showAndWait();
    }

}
