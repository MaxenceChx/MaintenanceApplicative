package com.mycalendar.gui;

import com.mycalendar.CalendarManager;
import com.mycalendar.valueobjects.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddTacheDialogController extends EventDialogController {
    
    @FXML private TextField titleField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Integer> hourComboBox;
    @FXML private ComboBox<Integer> minuteComboBox;
    @FXML private Spinner<Integer> durationSpinner;
    @FXML private ComboBox<String> priorityComboBox;
    @FXML private Label errorLabel;
    
    private final Map<String, PrioriteTache> priorityMap = new HashMap<>();
    
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
        
        // Configurer les options de priorité
        priorityMap.put("Haute", PrioriteTache.HAUTE);
        priorityMap.put("Moyenne", PrioriteTache.MOYENNE);
        priorityMap.put("Basse", PrioriteTache.BASSE);
        
        priorityComboBox.setItems(FXCollections.observableArrayList(
            "Haute", "Moyenne", "Basse"));
        priorityComboBox.setValue("Moyenne"); // Valeur par défaut
        
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
            
            if (priorityComboBox.getValue() == null) {
                showError("La priorité est obligatoire");
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
            
            PrioriteTache priorite = priorityMap.get(priorityComboBox.getValue());
            
            // Ajout de la tâche au calendrier
            calendarManager.ajouterTache(
                titre, utilisateur, dateEvenement, heureDebut, duree, priorite);
            
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