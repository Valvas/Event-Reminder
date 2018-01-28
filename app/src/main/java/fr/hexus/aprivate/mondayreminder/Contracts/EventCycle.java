package fr.hexus.aprivate.mondayreminder.Contracts;

import java.io.Serializable;

/**
 * Created by nicolas on 19/01/18.
 */

public class EventCycle implements Serializable{

    private int minutes;
    private int heures;
    private int jours;
    private int mois;
    private int annee;

    public EventCycle(int minutes, int heures, int jours, int mois, int annee) {
        this.minutes = minutes;
        this.heures = heures;
        this.jours = jours;
        this.mois = mois;
        this.annee = annee;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHeures() {
        return heures;
    }

    public int getJours() {
        return jours;
    }

    public int getMois() {
        return mois;
    }

    public int getAnnee() {
        return annee;
    }

    @Override
    public String toString() {
        return annee + " year(s) " + mois + " month(s) " + jours + " day(s) " + heures + " hour(s) " + minutes + " minute(s)";
    }
}
