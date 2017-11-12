package fr.hexus.aprivate.mondayreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyEvents extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
    }

    public void openMenu(View view)
    {
        startActivity(new Intent(this, Menu.class));
    }

    public void createNewEvent(View view)
    {
        startActivity(new Intent(this, NewEventForm.class));
    }
}
