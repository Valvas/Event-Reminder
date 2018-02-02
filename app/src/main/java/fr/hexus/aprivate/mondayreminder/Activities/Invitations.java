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


public class Invitations extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillInvitationsList();
    }

    public void startMenuActivity(View view)
    {
        Intent intent = new Intent(this, Menu.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    public void startFriendsActivity(View view)
    {
        Intent intent = new Intent(this, Friends.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    public void denyInvitation(View view){
        try {
            Log.i("OK", "It works!");
        } catch (Exception ex){
            Log.e("denyInvitation", "Error while denying an invitation.");
        }
    }

    public void acceptInvitation(View view){
        try {
            Log.i("OK", "It works!");
        } catch (Exception ex){
            Log.e("acceptInvitation", "Error while accepting an invitation.");
        }
    }

    public void fillInvitationsList(){
        String requesterEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        try {
            new APIFriends().getMyInvitations(this, requesterEmail);
        } catch (JSONException e) {
            Log.e("fillInvitationsList()", e.getMessage());
        }
    }
}