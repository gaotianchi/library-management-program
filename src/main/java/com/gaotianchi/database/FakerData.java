package com.gaotianchi.database;

import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FakerData {
    public static void initDb() throws SQLException {
        DBHandler.clearTableIfExists("book_stock");
        DBHandler.clearTableIfExists("transaction_record");

        Faker faker = new Faker();
        String insertBookStockSql = "INSERT INTO book_stock (title, quantity, costPrice, salePrice, saleQuantity) VALUES (?, ?, ?, ?, ?)";
        String insertTransactionRecordSql = "INSERT INTO transaction_record (title, timestamp, quantity, profit) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBHandler.connect();
             PreparedStatement insertBookStockStmt = conn.prepareStatement(insertBookStockSql);
             PreparedStatement insertTransactionRecordStmt = conn.prepareStatement(insertTransactionRecordSql)) {

            List<String> titleList = new ArrayList<>();
            Random random = new Random();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (int i = 0; i < 100; i++) {
                String title = faker.book().title();
                titleList.add(title);
                int quantity = faker.number().numberBetween(1, 10);
                int costPrice = faker.number().numberBetween(12, 260);
                int salePrice = costPrice + faker.number().numberBetween(1, 100);
                int saleQuantity = faker.number().numberBetween(1, 300);

                insertBookStockStmt.setString(1, title);
                insertBookStockStmt.setInt(2, quantity);
                insertBookStockStmt.setInt(3, costPrice);
                insertBookStockStmt.setInt(4, salePrice);
                insertBookStockStmt.setInt(5, saleQuantity);
                insertBookStockStmt.addBatch();
            }

            insertBookStockStmt.executeBatch();

            for (String title : titleList) {
                int numRecords = random.nextInt(12) + 1;
                for (int j = 0; j < numRecords; j++) {
                    Date utilDate = faker.date().between(
                            new Date(Timestamp.valueOf("2024-01-01 00:00:00").getTime()),
                            new Date(System.currentTimeMillis()));
                    String transactionTime = dateFormat.format(utilDate);

                    int quantity = faker.number().numberBetween(1, 10);
                    int profit = faker.number().numberBetween(10, 1000);

                    insertTransactionRecordStmt.setString(1, title);
                    insertTransactionRecordStmt.setString(2, transactionTime);
                    insertTransactionRecordStmt.setInt(3, quantity);
                    insertTransactionRecordStmt.setInt(4, profit);
                    insertTransactionRecordStmt.addBatch();
                }
            }

            insertTransactionRecordStmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
