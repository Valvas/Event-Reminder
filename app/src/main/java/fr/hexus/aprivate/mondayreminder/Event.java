package fr.hexus.aprivate.mondayreminder;

public class Event
{
    private long eventCycle;
    private String eventDate;
    private String eventName;
    private boolean isPonctual;
    private Account eventCreator;
    private String eventDescription;

    public Event(String name, Account creator, String description, String date, long cycle, boolean ponctual)
    {
        this.eventDate = date;
        this.eventName = name;
        this.eventCycle = cycle;
        this.isPonctual = ponctual;
        this.eventCreator = creator;
        this.eventDescription = description;
    }

    public String toString()
    {
        return "\nEvent name : "
                + this.eventName
                + "\nDate : "
                + this.eventDate
                + "\nPonctual : "
                + this.isPonctual
                + "\nEvent cycle : "
                + toStringEventCycle()
                + "\nEvent creator : "
                + this.eventCreator.getAccountFirstname()
                + " "
                + this.eventCreator.getAccountLastname()
                + "\nEvent description : "
                + this.eventDescription
                + "\n";
    }

    public String toStringEventCycle()
    {
        long minutesTotalAmount = this.eventCycle;

        long years = minutesTotalAmount / 525600;

        minutesTotalAmount -= years * 525600;

        long months = minutesTotalAmount / 43200;

        minutesTotalAmount -= months * 43200;

        long days = minutesTotalAmount / 1440;

        minutesTotalAmount -= days * 1440;

        long hours = minutesTotalAmount / 60;

        minutesTotalAmount -= hours * 60;

        return years + " year(s) " + months + " month(s) " + days + " day(s) " + hours + " hour(s) " + minutesTotalAmount + " minute(s)";
    }
}
