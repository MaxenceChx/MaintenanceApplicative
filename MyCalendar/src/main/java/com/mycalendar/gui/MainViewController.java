package com.mycalendar.gui;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.evenements.Evenement;
import com.mycalendar.valueobjects.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MainViewController {
    
    @FXML private Label welcomeLabel;
    @FXML private Label statusLabel;
    @FXML private TableView<Evenement> eventsTable;
    @FXML private TableColumn<Evenement, String> typeColumn;
    @FXML private TableColumn<Evenement, String> titleColumn;
    @FXML private TableColumn<Evenement, String> dateColumn;
    @FXML private TableColumn<Evenement, String> timeColumn;
    @FXML private TableColumn<Evenement, String> durationColumn;
    @FXML private TableColumn<Evenement, String> locationColumn;
    @FXML private TableColumn<Evenement, String> detailsColumn;
    @FXML private RadioButton allEventsRadio;
    @FXML private RadioButton todayEventsRadio;
    @FXML private RadioButton weekEventsRadio;
    @FXML private RadioButton monthEventsRadio;
    
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Utilisateur utilisateur;
    private Stage primaryStage;
    private ObservableList<Evenement> eventsData = FXCollections.observableArrayList();
    
    public void initialize(CalendarManager calendarManager, UserManager userManager, Utilisateur utilisateur, Stage primaryStage) {
        this.calendarManager = calendarManager;
        this.userManager = userManager;
        this.utilisateur = utilisateur;
        this.primaryStage = primaryStage;
        
        welcomeLabel.setText("Bienvenue, " + utilisateur.getIdentifiant() + "!");
        
        // Configuration des colonnes de la table
        typeColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getType().getLibelle()));
            
        titleColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTitre().getValeur()));
            
        dateColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().toString()));
            
        timeColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getHeureDebut().toString()));
            
        durationColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDuree().toString()));
            
        locationColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getLieu().getValeur()));
            
        detailsColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().description()));
        
        // Configuration de la sélection
        eventsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        // Listeners pour les filtres de date
        allEventsRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) refreshEvents();
        });
        
        todayEventsRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) refreshEvents();
        });
        
        weekEventsRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) refreshEvents();
        });
        
        monthEventsRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) refreshEvents();
        });
        
        // Charger les événements initiaux
        refreshEvents();
    }
    
    private void refreshEvents() {
        eventsData.clear();
        List<Evenement> events;
        
        LocalDateTime now = LocalDateTime.now();
        
        if (todayEventsRadio.isSelected()) {
            // Événements d'aujourd'hui
            LocalDateTime startOfDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);
            events = calendarManager.eventsDansPeriode(startOfDay, endOfDay);
        } else if (weekEventsRadio.isSelected()) {
            // Événements de cette semaine
            LocalDate startOfWeek = now.toLocalDate().minusDays(now.getDayOfWeek().getValue() - 1);
            LocalDate endOfWeek = startOfWeek.plusDays(6);
            events = calendarManager.eventsDansPeriode(
                LocalDateTime.of(startOfWeek, LocalTime.MIN),
                LocalDateTime.of(endOfWeek, LocalTime.MAX)
            );
        } else if (monthEventsRadio.isSelected()) {
            // Événements de ce mois
            LocalDate startOfMonth = now.toLocalDate().withDayOfMonth(1);
            LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
            events = calendarManager.eventsDansPeriode(
                LocalDateTime.of(startOfMonth, LocalTime.MIN),
                LocalDateTime.of(endOfMonth, LocalTime.MAX)
            );
        } else {
            // Tous les événements
            events = calendarManager.agendaPersonnel(utilisateur);
        }
        
        eventsData.addAll(events);
        eventsTable.setItems(eventsData);
        
        statusLabel.setText(events.size() + " événement(s) trouvé(s)");
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
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
    
    @FXML
    private void handleAddRendezVous(ActionEvent event) {
        openDialog("/fxml/AddRendezVousDialog.fxml", "Ajouter un rendez-vous personnel");
    }
    
    @FXML
    private void handleAddReunion(ActionEvent event) {
        openDialog("/fxml/AddReunionDialog.fxml", "Ajouter une réunion");
    }
    
    @FXML
    private void handleAddPeriodique(ActionEvent event) {
        openDialog("/fxml/AddPeriodiqueDialog.fxml", "Ajouter un événement périodique");
    }
    
    @FXML
    private void handleAddTache(ActionEvent event) {
        openDialog("/fxml/AddTacheDialog.fxml", "Ajouter une tâche");
    }
    
    @FXML
    private void handleDeleteEvent(ActionEvent event) {
        Evenement selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "Suppression", 
                      "Aucun événement sélectionné", 
                      "Veuillez sélectionner un événement à supprimer.");
            return;
        }
        
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirmation de suppression");
        confirmDialog.setHeaderText("Supprimer l'événement");
        confirmDialog.setContentText("Êtes-vous sûr de vouloir supprimer l'événement : " 
                                    + selectedEvent.getTitre() + " ?");
        
        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = calendarManager.supprimerEvenement(selectedEvent.getId());
                
                if (success) {
                    refreshEvents();
                    statusLabel.setText("Événement supprimé avec succès");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", 
                              "Erreur de suppression", 
                              "L'événement n'a pas pu être supprimé.");
                }
            }
        });
    }
    
    private void openDialog(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent dialogRoot = loader.load();
            
            // Configurer le contrôleur de dialogue
            EventDialogController controller = loader.getController();
            controller.initialize(calendarManager, utilisateur);
            
            // Créer une nouvelle fenêtre pour le dialogue
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            
            // Définir la scène avec le root chargé
            Scene scene = new Scene(dialogRoot);
            dialogStage.setScene(scene);
            
            // Après la fermeture du dialogue, rafraîchir les événements
            dialogStage.setOnHidden(e -> refreshEvents());
            
            // Afficher le dialogue et attendre sa fermeture
            dialogStage.showAndWait();
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors de l'ouverture du dialogue: " + e.getMessage());
        }
    }
    
    private void showError(String message) {
        statusLabel.setText("Erreur: " + message);
        showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue", message);
    }
    
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}