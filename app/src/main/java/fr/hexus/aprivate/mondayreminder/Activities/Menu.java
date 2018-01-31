package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fr.hexus.aprivate.mondayreminder.R;

public class Menu extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /**
     * Close the Menu activity
     * @param view View
     */
    public void closeMenu(View view)
    {
        finish();
    }

    /**
     * Starts the MyEvents activity
     * @param view View
     */
    public void startMyEventsActivity(View view)
    {
        Intent intent = new Intent(this, MyEvents.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    /**
     * Starts the Friends activity
     * @param view View
     */
    public void startFriendsActivity(View view)
    {
        Intent intent = new Intent(this, Friends.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    /**
     * Starts the Home activity
     * @param view View
     */
    public void startHomeActivity(View view)
    {
        Intent intent = new Intent(this, Home.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    /**
     * Starts the Logon activity
     * @param view View
     */
    public void startLogonActivity(View view){
        Intent intent = new Intent(this, Logon.class);

        intent.putExtra("wantsToBeDisconnected", true);

        startActivity(intent);
    }
}
