package fr.hexus.aprivate.mondayreminder.Contracts;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.io.Serializable;

public class Account implements Serializable
{
    //region Instance
    private static Account Instance = null;

    public static Account getInsance(){
        if(Instance != null)
            return null;
        return Instance;
    }

    public static Account CreateInstance(String lastname, String firstname, String identifier, GoogleSignInAccount googleAccount) throws IllegalAccessException {
        if(Instance != null)
            throw new IllegalAccessException("An instance is already setting up.");

        Instance = new Account(lastname, firstname, identifier, googleAccount);
        return Instance;
    }
    //endregion

    //region Attributes
    private String lastName;
    private String firstname;
    private String identifier;
    private GoogleSignInAccount googleAccount;
    //endregion

    //region Contructor
    public Account(String lastname, String firstname, String identifier, GoogleSignInAccount googleAccount)
    {
        this.lastName = lastname;
        this.firstname = firstname;
        this.identifier = identifier;
        this.googleAccount = googleAccount;
    }
    //endregion

    //region Getters
    public String getLastName()
    {
        return this.lastName;
    }

    public String getFirstname()
    {
        return this.firstname;
    }

    public String getIdentifier() { return this.identifier; }

    public GoogleSignInAccount getGoogleAccount() { return this.googleAccount; }
    //endregion

    //region Override methods
    public boolean equals(Account account) {
        return this.identifier.equals(account.getIdentifier());
    }

    @Override
    public String toString() {
        return "Account{" +
                "lastName='" + lastName + '\'' +
                ", firstname='" + firstname + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
    //endregion
}
