package com.gaotianchi.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler {
    private static final String URL = "jdbc:sqlite:books.db";
    private static final int PAGE_SIZE = 30;
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTablesIfNotExist() throws SQLException {
        String[] sqlStatements = {
                "CREATE TABLE IF NOT EXISTS book_stock (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "title TEXT, " +
                        "quantity INTEGER, " +
                        "costPrice INTEGER, " +
                        "salePrice INTEGER, " +
                        "saleQuantity INTEGER DEFAULT 0)",
                "CREATE TABLE IF NOT EXISTS transaction_record (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "title TEXT, " +
                        "timestamp TIMESTAMP, " +
                        "quantity INTEGER, " +
                        "profit INTEGER)"
        };

        try (Connection conn = DBHandler.connect();
                Statement stmt = conn.createStatement()) {
            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }
        }
    }

    public static void clearTableIfExists(String tableName) throws SQLException {
        String checkTableExistsQuery = String.format(
                "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='%s';", tableName
        );

        try (Connection conn = DBHandler.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkTableExistsQuery)) {

            if (rs.next() && rs.getInt(1) > 0) {
                String clearTableQuery = String.format("DELETE FROM %s;", tableName);
                stmt.executeUpdate(clearTableQuery);
            }
        }
    }

    public static List<Map<String, Object>> getPageData(String tableName, int pageNumber) throws SQLException {
        int offset = (pageNumber - 1) * PAGE_SIZE;
        String query = String.format("SELECT * FROM %s LIMIT ? OFFSET ?", tableName);

        List<Map<String, Object>> resultData = new ArrayList<>();

        try (Connection conn = DBHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, PAGE_SIZE);
            pstmt.setInt(2, offset);

            ResultSet rs = pstmt.executeQuery();

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                resultData.add(row);
            }
        }

        return resultData;
    }

}
