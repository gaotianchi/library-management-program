package com.gaotianchi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import com.gaotianchi.database.DBHandler;
import com.gaotianchi.database.FakerData;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("views/pages/home"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Library Management Program");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/icons/icon.png"))));
        stage.show();
    }

    public static Parent loadFXML(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws SQLException {
        DBHandler.createTablesIfNotExist();
        FakerData.initDb();
        launch();
    }

}