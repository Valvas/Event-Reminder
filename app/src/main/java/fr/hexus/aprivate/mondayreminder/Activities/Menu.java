package fr.hexus.aprivate.mondayreminder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fr.hexus.aprivate.mondayreminder.R;

public class Menu extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void closeMenu(View view)
    {
        finish();
    }

    public void logout(View view)
    {
        finishAffinity();

        startActivity(new Intent(this, Logon.class));
    }

    public void startEventsActivity(View view)
    {
        Intent intent = new Intent(this, MyEvents.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    public void startFriendsActivity(View view)
    {
        Intent intent = new Intent(this, Friends.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    public void startHomeActivity(View view)
    {
        Intent intent = new Intent(this, Home.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }
}
