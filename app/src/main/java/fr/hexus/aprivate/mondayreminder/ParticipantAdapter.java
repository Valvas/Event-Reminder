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

public class ParticipantAdapter extends ArrayAdapter<Participation>
{
    public ParticipantAdapter(@NonNull Context context, @NonNull List<Participation> participants)
    {
        super(context, 0, participants);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.participant_list, null);

        Participation currentParticipant = getItem(position);

        TextView participantName = row.findViewById(R.id.participantName);
        TextView participationStatus = row.findViewById(R.id.participationStatus);

        participantName.setText(currentParticipant.getParticipantName());

        int status = currentParticipant.getParticipationStatus();

        switch(status)
        {
            case Constants.PARTICIPATING_NO:
                participationStatus.setText(Constants.PARTICIPATING_NO_TEXT);
                break;
            case Constants.PARTICIPATING_WAIT:
                participationStatus.setText(Constants.PARTICIPATING_WAIT_TEXT);
                break;
            case Constants.PARTICIPATING_YES:
                participationStatus.setText(Constants.PARTICIPATING_YES_TEXT);
                break;
        }

        return row;
    }
}
