package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIEvents;
import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIParticipations;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Enums.ParticipatingStatus;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class EventDetails extends Activity
{
    private Event currentEvent = null;
    private TextView eventName = findViewById(R.id.eventName);
    private TextView eventDate = findViewById(R.id.eventDate);
    private TextView eventCycle = findViewById(R.id.eventCycle);
    private TextView eventCreator = findViewById(R.id.eventCreator);
    private TextView eventDescription = findViewById(R.id.eventDescription);

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        currentEvent = (Event) getIntent().getSerializableExtra(getResources().getString(R.string.EVENT));

        initControls();
    }

    private void initControls(){

        // Setting up the action on "Je participe" button
        Button YesResponseButton = findViewById(R.id.participatingYesButton);
        YesResponseButton.setOnClickListener(
                v -> new APIParticipations().UpdateStatus(EventDetails.this, GlobalVariables.CurrentAccount.getIdentifier(), currentEvent.getId(), ParticipatingStatus.YES.getValue())
        );

        // Setting up the action on "Je ne participe pas" button
        Button NoResponseButton = findViewById(R.id.participatingNoButton);
        NoResponseButton.setOnClickListener(
                v -> new APIParticipations().UpdateStatus(EventDetails.this, GlobalVariables.CurrentAccount.getIdentifier(), currentEvent.getId(), ParticipatingStatus.NO.getValue())
        );

        Button backButton = findViewById(R.id.openMenu);
        backButton.setOnClickListener(v -> finish());

        boolean isCreator = currentEvent.getAccountCreator().getIdentifier()
                .equals(GlobalVariables.CurrentAccount.getIdentifier());

        Button DeleteEventButton = findViewById(R.id.deleteEvent);

        if(isCreator){
            DeleteEventButton.setOnClickListener(v -> {
                new APIEvents().delete(EventDetails.this, currentEvent.getId());
                finish();
            });
        } else {
            DeleteEventButton.setVisibility(View.INVISIBLE);
        }

        new APIParticipations().GetParticipation(this,
                GlobalVariables.CurrentAccount.getIdentifier(), currentEvent.getId());

        setTexts(currentEvent);
    }

    private void setTexts(Event event){
        eventName.setText(event.getName());
        eventDate.setText(event.getSimpleDate());
        eventCycle.setText(event.getCycleDetails());

        if(GlobalVariables.CurrentAccount.getIdentifier().equals(event.getCreator())){
            eventCreator.setText("Créé par : Vous");
        } else {
            eventCreator.setText(String.format("Créé par : %s", event.getCreator()));
        }

        eventDescription.setText(String.format("Description : \n\n%s", event.getDescription()));
    }

    public void UpdateParticipatingStatusUI(int status){
        TextView participationStatus = findViewById(R.id.participationStatus);

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

    public void openParticipantsView(View view)
    {
        Intent intent = new Intent(this, Participants.class);

        intent.putExtra(getResources().getString(R.string.EVENT), getIntent().getSerializableExtra(getResources().getString(R.string.EVENT)));

        startActivity(intent);
    }

    public void updateEvent(View view, Event eventToUpdate) throws JSONException {
        eventName.setVisibility(View.INVISIBLE);
        eventDescription.setVisibility(View.INVISIBLE);

        new APIEvents().update(this, eventToUpdate);
    }
}
