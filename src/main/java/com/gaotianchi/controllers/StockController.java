package com.gaotianchi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.gaotianchi.database.DBHandler;
import com.gaotianchi.database.models.BookStock;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StockController {
    private int currentPage = 1;
    @FXML public TableView<BookStock> tableView;
    @FXML public TableColumn<BookStock, String> titleColumn;
    @FXML public TableColumn<BookStock, Integer> stockColumn;
    @FXML public TableColumn<BookStock, Integer> costPriceColumn;
    @FXML public TableColumn<BookStock, Integer> salePriceColumn;
    @FXML public TableColumn<BookStock, Integer> saleQuantityColumn;
    @FXML public Button previous_page;
    @FXML public Button next_page;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        costPriceColumn.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        salePriceColumn.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        saleQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("saleQuantity"));
        tableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            titleColumn.setPrefWidth(width * 0.57);
            stockColumn.setPrefWidth(width * 0.1);
            costPriceColumn.setPrefWidth(width * 0.1);
            salePriceColumn.setPrefWidth(width * 0.1);
            saleQuantityColumn.setPrefWidth(width * 0.1);
        });
        loadPageData(currentPage);
    }

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

    private void loadPageData(int pageNumber) {
        ObservableList<BookStock> data = FXCollections.observableArrayList();

        try {
            List<Map<String, Object>> pageData = DBHandler.getPageData("book_stock", pageNumber);
            for (Map<String, Object> row : pageData) {
                BookStock bookStock = new BookStock(
                        (String) row.get("title"),
                        (Integer) row.get("quantity"),
                        (Integer) row.get("costPrice"),
                        (Integer) row.get("salePrice"),
                        (Integer) row.get("saleQuantity")
                );
                data.add(bookStock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(data);
    }
}
