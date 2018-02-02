package fr.hexus.aprivate.mondayreminder.Contracts;

import java.io.Serializable;


public class Invitation implements Serializable {
    private String senderIdentifier;
    private String senderFirstname;
    private String senderLastname;
    private String recipientIdentifier;
    private String recipientFirstname;
    private String recipientLastname;
    private int status;

    public Invitation(String senderIdentifier, String senderFirstname, String senderLastname,
                      String recipientIdenfitier, String recipientFirstname, String recipientLastname,
                      int status)
    {
        this.senderIdentifier = senderIdentifier;
        this.senderFirstname = senderFirstname;
        this.senderLastname = senderLastname;
        this.recipientIdentifier = recipientIdenfitier;
        this.recipientFirstname = recipientFirstname;
        this.recipientLastname = recipientLastname;
        this.status = status;
    }

    public String getSenderIdentifier(){ return this.senderIdentifier; }

    public String getSenderFirstname(){ return this.senderFirstname; }

    public String getSenderLastname(){ return this.senderLastname; }

    public String getRecipientIdentifier(){ return this.recipientIdentifier; }

    public String getRecipientFirstname(){ return this.recipientFirstname; }

    public String getRecipientLastname(){ return this.recipientLastname; }

    public int getStatus(){ return this.status; }

    @Override
    public String toString() {
        return "Invitation{" +
                "recipientIdentifier='" + recipientIdentifier + '\'' +
                ", recipientFirstname='" + recipientFirstname + '\'' +
                ", recipientLastname='" + recipientLastname + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
