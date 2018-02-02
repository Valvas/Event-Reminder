package fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import fr.hexus.aprivate.mondayreminder.Contracts.Participation;
import fr.hexus.aprivate.mondayreminder.Enums.ParticipatingStatus;
import fr.hexus.aprivate.mondayreminder.R;

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
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.participant_list, parent, false);
        }
        Participation currentParticipant = getItem(position);

        TextView participantName = convertView.findViewById(R.id.participantName);
        TextView participationStatus = convertView.findViewById(R.id.participationStatus);

        participantName.setText(currentParticipant.getParticipantName());

        ParticipatingStatus status = currentParticipant.getParticipationStatus();

        switch(status)
        {
            case NO:
                participationStatus.setText(getContext().getText(R.string.PARTICIPATING_NO_TEXT));
                break;
            case WAIT:
                participationStatus.setText(getContext().getText(R.string.PARTICIPATING_WAIT_TEXT));
                break;
            case YES:
                participationStatus.setText(getContext().getText(R.string.PARTICIPATING_YES_TEXT));
                break;
        }

        return convertView;
    }


}
