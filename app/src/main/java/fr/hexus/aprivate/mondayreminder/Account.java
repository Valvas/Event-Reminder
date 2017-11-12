package fr.hexus.aprivate.mondayreminder;

public class Account
{
    private String accountLastname;
    private String accountFirstname;
    private String accountIdentifier;

    public Account(String lastname, String firstname, String identifier)
    {
        this.accountLastname = lastname;
        this.accountFirstname = firstname;
        this.accountIdentifier = identifier;
    }

    public String getAccountLastname()
    {
        return this.accountLastname;
    }

    public String getAccountFirstname()
    {
        return this.accountFirstname;
    }

    public String getAccountIdentifier() { return this.accountIdentifier; }
}
