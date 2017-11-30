package fr.hexus.aprivate.mondayreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EventView extends AppCompatActivity
{
    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        Event event = (Event) getIntent().getSerializableExtra(Constants.EVENT);
        Account account = (Account) getIntent().getSerializableExtra(Constants.ACCOUNT);

        if(account.equals(event.getLinkedAccount()))
        {
            //ADD A BUTTON TO CANCEL THE EVENT
        }

        TextView participationStatus = (TextView) findViewById(R.id.participationStatus);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
        {
            switch(new ApiQueries(Constants.API_ADDRESS, Constants.API_PORT).getParticipationStatusToEvent(event, account))
            {
                case 0:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FFFF00'>En attente</font>", Html.FROM_HTML_MODE_LEGACY));
                    break;
                case 1:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FF0921'>Non</font>", Html.FROM_HTML_MODE_LEGACY));
                    break;
                case 2:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#77B5FE'>Oui</font>", Html.FROM_HTML_MODE_LEGACY));
                    break;
            }
        }

        else
        {
            switch(new ApiQueries(Constants.API_ADDRESS, Constants.API_PORT).getParticipationStatusToEvent(event, account))
            {
                case 0:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#888800'>En attente</font>"));
                    break;
                case 1:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FF0921'>Non</font>"));
                    break;
                case 2:
                    participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#77B5FE'>Oui</font>"));
                    break;
            }
        }

        TextView eventName = (TextView) findViewById(R.id.eventName);
        TextView eventDate = (TextView) findViewById(R.id.eventDate);
        TextView eventCycle = (TextView) findViewById(R.id.eventCycle);
        TextView eventCreator = (TextView)  findViewById(R.id.eventCreator);
        TextView eventDescription = (TextView)  findViewById(R.id.eventDescription);

        eventName.setText(event.getEventName());
        eventDate.setText(event.getEventDate());
        eventCycle.setText(event.getEventCycle());

        if(account.equals(event.getLinkedAccount())){ eventCreator.setText("Créé par : Vous"); }
        else{ eventCreator.setText("Créé par : " + event.getEventCreator()); }

        eventDescription.setText("Description : \n\n" + event.getEventDescription());
    }

    public void getBackToTheList(View view)
    {
        finish();
    }

    public void openParticipantsView(View view)
    {
        Intent intent = new Intent(this, ParticipantsView.class);

        intent.putExtra(Constants.EVENT, getIntent().getSerializableExtra(Constants.EVENT));

        startActivity(intent);
    }

    public void clickOnParticipatingYes(View view)
    {
        ApiQueries apiQueries = new ApiQueries(Constants.API_ADDRESS, Constants.API_PORT);

        if(apiQueries.changeAccountParticipationStatusToEvent((Event) getIntent().getSerializableExtra(Constants.EVENT), (Account) getIntent().getSerializableExtra(Constants.ACCOUNT), Constants.PARTICIPATING_YES))
        {
            TextView participationStatus = (TextView) findViewById(R.id.participationStatus);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#77B5FE'>Oui</font>", Html.FROM_HTML_MODE_LEGACY));
            }

            else
            {
                participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#77B5FE'>Oui</font>"));
            }

            Toast.makeText(this, Constants.SUCCESS_UPDATING_PARTICIPATION_STATUS, Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this, Constants.ERROR_UPDATING_PARTICIPATION_STATUS, Toast.LENGTH_SHORT).show();
        }
    }

    public void clickOnParticipatingNo(View view)
    {
        ApiQueries apiQueries = new ApiQueries(Constants.API_ADDRESS, Constants.API_PORT);

        if(apiQueries.changeAccountParticipationStatusToEvent((Event) getIntent().getSerializableExtra(Constants.EVENT), (Account) getIntent().getSerializableExtra(Constants.ACCOUNT), Constants.PARTICIPATING_NO))
        {
            TextView participationStatus = (TextView) findViewById(R.id.participationStatus);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            {
                participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FF0921'>Non</font>", Html.FROM_HTML_MODE_LEGACY));
            }

            else
            {
                participationStatus.setText(Html.fromHtml("Vous participez : " + "<font color='#FF0921'>Non</font>"));
            }

            Toast.makeText(this, Constants.SUCCESS_UPDATING_PARTICIPATION_STATUS, Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this, Constants.ERROR_UPDATING_PARTICIPATION_STATUS, Toast.LENGTH_SHORT).show();
        }
    }
}
