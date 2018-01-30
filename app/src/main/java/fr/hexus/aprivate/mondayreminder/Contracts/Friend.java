package fr.hexus.aprivate.mondayreminder.Contracts;

public class Friend
{
    private Account friendOwner;
    private String friendLastname;
    private String friendFirstname;
    private String friendIdentifier;

    public Friend(String lastname, String firstname, String identifier, Account owner)
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
