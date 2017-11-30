package fr.hexus.aprivate.mondayreminder;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ApiQueries
{
    private String apiAddress;
    private String apiPort;

    public ApiQueries(String address, String port)
    {
        this.apiAddress = address;
        this.apiPort = port;
    }

    public List<Event> getMyEvents(Account account)
    {
        Event event1 = new Event("Noël", account, "Dinner at home.", "2017-12-24 19:00:00", 0, true);
        Event event2 = new Event("New Year", account, "Dinner at home.", "2017-12-31 19:00:00", 0, true);

        List<Event> eventList = new ArrayList<>();

        eventList.add(event1);
        eventList.add(event2);

        return eventList;
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
        Account account1 = new Account("Lefebvre", "Olivier", "olivier.lefebvre@gmail.com");
        Account account2 = new Account("Cornu", "Nicolas", "nicolas.cornu@gmail.com");

        Participation participation1 = new Participation(account1, event, 0);
        Participation participation2 = new Participation(account2, event, 2);

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
