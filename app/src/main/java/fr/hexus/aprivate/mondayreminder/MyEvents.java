package fr.hexus.aprivate.mondayreminder;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MyEvents extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        List<Event> eventList = new ApiQueries(getString(R.string.API_ADDRESS), getResources().getInteger(R.integer.API_PORT)).getMyEvents((Account) getIntent().getSerializableExtra(getString(R.string.ACCOUNT)));

        EventAdapter eventAdapter = new EventAdapter(this, eventList);
        setListAdapter(eventAdapter);
    }

    public void openMenu(View view)
    {
        Intent intent = new Intent(this, Menu.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    public void createNewEvent(View view)
    {
        Intent intent = new Intent(this, NewEventForm.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id)
    {
        Event clicked = (Event) getListAdapter().getItem(position);
        super.onListItemClick(listView, view, position, id);

        Intent intent = new Intent(this, EventView.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));
        intent.putExtra(getResources().getString(R.string.EVENT), clicked);

        startActivity(intent);
    }
}
