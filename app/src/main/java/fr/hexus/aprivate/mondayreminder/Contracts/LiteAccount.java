package fr.hexus.aprivate.mondayreminder.Contracts;

/**
 * Created by Nicolas on 28/01/2018.
 */

public class LiteAccount {

    private String lastName;
    private String firstname;
    private String identifier;

    public LiteAccount(String lastName, String firstname, String identifier) {
        this.lastName = lastName;
        this.firstname = firstname;
        this.identifier = identifier;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getIdentifier() {
        return identifier;
    }
}
