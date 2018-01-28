package fr.hexus.aprivate.mondayreminder.Contracts;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.io.Serializable;

public class Account implements Serializable
{
    //region Attributes
    private String lastName;
    private String firstname;
    private String identifier;
    private GoogleSignInAccount googleAccount;
    private String token;
    //endregion

    //region Contructor
    public Account(String lastname, String firstname, String identifier, GoogleSignInAccount googleAccount, String token)
    {
        this.lastName = lastname;
        this.firstname = firstname;
        this.identifier = identifier;
        this.googleAccount = googleAccount;
        this.token = token;
    }

    public Account(){}
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
                ", firstname='" + firstname + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
    //endregion
}
