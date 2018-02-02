package fr.hexus.aprivate.mondayreminder.Contracts;

import java.io.Serializable;

public class Friend implements Serializable
{
    private Account friendOwner;
    private String friendLastname;
    private String friendFirstname;
    private String friendIdentifier;

    public Friend(Account owner, String lastname, String firstname, String identifier)
    {
        this.friendOwner = owner;
        this.friendLastname = lastname;
        this.friendFirstname = firstname;
        this.friendIdentifier = identifier;
    }

    public String getFriendLastname(){ return this.friendLastname; }

    public String getFriendFirstname(){ return this.friendFirstname; }

    public String getFriendIdentifier(){ return this.friendIdentifier; }

    public String getFriendOwner(){ return this.friendOwner.getFirstName() + " " + this.friendOwner.getLastName(); }

    public String toString()
    {
        return "\nFriend : " +
                this.getFriendFirstname() +
                " " +
                this.getFriendLastname() +
                "\nOwner : " +
                this.getFriendOwner();
    }
}
