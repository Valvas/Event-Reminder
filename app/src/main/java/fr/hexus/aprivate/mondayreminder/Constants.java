package fr.hexus.aprivate.mondayreminder;

public class Constants
{
    public static final String API_PORT = "3000";
    public static final String API_ADDRESS = "127.0.0.1";

    public static final String ACCOUNT = "ACCOUNT";
    public static final String EVENT = "EVENT";

    public static final int EVENT_TITLE_LENGTH = 4;
    public static final int EVENT_DESCRIPTION_LENGTH = 8;

    public static final boolean EVENT_DATE_REQUIRED = true;
    public static final boolean EVENT_TIME_REQUIRED = true;
    public static final boolean EVENT_TITLE_REQUIRED = true;
    public static final boolean EVENT_DESCRIPTION_REQUIRED = false;

    public static final int PARTICIPATING_NO = 1;
    public static final int PARTICIPATING_YES = 2;
    public static final int PARTICIPATING_WAIT = 0;

    public static final String PARTICIPATING_NO_TEXT = "NON";
    public static final String PARTICIPATING_YES_TEXT = "OUI";
    public static final String PARTICIPATING_WAIT_TEXT = "EN ATTENTE";

    public static final String JANUARY = "Janvier";
    public static final String FEBRUARY = "Février";
    public static final String MARCH = "Mars";
    public static final String APRIL = "Avril";
    public static final String MAY = "Mai";
    public static final String JUNE = "Juin";
    public static final String JULY = "Juillet";
    public static final String AUGUST = "Août";
    public static final String SEPTEMBER = "Septembre";
    public static final String OCTOBER = "Octobre";
    public static final String NOVEMBER = "Novembre";
    public static final String DECEMBER = "Décembre";

    public static final String PARTICIPANTS_VIEW_EVENT_NAME = "Participants pour";

    public static final String ERROR_UPDATING_PARTICIPATION_STATUS = "Erreur : le changement de statut n'a pas été appliqué";

    public static final String SUCCESS_UPDATING_PARTICIPATION_STATUS = "Votre statut a été mis à jour";
}
