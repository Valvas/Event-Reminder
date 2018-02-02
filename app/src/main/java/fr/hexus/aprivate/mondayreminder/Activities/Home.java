package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class Home extends Activity
{
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        welcomeMessage.setText(welcomeMessage.getText() + " " + GlobalVariables.CurrentAccount.getFirstName());
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
