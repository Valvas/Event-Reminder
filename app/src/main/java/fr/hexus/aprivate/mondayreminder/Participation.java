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
}
