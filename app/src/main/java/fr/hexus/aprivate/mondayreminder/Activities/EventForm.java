package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.common.primitives.Ints;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIEvents;
import fr.hexus.aprivate.mondayreminder.Contracts.Account;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.EventCycle;
import fr.hexus.aprivate.mondayreminder.R;

public class EventForm extends AppCompatActivity
{
    private Calendar eventPlanning = Calendar.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_form);

        RegisterEventsControls();
    }

    /**
     * Get the inputs and create event
     */
    public void createNewEvent() {
        //region Getting controls
        EditText titleEvent = (EditText) findViewById(R.id.EditTxtEventName);
        EditText descEvent = (EditText) findViewById(R.id.EditTxtDescription);
        Switch switchButton = (Switch) findViewById(R.id.SwtchRepetition);
        //endregion

        //region Get the inputs
        String title = titleEvent.getText().toString();
        String description = descEvent.getText().toString();
        boolean repetition = switchButton.isChecked();
        EventCycle cycle = null;
        if(repetition){
            int minutes = (int) ((Spinner) findViewById(R.id.SpinMinutes)).getSelectedItem();
            int heures = (int) ((Spinner) findViewById(R.id.SpinHeures)).getSelectedItem();
            int jours = (int) ((Spinner) findViewById(R.id.SpinJours)).getSelectedItem();
            int mois = (int) ((Spinner) findViewById(R.id.SpinMois)).getSelectedItem();
            int annee = (int) ((Spinner) findViewById(R.id.SpinAnnee)).getSelectedItem();

            cycle = new EventCycle(minutes, heures, jours, mois, annee);
        }
        String dateString = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss").format(eventPlanning.getTime());
        Account account = (Account) getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT));
        //endregion

        //region Send data to API and close activity
        Event event = new Event(title, account, description, eventPlanning, cycle, repetition);

        try {
            new APIEvents().Create(this, event);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finish();
        //endregion

        //DEBUG LINE
        System.out.println(event.toString());
    }

    /**
     * Setting up the controls comportements and actions
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void RegisterEventsControls(){
        //region Getting controls
        // General controls
        EditText dateAndTimeField= (EditText) findViewById(R.id.DateAndTimePicker);
        EditText titleEvent = (EditText) findViewById(R.id.EditTxtEventName);
        EditText descEvent = (EditText) findViewById(R.id.EditTxtDescription);
        // Interval controls
        Switch switchButton = (Switch) findViewById(R.id.SwtchRepetition);
        TextView textRepetition = (TextView) findViewById(R.id.TxtRepetition);
        Spinner minutes = (Spinner) findViewById(R.id.SpinMinutes);
        Spinner heures = (Spinner) findViewById(R.id.SpinHeures);
        Spinner jours = (Spinner) findViewById(R.id.SpinJours);
        Spinner mois = (Spinner) findViewById(R.id.SpinMois);
        Spinner annee = (Spinner) findViewById(R.id.SpinAnnee);
        TextView txtMinutes = (TextView) findViewById(R.id.TxtMinutes);
        TextView txtHeures = (TextView) findViewById(R.id.TxtHeures);
        TextView txtJours = (TextView) findViewById(R.id.TxtJours);
        TextView txtMois = (TextView) findViewById(R.id.TxtMois);
        TextView txtAnnee = (TextView) findViewById(R.id.TxtAnnee);
        // Button validation
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
            new TimePickerDialog(EventForm.this, time, eventPlanning.get(Calendar.HOUR), eventPlanning.get(Calendar.MINUTE), true).show();
        };

        // Show DatePicker when I click on the EditText
        dateAndTimeField.setOnClickListener(v -> {
            new DatePickerDialog(EventForm.this, date, eventPlanning
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
                    minutes.setVisibility(View.VISIBLE);
                    txtMinutes.setVisibility(View.VISIBLE);
                    heures.setVisibility(View.VISIBLE);
                    txtHeures.setVisibility(View.VISIBLE);
                    jours.setVisibility(View.VISIBLE);
                    txtJours.setVisibility(View.VISIBLE);
                    mois.setVisibility(View.VISIBLE);
                    txtMois.setVisibility(View.VISIBLE);
                    annee.setVisibility(View.VISIBLE);
                    txtAnnee.setVisibility(View.VISIBLE);
                } else {
                    textRepetition.setVisibility(View.INVISIBLE);
                    minutes.setVisibility(View.INVISIBLE);
                    txtMinutes.setVisibility(View.INVISIBLE);
                    heures.setVisibility(View.INVISIBLE);
                    txtHeures.setVisibility(View.INVISIBLE);
                    jours.setVisibility(View.INVISIBLE);
                    txtJours.setVisibility(View.INVISIBLE);
                    mois.setVisibility(View.INVISIBLE);
                    txtMois.setVisibility(View.INVISIBLE);
                    annee.setVisibility(View.INVISIBLE);
                    txtAnnee.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Setup the adapters
        ArrayAdapter<Integer> adapterMinutes = new ArrayAdapter<Integer>(EventForm.this, R.layout.support_simple_spinner_dropdown_item, Ints.asList(getResources().getIntArray(R.array.minutes_array)));
        ArrayAdapter<Integer> adapterHours = new ArrayAdapter<Integer>(EventForm.this, R.layout.support_simple_spinner_dropdown_item, Ints.asList(getResources().getIntArray(R.array.hours_array)));
        ArrayAdapter<Integer> adapterDays = new ArrayAdapter<Integer>(EventForm.this, R.layout.support_simple_spinner_dropdown_item, Ints.asList(getResources().getIntArray(R.array.days_array)));
        ArrayAdapter<Integer> adapterMonths = new ArrayAdapter<Integer>(EventForm.this, R.layout.support_simple_spinner_dropdown_item, Ints.asList(getResources().getIntArray(R.array.months_array)));
        ArrayAdapter<Integer> adapterYears = new ArrayAdapter<Integer>(EventForm.this, R.layout.support_simple_spinner_dropdown_item, Ints.asList(getResources().getIntArray(R.array.years_array)));
        // Set the adapters in spinners
        minutes.setAdapter(adapterMinutes);
        heures.setAdapter(adapterHours);
        jours.setAdapter(adapterDays);
        mois.setAdapter(adapterMonths);
        annee.setAdapter(adapterYears);
        //endregion

        //region Register Input Validation

        // Set the validation setting when I click on the button
        addEvent.setOnClickListener(getValidationListener(titleEvent, descEvent, dateAndTimeField));
        //endregion
    }

    /**
     * Get the OnClickListener for the button. The listener job is to validate the inputs.
     * @param titleEvent EditText for event title
     * @param descEvent EditText for event description
     * @param dateAndTimeField EditText for event date and time
     * @return A OnClickListener configured
     */
    private View.OnClickListener getValidationListener(EditText titleEvent, EditText descEvent, EditText dateAndTimeField){
        View.OnClickListener listener = view -> {
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
                createNewEvent();
            }
        };
        return listener;
    }
}
