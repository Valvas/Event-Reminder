package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIEvents;
import fr.hexus.aprivate.mondayreminder.Contracts.Account;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class MyEvents extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        APIEvents apiEvents = new APIEvents();

        apiEvents.Get(this, GlobalVariables.CurrentAccount.getIdentifier());

    }

    public void openMenu(View view)
    {
        Intent intent = new Intent(this, Menu.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    public void createNewEvent(View view)
    {
        Intent intent = new Intent(this, EventForm.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id)
    {
        Event clicked = (Event) getListAdapter().getItem(position);
        super.onListItemClick(listView, view, position, id);

        Intent intent = new Intent(this, EventDetails.class);

        intent.putExtra(getResources().getString(R.string.EVENT), clicked);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new APIEvents().Get(this, GlobalVariables.CurrentAccount.getIdentifier());
    }
}
