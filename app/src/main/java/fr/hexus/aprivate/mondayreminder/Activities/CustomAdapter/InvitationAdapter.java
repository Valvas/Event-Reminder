package fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIFriends;
import fr.hexus.aprivate.mondayreminder.Contracts.Invitation;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;


public class InvitationAdapter extends ArrayAdapter<Invitation> {
    public InvitationAdapter(@NonNull Context context, @NonNull List<Invitation> invitations)
    {
        super(context, 0, invitations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.invitation_list, parent, false);
        }

        Invitation currentInvitation = getItem(position);

        TextView invitationEmailField = convertView.findViewById(R.id.invitationEmail);
        TextView invitationFirstnameField = convertView.findViewById(R.id.invitationFirstname);
        TextView invitationLastnameField = convertView.findViewById(R.id.invitationLastname);

        boolean isCurrentAccountSender =
                GlobalVariables.CurrentAccount.getIdentifier()
                        .equals(currentInvitation.getSenderIdentifier());

        if (isCurrentAccountSender){ // If the connected account sent the invitation...

            invitationEmailField.setText(currentInvitation.getRecipientIdentifier());
            invitationFirstnameField.setText(currentInvitation.getRecipientFirstname());
            invitationLastnameField.setText(currentInvitation.getRecipientLastname());

            String fullName = invitationFirstnameField.getText().toString() + " "
                    + invitationLastnameField.getText().toString();

            convertView.setOnClickListener(view -> {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Demande d'ami");
                alertDialog.setMessage("Annuler la requête d'ami envoyée à " + fullName + " ?");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Oui",
                        (dialog, which) ->
                                cancelInvitation(currentInvitation.getSenderIdentifier(),
                                        currentInvitation.getRecipientIdentifier()));

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Non",
                        (dialog, which) -> {});

                alertDialog.show();
            });

        } else { // If someone else sent the invitation...

            invitationEmailField.setText(currentInvitation.getSenderIdentifier());
            invitationFirstnameField.setText(currentInvitation.getSenderFirstname());
            invitationLastnameField.setText(currentInvitation.getSenderLastname());

            String fullName = invitationFirstnameField.getText().toString() + " "
                    + invitationLastnameField.getText().toString();

            convertView.setOnClickListener(view -> {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Demande d'ami");
                alertDialog.setMessage("Accepter la requête d'ami de " + fullName + " ?");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Accepter",
                        (dialog, which) ->
                                confirmInvitation(currentInvitation.getSenderIdentifier()));

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Refuser",
                        (dialog, which) ->
                                denyInvitation(currentInvitation.getSenderIdentifier()));

                alertDialog.show();
            });
        }

        return convertView;
    }

    private void cancelInvitation(String ownerEmail, String friendEmail){
        try {
            boolean isFromInvitations = true;
            new APIFriends().delete(getContext(), ownerEmail, friendEmail, isFromInvitations);
        } catch (Exception ex){
            Log.e("cancelInvitation", "Error while cancelling an invitation:\n"
                    + ex.getMessage());
        }
    }

    private void denyInvitation(String friendEmail){
        try {
            new APIFriends().denyInvitation(getContext(), friendEmail);
        } catch (Exception ex){
            Log.e("denyInvitation", "Error while denying an invitation:\n"
                    + ex.getMessage());
        }
    }

    private void confirmInvitation(String friendEmail){
        try {
            new APIFriends().confirmInvitation(getContext(), friendEmail);
        } catch (Exception ex){
            Log.e("confirmInvitation", "Error while accepting an invitation:\n"
                    + ex.getMessage());
        }
    }
}
