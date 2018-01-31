package fr.hexus.aprivate.mondayreminder.Contracts;

import java.io.Serializable;

public class Account implements Serializable
{
    //region Attributes
    private String lastName;
    private String firstName;
    private String identifier;
    private String token;
    //endregion

    //region Contructor
    public Account(String lastname, String firstname, String identifier, String token)
    {
        this.lastName = lastname;
        this.firstName = firstname;
        this.identifier = identifier;
        this.token = token;
    }

    public Account(){}
    //endregion

    //region Getters
    public String getLastName()
    {
        return this.lastName;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getIdentifier() { return this.identifier; }

    public String getToken() { return this.token; }
    //endregion

    //region Override methods
    public boolean equals(Account account) {
        return this.identifier.equals(account.getIdentifier());
    }

    @Override
    public String toString() {
        return "Account{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
    //endregion
}
