package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIParticipations;
import fr.hexus.aprivate.mondayreminder.ApiQueries;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.Participation;
import fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter.ParticipantAdapter;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class Participants extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_view);

        Event event = (Event) getIntent().getSerializableExtra(getResources().getString(R.string.EVENT));

        TextView eventName = findViewById(R.id.eventName);

        eventName.setText(getText(R.string.participants_view_event_name) + " : " + event.getName());

        new APIParticipations().GetParticipants(this, event.getId());

        setActionAddParticipants();
    }

    public void goBackToEventView(View view)
    {
        finish();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Participation participation = (Participation) l.getItemAtPosition(position);

        if(participation.participatingAccount.getIdentifier().equalsIgnoreCase(GlobalVariables.CurrentAccount.getIdentifier())){
            return;
        }

        AlertDialog.Builder removeParticipant = new AlertDialog.Builder(this);
        removeParticipant.setTitle("Enlever participant").setMessage("Voulez-vous supprimer cette personne de l'évenement ?");

        removeParticipant.setPositiveButton("Valider", (dialog, which) -> {
            Event event = (Event) getIntent().getSerializableExtra(getResources().getString(R.string.EVENT));
            new APIParticipations().RemoveParticipant(Participants.this, participation.participatingAccount.getIdentifier(), event.getId());
            new APIParticipations().GetParticipants(Participants.this, event.getId());
        });

        removeParticipant.setNegativeButton("NO", (dialog, which) -> dialog.cancel());

        AlertDialog removeDialog = removeParticipant.create();
        removeDialog.show();
    }

    private void setActionAddParticipants(){
        Button addParticipants = findViewById(R.id.addParticipant);
        View layout = getLayoutInflater().inflate(R.layout.activity_participants_view, null);

        AlertDialog.Builder addParticipantDialog = new AlertDialog.Builder(this);
        addParticipantDialog.setView(layout);
        addParticipantDialog.setTitle("Ajouter participant");
        addParticipantDialog.setMessage("Email du participant :");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        addParticipantDialog.setView(input);

        addParticipantDialog.setPositiveButton("Valider", (dialog, which) -> {
            String email = input.getText().toString();
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Event event = (Event) getIntent().getSerializableExtra(getResources().getString(R.string.EVENT));
                new APIParticipations().AddParticipant(Participants.this, email, event.getId(), dialog);
            } else {
                Toast.makeText(Participants.this, "L'adresse n'est pas valide.", Toast.LENGTH_SHORT).show();
            }
        });

        addParticipantDialog.setNegativeButton("NO", (dialog, which) -> dialog.cancel());



        addParticipants.setOnClickListener(v -> {
            AlertDialog dialog = addParticipantDialog.show();

            if(dialog.isShowing())
                dialog.dismiss();
        });
    }
}
