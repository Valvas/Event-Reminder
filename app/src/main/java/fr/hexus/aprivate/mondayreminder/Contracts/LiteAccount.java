package fr.hexus.aprivate.mondayreminder.Contracts;

import java.io.Serializable;

/**
 * Created by Nicolas on 28/01/2018.
 */

public class LiteAccount implements Serializable{

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

    public String getFirstName() {
        return firstname;
    }

    public String getIdentifier() {
        return identifier;
    }
}
