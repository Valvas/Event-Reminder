package fr.hexus.aprivate.mondayreminder;

public class Participation
{
    private int participatingStatus;
    private Event participatedEvent;
    private Account participatingAccount;

    public Participation(Account account, Event event, int status)
    {
        this.participatedEvent = event;
        this.participatingStatus = status;
        this.participatingAccount = account;
    }

    public String toString()
    {
        return this.participatedEvent.getEventName() +
                "\n" +
                this.participatingAccount.getAccountFirstname() +
                " " +
                this.participatingAccount.getAccountLastname();
    }

    public String getParticipantName()
    {
        return this.participatingAccount.getAccountFirstname() +
                " " +
                this.participatingAccount.getAccountLastname();
    }

    public int getParticipationStatus(){ return this.participatingStatus; }
}
