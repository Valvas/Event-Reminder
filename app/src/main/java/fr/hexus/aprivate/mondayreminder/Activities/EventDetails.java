package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.microedition.khronos.opengles.GL;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIEvents;
import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIParticipations;
import fr.hexus.aprivate.mondayreminder.ApiQueries;
import fr.hexus.aprivate.mondayreminder.Contracts.Account;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Enums.ParticipatingStatus;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class EventDetails extends Activity
{
    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        Event event = (Event) getIntent().getSerializableExtra(getResources().getString(R.string.EVENT));

        if(GlobalVariables.CurrentAccount.getIdentifier().equals(event.getCreator()))
        {
            //ADD A BUTTON TO CANCEL THE EVENT
        }

        TextView participationStatus = (TextView) findViewById(R.id.participationStatus);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
        {
            switch(new ApiQueries(getString(R.string.API_ADDRESS), getResources().getInteger(R.integer.API_PORT)).getParticipationStatusToEvent(event, GlobalVariables.CurrentAccount))
            {
                case 0:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FFFF00'>En attente</font>", Html.FROM_HTML_MODE_LEGACY));
                    break;
                case 1:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FF0921'>Non</font>", Html.FROM_HTML_MODE_LEGACY));
                    break;
                case 2:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#77B5FE'>Oui</font>", Html.FROM_HTML_MODE_LEGACY));
                    break;
            }
        }

        else
        {
            switch(new ApiQueries(getString(R.string.API_ADDRESS), getResources().getInteger(R.integer.API_PORT)).getParticipationStatusToEvent(event, GlobalVariables.CurrentAccount))
            {
                case 0:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#888800'>En attente</font>"));
                    break;
                case 1:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FF0921'>Non</font>"));
                    break;
                case 2:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#77B5FE'>Oui</font>"));
                    break;
            }
        }


    }

    private void initControls(){
        Event event = (Event) getIntent().getSerializableExtra(getResources().getString(R.string.EVENT));

        // Setting up the action on "Je participe" button
        Button YesResponseButton = (Button) findViewById(R.id.participatingYesButton);
        YesResponseButton.setOnClickListener(
                v -> new APIParticipations().UpdateStatus(EventDetails.this, GlobalVariables.CurrentAccount.getIdentifier(), event.getId(), ParticipatingStatus.YES.getValue())
        );

        // Setting up the action on "Je ne participe pas" button
        Button NoResponseButton = (Button) findViewById(R.id.participatingNoButton);
        NoResponseButton.setOnClickListener(
                v -> new APIParticipations().UpdateStatus(EventDetails.this, GlobalVariables.CurrentAccount.getIdentifier(), event.getId(), ParticipatingStatus.NO.getValue())
        );

        Button DeleteEventButton = (Button) findViewById(R.id.deleteevent);
        DeleteEventButton.setOnClickListener(
                v -> new APIEvents().Delete(EventDetails.this, event.getId())
        );

        new APIParticipations().GetParticipation(this, GlobalVariables.CurrentAccount.getIdentifier(), event.getId());

        setTexts(event);
    }

    private void setTexts(Event event){
        TextView eventName = (TextView) findViewById(R.id.eventName);
        TextView eventDate = (TextView) findViewById(R.id.eventDate);
        TextView eventCycle = (TextView) findViewById(R.id.eventCycle);
        TextView eventCreator = (TextView)  findViewById(R.id.eventCreator);
        TextView eventDescription = (TextView)  findViewById(R.id.eventDescription);

        eventName.setText(event.getName());
        eventDate.setText(event.getPrettyDate());
        eventCycle.setText(event.getCycleDetails());

        if(GlobalVariables.CurrentAccount.getIdentifier().equals(event.getCreator())){ eventCreator.setText("Créé par : Vous"); }
        else{ eventCreator.setText("Créé par : " + event.getCreator()); }

        eventDescription.setText("Description : \n\n" + event.getDescription());
    }

    public void UpdateParticipatingStatusUI(int status){
        TextView participationStatus = (TextView) findViewById(R.id.participationStatus);

        switch(status)
        {
            case 0:
                participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#888800'>En attente</font>"));
                break;
            case 1:
                participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FF0921'>Non</font>"));
                break;
            case 2:
                participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#77B5FE'>Oui</font>"));
                break;
        }
    }

    public void getBackToTheList()
    {
        finish();
    }

    public void openParticipantsView(View view)
    {
        Intent intent = new Intent(this, Participants.class);

        intent.putExtra(getResources().getString(R.string.EVENT), getIntent().getSerializableExtra(getResources().getString(R.string.EVENT)));

        startActivity(intent);
    }
}
