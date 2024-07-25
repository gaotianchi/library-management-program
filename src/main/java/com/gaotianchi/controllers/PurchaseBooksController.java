package com.gaotianchi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.gaotianchi.database.DBHandler;

public class PurchaseBooksController {

    @FXML private TextField titleField;
    @FXML private TextField quantityField;
    @FXML private TextField costPriceField;
    @FXML private TextField salePriceField;

    @FXML
    private void submitForm() {
        String title = titleField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        int costPrice = Integer.parseInt(costPriceField.getText());
        int salePrice = Integer.parseInt(salePriceField.getText());

        String selectSql = "SELECT id, quantity FROM book_stock WHERE title = ?";
        String insertSql = "INSERT INTO book_stock (title, quantity, costPrice, salePrice, saleQuantity) VALUES (?, ?, ?, ?, 0)";
        String updateSql = "UPDATE book_stock SET quantity = ?, costPrice = ?, salePrice = ? WHERE id = ?";

        try (Connection conn = DBHandler.connect();
                PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            selectStmt.setString(1, title);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int existingQuantity = rs.getInt("quantity");
                int newQuantity = existingQuantity + quantity;
                int id = rs.getInt("id");

                updateStmt.setInt(1, newQuantity);
                updateStmt.setDouble(2, costPrice);
                updateStmt.setDouble(3, salePrice);
                updateStmt.setInt(4, id);
                updateStmt.executeUpdate();
            } else {
                insertStmt.setString(1, title);
                insertStmt.setInt(2, quantity);
                insertStmt.setDouble(3, costPrice);
                insertStmt.setDouble(4, salePrice);
                insertStmt.executeUpdate();
            }
            titleField.setText("");
            quantityField.setText("");
            costPriceField.setText("");
            salePriceField.setText("");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("数据更新失败，请重新输入！");
            throw new RuntimeException(e);
        }
    }
}
