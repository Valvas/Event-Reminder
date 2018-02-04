package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIUser;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class Home extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        try {
            new APIUser().sendNotificationToken(this,
                    GlobalVariables.CurrentAccount.getIdentifier(),
                    FirebaseInstanceId.getInstance().getToken());

            Log.d("onCreate() Home",
                    "Account: " + GlobalVariables.CurrentAccount.getIdentifier() +
                    " | Token: " + GlobalVariables.CurrentAccount.getToken() +
                    " | Firebase Token: " + FirebaseInstanceId.getInstance().getToken());

        } catch (Exception e) {
            Log.e("onCreate() Home", e.getMessage());
        }

        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        welcomeMessage.setText(String.format("%s %s",
                welcomeMessage.getText(), GlobalVariables.CurrentAccount.getFirstName()));
    }

    /**
     * Starts the Menu activity
     * @param view View
     */
    public void startMenuActivity(View view)
    {
        Intent menuIntent = new Intent(this, Menu.class);

        startActivity(menuIntent);
    }
}
