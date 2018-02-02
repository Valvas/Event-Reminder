package fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.Friend;
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

        return convertView;
    }
}
