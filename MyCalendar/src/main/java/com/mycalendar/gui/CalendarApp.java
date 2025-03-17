package com.mycalendar.gui;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarApp extends Application {
    
    private static CalendarManager calendarManager;
    private static UserManager userManager;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialiser les managers
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        
        // Charger la vue de connexion
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        
        // Injecter les dépendances dans le contrôleur
        LoginController controller = loader.getController();
        controller.initialize(calendarManager, userManager, primaryStage);
        
        // Configurer la scène principale
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("MyCalendar - Gestionnaire d'Événements");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static CalendarManager getCalendarManager() {
        return calendarManager;
    }
    
    public static UserManager getUserManager() {
        return userManager;
    }
}