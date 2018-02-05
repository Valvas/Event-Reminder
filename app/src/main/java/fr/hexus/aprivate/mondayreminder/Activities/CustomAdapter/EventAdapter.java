package fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.R;

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
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.event_list, parent, false);
        }

        Event currentEvent = getItem(position);

        TextView eventName = convertView.findViewById(R.id.eventName);
        TextView eventDate = convertView.findViewById(R.id.eventDate);
        TextView eventCreator = convertView.findViewById(R.id.eventCreator);

        eventName.setText(currentEvent.getName());
        eventDate.setText(currentEvent.getSimpleDate());
        eventCreator.setText(currentEvent.getCreator());
        UpdateParticipatingStatusUI(currentEvent.getMyParticipationStatus(), convertView);

        return convertView;
    }

    public void UpdateParticipatingStatusUI(int status, View view){
        TextView participationStatus = view.findViewById(R.id.eventParticipation);

        switch(status)
        {
            case 0:
                participationStatus.setText(Html.fromHtml("<font color='#888800'>En attente</font>"));
                break;
            case 1:
                participationStatus.setText(Html.fromHtml("<font color='#FF0921'>Non</font>"));
                break;
            case 2:
                participationStatus.setText(Html.fromHtml("<font color='#77B5FE'>Oui</font>"));
                break;
        }

        participationStatus.setGravity(Gravity.CENTER | Gravity.RIGHT);
        participationStatus.setTextSize(24);
    }
}
