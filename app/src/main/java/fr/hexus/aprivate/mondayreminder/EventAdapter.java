package fr.hexus.aprivate.mondayreminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event>
{
    public EventAdapter(@NonNull Context context, @NonNull List<Event> events)
    {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.event_list, null);

        Event currentEvent = getItem(position);

        TextView eventName = row.findViewById(R.id.eventName);
        TextView eventDate = row.findViewById(R.id.eventDate);
        TextView eventCreator = row.findViewById(R.id.eventCreator);

        eventName.setText(currentEvent.getEventName());
        eventDate.setText(currentEvent.getEventDate());
        eventCreator.setText(currentEvent.getEventCreator());

        return row;
    }
}
