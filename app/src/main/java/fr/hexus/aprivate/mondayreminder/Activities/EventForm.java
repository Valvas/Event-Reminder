package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.common.primitives.Ints;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIEvents;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.EventCycle;
import fr.hexus.aprivate.mondayreminder.Contracts.LiteAccount;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class EventForm extends Activity
{
    private DateTime eventPlanning = new DateTime(DateTimeZone.getDefault());

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
        EditText titleEvent = findViewById(R.id.EditTxtEventName);
        EditText descEvent = findViewById(R.id.EditTxtDescription);
        Switch switchButton = findViewById(R.id.SwitchRepetition);
        //endregion

        //region Get the inputs
        String title = titleEvent.getText().toString();
        String description = descEvent.getText().toString();
        boolean repetition = switchButton.isChecked();
        EventCycle cycle;
        if(repetition){
            int minutes = (int) ((Spinner) findViewById(R.id.SpinMinutes)).getSelectedItem();
            int heures = (int) ((Spinner) findViewById(R.id.SpinHeures)).getSelectedItem();
            int jours = (int) ((Spinner) findViewById(R.id.SpinJours)).getSelectedItem();
            int mois = (int) ((Spinner) findViewById(R.id.SpinMois)).getSelectedItem();
            int annee = (int) ((Spinner) findViewById(R.id.SpinAnnee)).getSelectedItem();

            cycle = new EventCycle(minutes, heures, jours, mois, annee);
        } else {
            cycle = new EventCycle(0, 0, 0, 0, 0);
        }

        LiteAccount account = new LiteAccount(GlobalVariables.CurrentAccount.getLastName(), GlobalVariables.CurrentAccount.getFirstName(), GlobalVariables.CurrentAccount.getIdentifier());
        //endregion

        //region Send data to API and close activity
        Event event = new Event(title, account, description, eventPlanning, cycle, !repetition);

        try {
            new APIEvents().create(this, event);
        } catch (JSONException jsonEx) {
            Log.e("createNewEvent() Event", jsonEx.getMessage());
        }
        finish();
        //endregion
    }

    /**
     * Setting up the controls comportements and actions
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void RegisterEventsControls(){
        //region Getting controls
        // General controls
        EditText dateAndTimeField= findViewById(R.id.DateAndTimePicker);
        EditText titleEvent = findViewById(R.id.EditTxtEventName);
        EditText descEvent = findViewById(R.id.EditTxtDescription);
        // Interval controls
        Switch switchButton = findViewById(R.id.SwitchRepetition);
        TextView textRepetition = findViewById(R.id.TxtRepetition);
        Spinner minute = findViewById(R.id.SpinMinutes);
        Spinner hour = findViewById(R.id.SpinHeures);
        Spinner day = findViewById(R.id.SpinJours);
        Spinner month = findViewById(R.id.SpinMois);
        Spinner year = findViewById(R.id.SpinAnnee);
        TextView txtMinute = findViewById(R.id.TxtMinutes);
        TextView txtHour = findViewById(R.id.TxtHeures);
        TextView txtDay = findViewById(R.id.TxtJours);
        TextView txtMonth = findViewById(R.id.TxtMois);
        TextView txtYear = findViewById(R.id.TxtAnnee);

        // Button validation
        Button addEvent = findViewById(R.id.ButtonAddEvent);
        //endregion

        //region Register DatePicker and TimePicker

        // What TimePicker do when I click on OK
        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            eventPlanning = eventPlanning.hourOfDay().setCopy(i);
            eventPlanning = eventPlanning.minuteOfHour().setCopy(i1);
            eventPlanning = eventPlanning.secondOfMinute().setCopy(0);

            String timeString = eventPlanning.getHourOfDay() + ":" + eventPlanning.getMinuteOfHour();
            dateAndTimeField.setText(String.format("%s à %s", dateAndTimeField.getText(), timeString));
        };

        // What DatePicker do when i click OK
        DatePickerDialog.OnDateSetListener date = (view, theYear, monthOfYear, dayOfMonth) -> {
            // Setting the calendar date out of selection
            eventPlanning = eventPlanning.year().setCopy(theYear);
            eventPlanning = eventPlanning.monthOfYear().setCopy(monthOfYear);
            eventPlanning = eventPlanning.dayOfMonth().setCopy(dayOfMonth);

            // Render the date on the UI
            String dateString = eventPlanning.getDayOfMonth() + "/" + eventPlanning.getMonthOfYear() + "/" + eventPlanning.getYear();
            dateAndTimeField.setText(dateString);

            // Show TimePicker after I selected the date
            new TimePickerDialog(EventForm.this, time, eventPlanning.getHourOfDay(), eventPlanning.getMinuteOfHour(), true).show();
        };

        // Show DatePicker when I click on the EditText
        dateAndTimeField.setOnClickListener(v -> {
            new DatePickerDialog(EventForm.this, date, eventPlanning
                    .getYear(), eventPlanning.getMonthOfYear(),
                    eventPlanning.getDayOfMonth()).show();
        });
        //endregion

        //region Register Switch

        // Show the Spinner when Switch is on TRUE
        switchButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                textRepetition.setVisibility(View.VISIBLE);
                minute.setVisibility(View.VISIBLE);
                txtMinute.setVisibility(View.VISIBLE);
                hour.setVisibility(View.VISIBLE);
                txtHour.setVisibility(View.VISIBLE);
                day.setVisibility(View.VISIBLE);
                txtDay.setVisibility(View.VISIBLE);
                month.setVisibility(View.VISIBLE);
                txtMonth.setVisibility(View.VISIBLE);
                year.setVisibility(View.VISIBLE);
                txtYear.setVisibility(View.VISIBLE);
            } else {
                textRepetition.setVisibility(View.INVISIBLE);
                minute.setVisibility(View.INVISIBLE);
                txtMinute.setVisibility(View.INVISIBLE);
                hour.setVisibility(View.INVISIBLE);
                txtHour.setVisibility(View.INVISIBLE);
                day.setVisibility(View.INVISIBLE);
                txtDay.setVisibility(View.INVISIBLE);
                month.setVisibility(View.INVISIBLE);
                txtMonth.setVisibility(View.INVISIBLE);
                year.setVisibility(View.INVISIBLE);
                txtYear.setVisibility(View.INVISIBLE);
            }
        });

        // Setup the adapters
        ArrayAdapter<Integer> adapterMinutes = new ArrayAdapter<>(EventForm.this, android.R.layout.simple_spinner_item, Ints.asList(getResources().getIntArray(R.array.minutes_array)));
        ArrayAdapter<Integer> adapterHours = new ArrayAdapter<>(EventForm.this, android.R.layout.simple_spinner_item, Ints.asList(getResources().getIntArray(R.array.hours_array)));
        ArrayAdapter<Integer> adapterDays = new ArrayAdapter<>(EventForm.this, android.R.layout.simple_spinner_item, Ints.asList(getResources().getIntArray(R.array.days_array)));
        ArrayAdapter<Integer> adapterMonths = new ArrayAdapter<>(EventForm.this, android.R.layout.simple_spinner_item, Ints.asList(getResources().getIntArray(R.array.months_array)));
        ArrayAdapter<Integer> adapterYears = new ArrayAdapter<>(EventForm.this, android.R.layout.simple_spinner_item, Ints.asList(getResources().getIntArray(R.array.years_array)));
        // Setup the dropdown
        adapterMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapters in spinners
        minute.setAdapter(adapterMinutes);
        hour.setAdapter(adapterHours);
        day.setAdapter(adapterDays);
        month.setAdapter(adapterMonths);
        year.setAdapter(adapterYears);
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
        return view -> {
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
    }
}
