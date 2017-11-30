package fr.hexus.aprivate.mondayreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

        startActivity(new Intent(this, LogonPage.class));
    }

    public void startEventsActivity(View view)
    {
        Intent intent = new Intent(this, MyEvents.class);

        intent.putExtra(Constants.ACCOUNT, getIntent().getSerializableExtra(Constants.ACCOUNT));

        startActivity(intent);
    }

    public void startHomeActivity(View view)
    {
        Intent intent = new Intent(this, HomePage.class);

        intent.putExtra(Constants.ACCOUNT, getIntent().getSerializableExtra(Constants.ACCOUNT));

        startActivity(intent);
    }
}
