package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIParticipations;
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

        new APIParticipations().GetParticipants(this, event.getId());
    }

    public void goBackToEventView(View view)
    {
        finish();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        AlertDialog.Builder removeParticipant = new AlertDialog.Builder(this);
        removeParticipant.setTitle("Enlever participant").setMessage("Voulez-vous supprimer cette personne de l'évenement ?");

        removeParticipant.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Participation participation = (Participation) l.getItemAtPosition(position);
                Event event = (Event) getIntent().getSerializableExtra(getResources().getString(R.string.EVENT));

                new APIParticipations().RemoveParticipant(Participants.this, participation.participatingAccount.getIdentifier(), event.getId());
            }
        });

        AlertDialog removeDialog = removeParticipant.create();
        removeDialog.show();
    }
}
