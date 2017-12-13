package fr.hexus.aprivate.mondayreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewEventForm extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_form);
    }

    public void backToEventList(View view)
    {
        finish();
    }

    public void createNewEvent(View view)
    {
        EditText titleField = (EditText) findViewById(R.id.titleField);
        EditText dateField = (EditText) findViewById(R.id.eventDateField);
        EditText timeField = (EditText) findViewById(R.id.eventTimeField);
        EditText descriptionField = (EditText) findViewById(R.id.descriptionField);

        String date = dateField.getText().toString();
        String time = timeField.getText().toString();
        String title = titleField.getText().toString();
        String description = descriptionField.getText().toString();

        boolean check = true;

        if(getResources().getBoolean(R.bool.EVENT_TIME_REQUIRED) && title.length() == 0)
        {
            Toast.makeText(this, R.string.event_form_title_empty_message, Toast.LENGTH_SHORT).show();
            check = false;
        }

        else if(getResources().getBoolean(R.bool.EVENT_TIME_REQUIRED) && title.length() < getResources().getInteger(R.integer.EVENT_TITLE_LENGTH))
        {
            Toast.makeText(this, getString(R.string.event_form_title_length_message) + getResources().getInteger(R.integer.EVENT_TITLE_LENGTH), Toast.LENGTH_SHORT).show();
            check = false;
        }

        else if(getResources().getBoolean(R.bool.EVENT_TIME_REQUIRED) == false && title.length() > 0 && title.length() < getResources().getInteger(R.integer.EVENT_TITLE_LENGTH))
        {
            Toast.makeText(this, getString(R.string.event_form_title_length_message) + getResources().getInteger(R.integer.EVENT_TITLE_LENGTH), Toast.LENGTH_SHORT).show();
            check = false;
        }

        if(getResources().getBoolean(R.bool.EVENT_DESCRIPTION_REQUIRED) && description.length() == 0)
        {
            Toast.makeText(this, R.string.event_form_description_empty_message, Toast.LENGTH_SHORT).show();
            check = false;
        }

        else if(getResources().getBoolean(R.bool.EVENT_DESCRIPTION_REQUIRED) && description.length() < getResources().getInteger(R.integer.EVENT_DESCRIPTION_LENGTH))
        {
            Toast.makeText(this, getString(R.string.event_form_description_length_message) + getResources().getInteger(R.integer.EVENT_DESCRIPTION_LENGTH), Toast.LENGTH_SHORT).show();
            check = false;
        }

        else if(getResources().getBoolean(R.bool.EVENT_DESCRIPTION_REQUIRED) == false && description.length() > 0 && description.length() < getResources().getInteger(R.integer.EVENT_DESCRIPTION_LENGTH))
        {
            Toast.makeText(this, getString(R.string.event_form_description_length_message) + getResources().getInteger(R.integer.EVENT_DESCRIPTION_LENGTH), Toast.LENGTH_SHORT).show();
            check = false;
        }

        if(check)
        {
            Account account = (Account) getIntent().getSerializableExtra(getString(R.string.ACCOUNT));

            Event event = new Event(title, account, description, "2017-11-15 09:00:00", 0, true);

            System.out.println(event.toString());

            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
        }
    }
}
