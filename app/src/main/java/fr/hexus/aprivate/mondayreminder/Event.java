package fr.hexus.aprivate.mondayreminder;

import java.io.Serializable;
import java.util.HashMap;

import fr.hexus.aprivate.mondayreminder.Enums.Months;

public class Event implements Serializable
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

    public Account getLinkedAccount(){ return this.eventCreator; }

    public String getEventName(){ return this.eventName; }
    public String getEventDescription(){ return this.eventDescription; }
    public String getEventCreator(){ return this.eventCreator.getAccountFirstname() + " " + this.eventCreator.getAccountLastname(); }

    public String getEventDate()
    {
        return "Date : " +
                this.eventDate.substring(8, 10) +
                " " +
                getTextMonthFromItsNumber(Integer.parseInt(this.eventDate.substring(5, 7))) +
                " " +
                this.eventDate.substring(0, 4);
    }

    public String getTextMonthFromItsNumber(int month)
    {
        switch(month)
        {
            case 1: return Months.Janvier.toString();
            case 2: return Months.Février.toString();
            case 3: return Months.Mars.toString();
            case 4: return Months.Avril.toString();
            case 5: return Months.Mai.toString();
            case 6: return Months.Juin.toString();
            case 7: return Months.Juillet.toString();
            case 8: return Months.Août.toString();
            case 9: return Months.Septembre.toString();
            case 10: return Months.Octobre.toString();
            case 11: return Months.Novembre.toString();
            case 12: return Months.Décembre.toString();
            default: return "???";
        }
    }

    @Override
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

    public HashMap<String, Long> getDetailsEventCycle(){
        HashMap<String, Long> ret = new HashMap<>();

        Long minutesTotalAmount = this.eventCycle;
        Long years = minutesTotalAmount / 525600;
        ///
        ret.put("years", years);
        ///
        minutesTotalAmount -= years * 525600;
        Long months = minutesTotalAmount / 43200;
        ///
        ret.put("month", months);
        ///
        minutesTotalAmount -= months * 43200;
        Long days = minutesTotalAmount / 1440;
        ///
        ret.put("days", days);
        ///
        minutesTotalAmount -= days * 1440;
        Long hours = minutesTotalAmount / 60;
        ///
        ret.put("hours", hours);
        ///
        minutesTotalAmount -= hours * 60;
        ///
        ret.put("minutes", minutesTotalAmount);

        return ret;
    }

    public String getEventCycle()
    {
        if(!this.isPonctual){ return "Répétitif : Non"; }

        else
        {
            return "Répétition : " + toStringEventCycle();
        }
    }

    public Boolean getEventCycleBoolean()
    {
        if(!this.isPonctual){ return false; }

        else
        {
            return true;
        }
    }
}
