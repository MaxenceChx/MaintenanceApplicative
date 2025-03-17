module com.mycalendar {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycalendar.gui to javafx.fxml;
    exports com.mycalendar.gui;
}