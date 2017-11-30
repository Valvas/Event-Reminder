package fr.hexus.aprivate.mondayreminder;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ParticipantsView extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_view);

        Event event = (Event) getIntent().getSerializableExtra(Constants.EVENT);

        TextView eventName = (TextView) findViewById(R.id.eventName);

        eventName.setText(Constants.PARTICIPANTS_VIEW_EVENT_NAME + " : " + event.getEventName());

        List<Participation> participantsList = new ApiQueries(Constants.API_ADDRESS, Constants.API_PORT).getParticipantsToEvent(event);

        ParticipantAdapter participantAdapter = new ParticipantAdapter(this, participantsList);
        setListAdapter(participantAdapter);
    }

    public void goBackToEventView(View view)
    {
        finish();
    }
}
