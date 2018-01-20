package fr.hexus.aprivate.mondayreminder;

import java.util.ArrayList;
import java.util.List;

import fr.hexus.aprivate.mondayreminder.Contracts.Account;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.Friend;
import fr.hexus.aprivate.mondayreminder.Contracts.Participation;
import fr.hexus.aprivate.mondayreminder.Enums.ParticipatingStatus;

public class ApiQueries
{
    private String apiAddress;
    private int apiPort;

    public ApiQueries(String address, int port)
    {
        this.apiAddress = address;
        this.apiPort = port;
    }

    public List<Friend> getMyFriends(Account account)
    {
        Friend friend1 = new Friend("Cornu", "Nicolas", "nicolas.cornu@gmail.com", account);
        Friend friend2 = new Friend("Valvas", "Vincent", "vincent.valvas@gmail.com", account);

        List<Friend> friendList = new ArrayList<>();

        friendList.add(friend1);
        friendList.add(friend2);

        return friendList;
    }

    public List<Participation> getParticipantsToEvent(Event event)
    {
        Account account1 = new Account("Lefebvre", "Olivier", "olivier.lefebvre@gmail.com", null);
        Account account2 = new Account("Cornu", "Nicolas", "nicolas.cornu@gmail.com", null);

        Participation participation1 = new Participation(account1, event, ParticipatingStatus.WAIT);
        Participation participation2 = new Participation(account2, event, ParticipatingStatus.YES);

        List<Participation> participantsList = new ArrayList<>();

        participantsList.add(participation1);
        participantsList.add(participation2);

        return participantsList;
    }

    public boolean changeAccountParticipationStatusToEvent(Event event, Account account, int status)
    {
        return true;
    }

    public int getParticipationStatusToEvent(Event event, Account account)
    {
        return 0;
    }
}
