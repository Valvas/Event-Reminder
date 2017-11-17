package fr.hexus.aprivate.mondayreminder;

import java.util.ArrayList;
import java.util.List;

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
        Event event2 = new Event("New Year", account, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam.", "2017-12-31 19:00:00", 0, true);

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
}
