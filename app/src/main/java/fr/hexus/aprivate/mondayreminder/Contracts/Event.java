package fr.hexus.aprivate.mondayreminder.Contracts;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Locale;

import fr.hexus.aprivate.mondayreminder.Enums.Months;

public class Event implements Serializable
{
    //region Attributes
    private int id;
    /**
     * Name of the event
     */
    private String name;
    /**
     * Date of the event
     */
    private DateTime date;
    /**
     * Description of the event
     */
    private String description;
    /**
     * Account of creator
     */
    private LiteAccount creator;
    /**
     * Time cycle of the event
     */
    private EventCycle cycle;
    /**
     * If time cycle is activated
     */
    private boolean isPonctual;
    //endregion

    //region Constructor
    public Event(String name, LiteAccount creator, String description, DateTime date, EventCycle cycle, boolean isPonctual, int id)
    {
        this.id = id;
        this.date = date;
        this.name = name;
        this.cycle = cycle;
        this.isPonctual = isPonctual;
        this.creator = creator;
        this.description = description;
    }

    public Event(String name, LiteAccount creator, String description, DateTime date, EventCycle cycle, boolean isPonctual)
    {
        this.date = date;
        this.name = name;
        this.cycle = cycle;
        this.isPonctual = isPonctual;
        this.creator = creator;
        this.description = description;
    }
    //endregion

    //region Getters
    public int getId(){ return this.id; }

    public String getName(){ return this.name; }

    public String getDescription(){ return this.description; }

    public LiteAccount getAccountCreator(){ return this.creator; }

    public String getCreator(){
        return this.creator.getFirstName() + " " + this.creator.getLastName();
    }

    //region Date Getters
    /**
     * Return a date which is prettified
     * @return A prettified date
     */
    public String getPrettyDate()
    {
        return "Date : " +
                this.date.getDayOfMonth() +
                " " +
                getTextMonthFromItsNumber(this.date.getMonthOfYear()) +
                " " +
                this.date.getYear();
    }

    /**
     * Return a date formatted with the following pattern : yyyy-MM-dd hh:mm:ss
     * @return A string formatted date
     */
    public String getSimpleDate(){
        return this.date.toString("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    public DateTime getDate(){
        return date;
    }
    //endregion

    //region Cycle Getters
    public EventCycle getCycle(){
        if(cycle != null)
            return this.cycle;
        return new EventCycle(0, 0, 0, 0, 0);
    }

    public String getCycleDetails(){
        if(!this.isPonctual)
            return "Répétition : " + this.cycle.toString();

        return "Répétitif : Non";
    }

    public Boolean getCycleState(){
        if(!this.isPonctual)
            return false;

        return true;
    }
    //endregion
    //endregion

    //region Methods
    /**
     * Get the month name out of it's number
     * @param month Month number
     * @return Name of the month
     */
    private String getTextMonthFromItsNumber(int month)
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
    //endregion

    //region Override methods
    @Override
    public String toString()
    {
        return "\nEvent name : "
                + this.name
                + "\nDate : "
                + this.date
                + "\nPonctual : "
                + this.isPonctual
                + "\nEvent cycle : "
                + this.cycle.toString()
                + "\nEvent creator : "
                + this.creator.getFirstName()
                + " "
                + this.creator.getLastName()
                + "\nEvent description : "
                + this.description
                + "\n";
    }
    //endregion
} 
