package com.mycalendar.gui;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.valueobjects.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button createAccountButton;
    @FXML private Label errorLabel;
    
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Stage primaryStage;
    
    public void initialize(CalendarManager calendarManager, UserManager userManager, Stage primaryStage) {
        this.calendarManager = calendarManager;
        this.userManager = userManager;
        this.primaryStage = primaryStage;
    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Veuillez saisir un nom d'utilisateur et un mot de passe");
            return;
        }
        
        if (userManager.verifierAuthentification(username, password)) {
            Utilisateur utilisateur = userManager.rechercherUtilisateur(username);
            openMainView(utilisateur);
        } else {
            showError("Nom d'utilisateur ou mot de passe incorrect");
        }
    }
    
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateAccount.fxml"));
            Parent root = loader.load();
            
            CreateAccountController controller = loader.getController();
            controller.initialize(calendarManager, userManager, primaryStage);
            
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du chargement de la vue de création de compte");
        }
    }
    
    private void openMainView(Utilisateur utilisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();
            
            MainViewController controller = loader.getController();
            controller.initialize(calendarManager, userManager, utilisateur, primaryStage);
            
            Scene scene = new Scene(root, 1024, 768);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du chargement de la vue principale");
        }
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}