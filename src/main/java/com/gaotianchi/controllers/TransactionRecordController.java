package com.gaotianchi.controllers;

import com.gaotianchi.database.models.TransactionRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.gaotianchi.database.DBHandler;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TransactionRecordController {
    @FXML public TableView<TransactionRecord> tableView;
    @FXML public TableColumn<TransactionRecord, String> titleColumn;
    @FXML public TableColumn<TransactionRecord, Integer> transactionQuantityColumn;
    @FXML public TableColumn<TransactionRecord, Integer> tradingProfitColumn;
    @FXML public TableColumn<TransactionRecord, String> transactionDateColumn;
    @FXML public Button previous_page;
    @FXML public Button next_page;

    private int currentPage = 1;

    @FXML
    public void nextPage() {
        currentPage++;
        loadPageData(currentPage);
    }

    @FXML
    public void previousPage() {
        currentPage--;
        loadPageData(currentPage);
    }

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        transactionQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tradingProfitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        tableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            titleColumn.setPrefWidth(width * 0.47);
            transactionQuantityColumn.setPrefWidth(width * 0.1);
            tradingProfitColumn.setPrefWidth(width * 0.1);
            transactionDateColumn.setPrefWidth(width * 0.3);
        });
        loadPageData(currentPage);
    }
    private void loadPageData(int pageNumber) {
        ObservableList<TransactionRecord> data = FXCollections.observableArrayList();

        try {
            List<Map<String, Object>> pageData = DBHandler.getPageData("transaction_record", pageNumber);
            for (Map<String, Object> row : pageData) {
                TransactionRecord transactionRecord = new TransactionRecord(
                        (String) row.get("title"),
                        (String) row.get("timestamp"),
                        (Integer) row.get("quantity"),
                        (Integer) row.get("profit")
                );
                data.add(transactionRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(data);
    }

}
