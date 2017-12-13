package fr.hexus.aprivate.mondayreminder;

import fr.hexus.aprivate.mondayreminder.Enums.ParticipatingStatus;

public class Participation
{
    private ParticipatingStatus participatingStatus;
    private Event participatedEvent;
    private Account participatingAccount;

    public Participation(Account account, Event event, ParticipatingStatus status)
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

    public ParticipatingStatus getParticipationStatus(){ return this.participatingStatus; }
}
