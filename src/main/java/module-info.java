module com.gaotianch.controllers {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive java.sql;
    requires javafaker;

    opens com.gaotianchi.controllers to javafx.fxml;
    opens com.gaotianchi.database to javafx.base;

    exports com.gaotianchi;
    exports com.gaotianchi.controllers;
    exports com.gaotianchi.database;
    exports com.gaotianchi.database.models;
}
