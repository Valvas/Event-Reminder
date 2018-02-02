package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIFriends;
import fr.hexus.aprivate.mondayreminder.R;

public class Friends extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillFriendsList();
    }

    public void startMenuActivity(View view)
    {
        Intent intent = new Intent(this, Menu.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    public void startInvitationActivity(View view){
        Intent intent = new Intent(this, Invitations.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    public void addFriend(View view)
    {
        try {
            String requesterEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            EditText receiverEmailInput = findViewById(R.id.friendEmailInput);
            String receiverEmail = String.valueOf(receiverEmailInput.getText());

            new APIFriends().Add(this, requesterEmail, receiverEmail);
            fillFriendsList();
        } catch (Exception ex){
            Log.e("addFriend", "Error while adding friend.");
        }
    }

    public void deleteFriend(View view){
        try {
            String requesterEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            EditText receiverEmailInput = findViewById(R.id.friendEmailInput);
            String receiverEmail = String.valueOf(receiverEmailInput.getText());

            new APIFriends().Delete(this, requesterEmail, receiverEmail);
            fillFriendsList();
        } catch (Exception ex){
            Log.e("deleteFriend", "Error while deleting a friend.");
        }
    }

    public void fillFriendsList(){
        String requesterEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        try {
            new APIFriends().getMyFriends(this, requesterEmail);
        } catch (JSONException e) {
            Log.e("fillInvitationsList()", e.getMessage());
        }
    }
}
