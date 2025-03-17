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

public class CreateAccountController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button createButton;
    @FXML private Button cancelButton;
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
    private void handleCreate(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Validation des champs
        if (username.isEmpty()) {
            showError("Le nom d'utilisateur est obligatoire");
            return;
        }
        
        if (password.isEmpty()) {
            showError("Le mot de passe est obligatoire");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Les mots de passe ne correspondent pas");
            return;
        }
        
        // Création du compte
        boolean success = userManager.creerCompte(username, password);
        
        if (success) {
            Utilisateur utilisateur = userManager.rechercherUtilisateur(username);
            openMainView(utilisateur);
        } else {
            showError("Impossible de créer le compte. Ce nom d'utilisateur existe peut-être déjà.");
        }
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        returnToLogin();
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
    
    private void returnToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            
            LoginController controller = loader.getController();
            controller.initialize(calendarManager, userManager, primaryStage);
            
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du retour à l'écran de connexion");
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
}