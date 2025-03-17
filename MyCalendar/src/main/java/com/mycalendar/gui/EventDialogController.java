package com.mycalendar.gui;

import com.mycalendar.CalendarManager;
import com.mycalendar.valueobjects.Utilisateur;

public abstract class EventDialogController {
    
    protected CalendarManager calendarManager;
    protected Utilisateur utilisateur;
    
    public void initialize(CalendarManager calendarManager, Utilisateur utilisateur) {
        this.calendarManager = calendarManager;
        this.utilisateur = utilisateur;
    }
}