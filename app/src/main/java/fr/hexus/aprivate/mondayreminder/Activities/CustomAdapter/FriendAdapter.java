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
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.Friend;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class FriendAdapter extends ArrayAdapter<Friend>
{
    public FriendAdapter(@NonNull Context context, @NonNull List<Friend> friends)
    {
        super(context, 0, friends);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.friend_list, parent, false);
        }
        Friend currentFriend = getItem(position);

        TextView friendEmail = convertView.findViewById(R.id.friendEmail);
        TextView friendFirstname = convertView.findViewById(R.id.friendFirstname);
        TextView friendLastname = convertView.findViewById(R.id.friendLastname);

        friendEmail.setText(currentFriend.getFriendIdentifier());
        friendFirstname.setText(currentFriend.getFriendFirstname());
        friendLastname.setText(currentFriend.getFriendLastname());

        String fullName = friendFirstname.getText().toString() + " "
                + friendLastname.getText().toString();

        convertView.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Supprimer ami");
            alertDialog.setMessage("Supprimer " + fullName + " de votre liste d'amis ?");

            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Oui",
                    (dialog, which) -> deleteFriend(GlobalVariables.CurrentAccount.getIdentifier(),
                            currentFriend.getFriendIdentifier()));

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Non",
                    (dialog, which) -> {});

            alertDialog.show();
        });

        return convertView;
    }

    private void deleteFriend(String ownerEmail, String friendEmail){
        try {
            new APIFriends().delete(getContext(), ownerEmail, friendEmail);
        } catch (Exception ex){
            Log.e("deleteFriend", "Error while deleting a friend:\n"
                    + ex.getMessage());
        }
    }
}
