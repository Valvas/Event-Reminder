package fr.hexus.aprivate.mondayreminder.Contracts;

import fr.hexus.aprivate.mondayreminder.Enums.ParticipatingStatus;

public class Participation
{
    public ParticipatingStatus participatingStatus;
    public Event participatedEvent;
    public LiteAccount participatingAccount;

    public Participation(LiteAccount account, Event event, ParticipatingStatus status)
    {
        this.participatedEvent = event;
        this.participatingStatus = status;
        this.participatingAccount = account;
    }

    public String toString()
    {
        return this.participatedEvent.getName() +
                "\n" +
                this.participatingAccount.getFirstname() +
                " " +
                this.participatingAccount.getLastName();
    }

    public String getParticipantName()
    {
        return this.participatingAccount.getFirstname() +
                " " +
                this.participatingAccount.getLastName();
    }

    public ParticipatingStatus getParticipationStatus(){ return this.participatingStatus; }
}
