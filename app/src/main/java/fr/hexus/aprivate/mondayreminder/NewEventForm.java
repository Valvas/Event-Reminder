package fr.hexus.aprivate.mondayreminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIEvents;

public class NewEventForm extends AppCompatActivity
{
    private Calendar eventPlanning = Calendar.getInstance();
    private HashMap<String, Integer> choices = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_form);

        InitSpinnerChoices();
        RegisterEventsControls();


    }

    public void backToEventList(View view)
    {
        finish();
    }

    public void createNewEvent(View view)
    {
        //region Getting controls
        EditText titleEvent = (EditText) findViewById(R.id.EditTxtEventName);
        EditText descEvent = (EditText) findViewById(R.id.EditTxtDescription);
        Switch switchButton = (Switch) findViewById(R.id.SwtchRepetition);
        Spinner repetitionChoices = (Spinner) findViewById(R.id.SpinChoices);
        //endregion

        String title = titleEvent.getText().toString();
        String description = descEvent.getText().toString();
        boolean repetition = switchButton.isChecked();
        long cycle = 0;
        if(repetition)
             cycle = choices.get(repetitionChoices.getSelectedItem());
        String dateString = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss").format(eventPlanning.getTime());


        Account account = (Account) getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT));

        Event event = new Event(title, account, description, dateString, cycle, repetition);

        try {
            new APIEvents().Create(this, event);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finishActivity(RESULT_OK);
        System.out.println(event.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void RegisterEventsControls(){
        //region Getting controls
        EditText dateAndTimeField= (EditText) findViewById(R.id.DateAndTimePicker);
        EditText titleEvent = (EditText) findViewById(R.id.EditTxtEventName);
        EditText descEvent = (EditText) findViewById(R.id.EditTxtDescription);
        Switch switchButton = (Switch) findViewById(R.id.SwtchRepetition);
        TextView textRepetition = (TextView) findViewById(R.id.TxtRepetition);
        Spinner repetitionChoices = (Spinner) findViewById(R.id.SpinChoices);
        Button addEvent = (Button) findViewById(R.id.ButtonAddEvent);
        //endregion

        //region Register DatePicker and TimePicker

        // What TimePicker do when I click on OK
        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            eventPlanning.set(Calendar.HOUR, i);
            eventPlanning.set(Calendar.MINUTE, i1);
            eventPlanning.set(Calendar.SECOND, 0);

            String timeString = new SimpleDateFormat("hh:mm").format(eventPlanning.getTime());
            dateAndTimeField.setText(dateAndTimeField.getText() + " à " + timeString);
        };

        // What DatePicker do when i click OK
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // Setting the calendar date out of selection
            eventPlanning.set(Calendar.YEAR, year);
            eventPlanning.set(Calendar.MONTH, monthOfYear);
            eventPlanning.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Render the date on the UI
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(eventPlanning.getTime());
            dateAndTimeField.setText(dateString);

            // Show TimePicker after I selected the date
            new TimePickerDialog(NewEventForm.this, time, eventPlanning.get(Calendar.HOUR), eventPlanning.get(Calendar.MINUTE), true).show();
        };

        // Show DatePicker when I click on the EditText
        dateAndTimeField.setOnClickListener(v -> {
            new DatePickerDialog(NewEventForm.this, date, eventPlanning
                    .get(Calendar.YEAR), eventPlanning.get(Calendar.MONTH),
                    eventPlanning.get(Calendar.DAY_OF_MONTH)).show();


        });
        //endregion

        //region Register Switch

        // Show the Spinner when Switch is on TRUE
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    textRepetition.setVisibility(View.VISIBLE);
                    repetitionChoices.setVisibility(View.VISIBLE);
                } else {
                    textRepetition.setVisibility(View.INVISIBLE);
                    repetitionChoices.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Get a list of choices
        List<String> choicesArrayForAdapter = new ArrayList<>();
        choicesArrayForAdapter.addAll(choices.keySet());
        choicesArrayForAdapter.sort((p1, p2) -> p2.compareTo(p1));

        // Set the list in the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, choicesArrayForAdapter);
        repetitionChoices.setAdapter(adapter);

        //endregion

        //region Register Input Validation

        // Set the validation setting when I click on the button
        addEvent.setOnClickListener(getValidationListener(titleEvent, descEvent, dateAndTimeField));
        //endregion
    }

    private View.OnClickListener getValidationListener(EditText titleEvent, EditText descEvent, EditText dateAndTimeField){
        View.OnClickListener listener = (View.OnClickListener) view -> {
            boolean checkError = false;

            if(titleEvent.getText().toString().length() < getResources().getInteger(R.integer.EVENT_TITLE_LENGTH)){
                checkError = true;
                titleEvent.setError("Le titre doit être de " + getResources().getInteger(R.integer.EVENT_TITLE_LENGTH) + " caractères minimum.");
            }

            if(descEvent.getText().toString().length() < getResources().getInteger(R.integer.EVENT_DESCRIPTION_LENGTH) && getResources().getBoolean(R.bool.EVENT_DESCRIPTION_REQUIRED)){
                checkError = true;
                descEvent.setError("La description doit être de " + getResources().getInteger(R.integer.EVENT_TITLE_LENGTH) + " caractères minimum.");
            }

            if(dateAndTimeField.getText().toString().length() <= 0){
                checkError = true;
                dateAndTimeField.setError("Une date doit être renseignée.");
            }

            if(!checkError){
                createNewEvent(view);
            }
        };
        return listener;
    }

    private void InitSpinnerChoices() {
        choices.put("1 heure", 3600);
        choices.put("6 heures", 21600);
        choices.put("12 heures", 43200);
        choices.put("1 jours", 86400);
        choices.put("1 semaine", 604800);
        choices.put("2 semaines", 1209600);
        choices.put("1 mois", 2419200);
        choices.put("1 trimestre", 7257600);
        choices.put("1 semestre", 14515200);
        choices.put("1 ans", 29030400);
    }

}
