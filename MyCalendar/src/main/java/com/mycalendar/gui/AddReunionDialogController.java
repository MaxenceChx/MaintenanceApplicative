package com.mycalendar.gui;

import com.mycalendar.CalendarManager;
import com.mycalendar.valueobjects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddReunionDialogController extends EventDialogController {
    
    @FXML private TextField titleField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Integer> hourComboBox;
    @FXML private ComboBox<Integer> minuteComboBox;
    @FXML private Spinner<Integer> durationSpinner;
    @FXML private TextField locationField;
    @FXML private ListView<String> participantsList;
    @FXML private TextField participantField;
    @FXML private Label errorLabel;
    
    private ObservableList<String> participants = FXCollections.observableArrayList();
    
    @Override
    public void initialize(CalendarManager calendarManager, Utilisateur utilisateur) {
        super.initialize(calendarManager, utilisateur);
        
        // Initialiser la date avec aujourd'hui
        datePicker.setValue(LocalDate.now());
        
        // Remplir les combobox d'heures (0-23)
        hourComboBox.setItems(FXCollections.observableArrayList(
            IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList())));
        hourComboBox.setValue(9); // Valeur par défaut : 9h
        
        // Remplir les combobox de minutes (0, 15, 30, 45)
        minuteComboBox.setItems(FXCollections.observableArrayList(0, 15, 30, 45));
        minuteComboBox.setValue(0); // Valeur par défaut : 0 minute
        
        // Configurer le spinner de durée (15-480 minutes, par pas de 15)
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(15, 480, 60, 15);
        durationSpinner.setValueFactory(valueFactory);
        
        // Formatter pour afficher les nombres avec 2 chiffres
        StringConverter<Integer> twoDigitConverter = new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
            }
            
            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        };
        
        hourComboBox.setConverter(twoDigitConverter);
        minuteComboBox.setConverter(twoDigitConverter);
        
        // Ajouter l'utilisateur actuel comme participant par défaut
        participants.add(utilisateur.getIdentifiant());
        participantsList.setItems(participants);
    }
    
    @FXML
    private void handleAddParticipant(ActionEvent event) {
        String participant = participantField.getText().trim();
        
        if (participant.isEmpty()) {
            showError("Veuillez saisir un nom de participant");
            return;
        }
        
        if (!participants.contains(participant)) {
            participants.add(participant);
            participantField.clear();
        } else {
            showError("Ce participant est déjà dans la liste");
        }
    }
    
    @FXML
    private void handleRemoveParticipant(ActionEvent event) {
        String selected = participantsList.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showError("Veuillez sélectionner un participant à supprimer");
            return;
        }
        
        // Ne pas permettre de supprimer l'utilisateur actuel
        if (selected.equals(utilisateur.getIdentifiant())) {
            showError("Vous ne pouvez pas vous retirer de la réunion");
            return;
        }
        
        participants.remove(selected);
    }
    
    @FXML
    private void handleAdd(ActionEvent event) {
        try {
            // Validation des champs
            if (titleField.getText().trim().isEmpty()) {
                showError("Le titre est obligatoire");
                return;
            }
            
            if (datePicker.getValue() == null) {
                showError("La date est obligatoire");
                return;
            }
            
            if (hourComboBox.getValue() == null || minuteComboBox.getValue() == null) {
                showError("L'heure est obligatoire");
                return;
            }
            
            if (durationSpinner.getValue() == null || durationSpinner.getValue() < 15) {
                showError("La durée doit être d'au moins 15 minutes");
                return;
            }
            
            if (locationField.getText().trim().isEmpty()) {
                showError("Le lieu est obligatoire");
                return;
            }
            
            if (participants.isEmpty()) {
                showError("Vous devez ajouter au moins un participant");
                return;
            }
            
            // Création des value objects
            TitreEvenement titre = new TitreEvenement(titleField.getText().trim());
            
            LocalDate date = datePicker.getValue();
            DateEvenement dateEvenement = new DateEvenement(
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());
            
            HeureDebut heureDebut = new HeureDebut(
                hourComboBox.getValue(), minuteComboBox.getValue());
            
            DureeEvenement duree = new DureeEvenement(durationSpinner.getValue());
            
            LieuEvenement lieu = new LieuEvenement(locationField.getText().trim());
            
            // Convertir les noms de participants en objets Utilisateur
            List<Utilisateur> participantsUtilisateurs = new ArrayList<>();
            for (String participantName : participants) {
                participantsUtilisateurs.add(new Utilisateur(participantName, "—PLACEHOLDER—"));
            }
            ParticipantsEvenement participantsEvenement = 
                ParticipantsEvenement.avecUtilisateurs(participantsUtilisateurs);
            
            // Ajout de la réunion au calendrier
            calendarManager.ajouterReunion(
                titre, utilisateur, dateEvenement, heureDebut, duree, lieu, participantsEvenement);
            
            // Fermer la fenêtre
            closeDialog();
            
        } catch (Exception e) {
            showError("Erreur: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        closeDialog();
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
    
    private void closeDialog() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}