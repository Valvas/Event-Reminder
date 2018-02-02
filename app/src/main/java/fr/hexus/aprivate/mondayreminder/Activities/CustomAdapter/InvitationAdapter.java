package fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.hexus.aprivate.mondayreminder.Activities.Invitations;
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

        boolean isConnectedAccount =
                GlobalVariables.CurrentAccount.getIdentifier()
                        .equals(currentInvitation.getSenderIdentifier());

        // If the connected account sent the invitation...
        if (isConnectedAccount){
            invitationEmailField.setText(currentInvitation.getRecipientIdentifier());
            invitationFirstnameField.setText(currentInvitation.getRecipientFirstname());
            invitationLastnameField.setText(currentInvitation.getRecipientLastname());
        } else { // If someone else sent the invitation...
            invitationEmailField.setText(currentInvitation.getSenderIdentifier());
            invitationFirstnameField.setText(currentInvitation.getSenderFirstname());
            invitationLastnameField.setText(currentInvitation.getSenderLastname());
        }

        String fullName = invitationFirstnameField.getText().toString() + " "
                + invitationLastnameField.getText().toString();

        convertView.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Invitation");
            alertDialog.setMessage("Accepter requête d'ami de : " + fullName + " ?");
            Invitations invitations = new Invitations();

            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Accepter",
                    (dialog, which) -> invitations.acceptInvitation(view));

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Refuser",
                    (dialog, which) -> invitations.denyInvitation(view));

            alertDialog.show();
        });

        return convertView;
    }
}
