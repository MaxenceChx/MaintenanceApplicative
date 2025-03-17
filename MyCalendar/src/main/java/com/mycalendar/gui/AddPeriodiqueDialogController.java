package com.mycalendar.gui;

import com.mycalendar.CalendarManager;
import com.mycalendar.valueobjects.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddPeriodiqueDialogController extends EventDialogController {
    
    @FXML private TextField titleField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Integer> hourComboBox;
    @FXML private ComboBox<Integer> minuteComboBox;
    @FXML private ComboBox<String> frequenceComboBox;
    @FXML private Spinner<Integer> frequenceJoursSpinner;
    @FXML private Label errorLabel;
    
    private final Map<String, Integer> frequenceMap = new HashMap<>();
    
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
        
        // Configurer les options de fréquence
        frequenceMap.put("Quotidienne", 1);
        frequenceMap.put("Hebdomadaire", 7);
        frequenceMap.put("Bihebdomadaire", 14);
        frequenceMap.put("Mensuelle", 30);
        frequenceMap.put("Personnalisée", -1);
        
        frequenceComboBox.setItems(FXCollections.observableArrayList(
            Arrays.asList("Quotidienne", "Hebdomadaire", "Bihebdomadaire", "Mensuelle", "Personnalisée")));
        frequenceComboBox.setValue("Hebdomadaire"); // Valeur par défaut
        
        // Configurer le spinner de fréquence personnalisée
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 7);
        frequenceJoursSpinner.setValueFactory(valueFactory);
        frequenceJoursSpinner.setDisable(true); // Désactivé par défaut
        
        // Activer/désactiver le spinner en fonction de la sélection de fréquence
        frequenceComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            frequenceJoursSpinner.setDisable(!newVal.equals("Personnalisée"));
        });
        
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
            
            if (frequenceComboBox.getValue() == null) {
                showError("La fréquence est obligatoire");
                return;
            }
            
            // Création des value objects
            TitreEvenement titre = new TitreEvenement(titleField.getText().trim());
            
            LocalDate date = datePicker.getValue();
            DateEvenement dateEvenement = new DateEvenement(
                date.getYear(), date.getMonthValue(), date.getDayOfMonth());
            
            HeureDebut heureDebut = new HeureDebut(
                hourComboBox.getValue(), minuteComboBox.getValue());
            
            // Déterminer la fréquence
            int joursEntrePeriodes;
            if (frequenceComboBox.getValue().equals("Personnalisée")) {
                joursEntrePeriodes = frequenceJoursSpinner.getValue();
            } else {
                joursEntrePeriodes = frequenceMap.get(frequenceComboBox.getValue());
            }
            
            FrequenceEvenement frequence = new FrequenceEvenement(joursEntrePeriodes);
            
            // Ajout de l'événement périodique au calendrier
            calendarManager.ajouterEvenementPeriodique(
                titre, utilisateur, dateEvenement, heureDebut, frequence);
            
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