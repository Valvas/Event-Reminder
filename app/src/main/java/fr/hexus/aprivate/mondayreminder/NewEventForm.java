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

    public void openMenu(View view)
    {
        startActivity(new Intent(this, Menu.class));
    }

    public void createNewEvent(View view)
    {
        EditText titleField = (EditText) findViewById(R.id.titleField);
        EditText descriptionField = (EditText) findViewById(R.id.descriptionField);

        String title = titleField.getText().toString();
        String description = descriptionField.getText().toString();

        boolean check = true;

        if(title.length() == 0)
        {
            Toast.makeText(this, R.string.event_form_title_empty_message, Toast.LENGTH_SHORT).show();
            check = false;
        }

        if(description.length() == 0)
        {
            Toast.makeText(this, R.string.event_form_description_empty_message, Toast.LENGTH_SHORT).show();
            check = false;
        }

        if(check)
        {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        }
    }
}
