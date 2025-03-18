module com.mycalendar {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    
    // Exporter tous les packages
    exports com.mycalendar;
    exports com.mycalendar.evenements;
    exports com.mycalendar.valueobjects;
    exports com.mycalendar.menu;
    exports com.mycalendar.menu.afficher;
    exports com.mycalendar.menu.ajouter;
    exports com.mycalendar.menu.compte;
    exports com.mycalendar.menu.supprimer;
    exports com.mycalendar.gui;
    
    // Ouvrir tous les packages
    opens com.mycalendar;
    opens com.mycalendar.evenements;
    opens com.mycalendar.valueobjects;
    opens com.mycalendar.menu;
    opens com.mycalendar.menu.afficher;
    opens com.mycalendar.menu.ajouter;
    opens com.mycalendar.menu.compte;
    opens com.mycalendar.menu.supprimer;
    opens com.mycalendar.gui;
}