package com.gaotianchi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.gaotianchi.database.DBHandler;

public class SellingBooksController {

    @FXML public TextField titleField;
    @FXML public TextField quantityField;

    @FXML
    public void updateTransactionRecordTable() {
        String title = titleField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        String selectSql = "SELECT costPrice, salePrice, quantity, saleQuantity FROM book_stock WHERE title = ? COLLATE NOCASE";
        String insertTransactionSql = "INSERT INTO transaction_record (title, timestamp, quantity, profit) VALUES (?, ?, ?, ?)";
        String updateBookStockSql = "UPDATE book_stock SET quantity = ?, saleQuantity = ? WHERE title = ? COLLATE NOCASE";

        try (Connection conn = DBHandler.connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement insertTransactionStmt = conn.prepareStatement(insertTransactionSql);
             PreparedStatement updateBookStockStmt = conn.prepareStatement(updateBookStockSql)) {

            selectStmt.setString(1, title);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int costPrice = rs.getInt("costPrice");
                int salePrice = rs.getInt("salePrice");
                int currentQuantity = rs.getInt("quantity");
                int currentSaleQuantity = rs.getInt("saleQuantity");

                int profitPerBook = salePrice - costPrice;
                int totalProfit = profitPerBook * quantity;

                LocalDateTime now = LocalDateTime.now();
                String transactionTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                insertTransactionStmt.setString(1, title);
                insertTransactionStmt.setString(2, transactionTime);
                insertTransactionStmt.setInt(3, quantity);
                insertTransactionStmt.setInt(4, totalProfit);
                insertTransactionStmt.executeUpdate();

                int newQuantity = currentQuantity - quantity;
                int newSaleQuantity = currentSaleQuantity + quantity;

                updateBookStockStmt.setInt(1, newQuantity);
                updateBookStockStmt.setInt(2, newSaleQuantity);
                updateBookStockStmt.setString(3, title);
                updateBookStockStmt.executeUpdate();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("未找到书籍：" + title);
            }

            titleField.setText("");
            quantityField.setText("");

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("数据更新失败，请重新输入！");
            throw new RuntimeException(e);
        }
    }
}
