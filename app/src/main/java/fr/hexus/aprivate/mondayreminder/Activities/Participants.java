package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import fr.hexus.aprivate.mondayreminder.ApiQueries;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.Participation;
import fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter.ParticipantAdapter;
import fr.hexus.aprivate.mondayreminder.R;

public class Participants extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_view);

        Event event = (Event) getIntent().getSerializableExtra(getResources().getString(R.string.EVENT));

        TextView eventName = (TextView) findViewById(R.id.eventName);

        eventName.setText(getText(R.string.participants_view_event_name) + " : " + event.getName());

        List<Participation> participantsList = new ApiQueries(getString(R.string.API_ADDRESS), getResources().getInteger(R.integer.API_PORT)).getParticipantsToEvent(event);

        ParticipantAdapter participantAdapter = new ParticipantAdapter(this, participantsList);
        setListAdapter(participantAdapter);
    }

    public void goBackToEventView(View view)
    {
        finish();
    }
}
