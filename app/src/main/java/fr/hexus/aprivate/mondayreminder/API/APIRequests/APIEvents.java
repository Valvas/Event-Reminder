package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.common.primitives.Booleans;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter.EventAdapter;
import fr.hexus.aprivate.mondayreminder.Activities.MyEvents;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.EventCycle;
import fr.hexus.aprivate.mondayreminder.R;

/**
 * Created by Nicolas on 07/01/2018.
 */

public class APIEvents extends APIRequester {

    private final String route = this.baseURL + "/events/";
    private List<Event> eventsCache;

    public void Get(final Context context, final String requesterEmail) {
        try{
            JSONObject content = new JSONObject();

            content.put("email", requesterEmail);

            if(eventsCache != null){
                TextView loadingText = ((MyEvents)context).findViewById(R.id.LoadText);
                loadingText.setVisibility(View.INVISIBLE);

                EventAdapter eventListAdapter = new EventAdapter(context, eventsCache);
                ((MyEvents)context).setListAdapter(eventListAdapter);
            }

            this.readFromUrl(this.route + "get-my-events", content, Request.Method.GET, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        List<Event> eventList = new ArrayList<>();
                        if(result.getBoolean("result") == true){


                            JSONObject events = result.getJSONObject("events");
                            Iterator<?> keys = events.keys();

                            while(keys.hasNext()){
                                JSONObject event = events.getJSONObject((String)keys.next());

                                String name = event.getString("name");
                                String description = event.getString("description");
                                String creator = event.getString("account_email");
                                boolean ponctual = event.getInt("is_ponctual") == 1;
                                int cycleHours = event.getInt("cycle_hours");
                                int cycleDays = event.getInt("cycle_days");
                                int cycleMonths = event.getInt("cycle_months");
                                int cycleYears = event.getInt("cycle_years");
                                DateTime date = new DateTime(event.getLong("date"));
                                EventCycle cycle = new EventCycle(0, cycleHours, cycleDays, cycleMonths, cycleYears);

                                Event eventForList = new Event(name, creator, description, date, cycle, ponctual);
                                eventList.add(eventForList);
                            }
                            eventsCache = eventList;

                            EventAdapter eventListAdapter = new EventAdapter(context, eventList);
                            ((MyEvents)context).setListAdapter(eventListAdapter);

                        }

                        TextView loadingText = ((MyEvents)context).findViewById(R.id.LoadText);
                        loadingText.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Create(final Context context, final Event newEvent) throws JSONException {
        JSONObject eventNode = buildEventJSON(newEvent);

        try{
            this.readFromUrl(this.route + "create-new-event", eventNode, Request.Method.POST, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    String test = new String(error.networkResponse.data).toString();
                    Log.i("response", test);

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Update(final Context context, final Event newEvent) throws JSONException {
        JSONObject eventNode = buildEventJSON(newEvent);

        try{
            this.readFromUrl(this.route + "update-event", eventNode, Request.Method.PUT, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Delete(final Context context, final int idEvent) throws JSONException {
        JSONObject eventNode = new JSONObject();

        eventNode.put("event", idEvent);

        try{
            this.readFromUrl(this.route + "delete-event", eventNode, Request.Method.DELETE, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private JSONObject buildEventJSON(Event event) throws JSONException {
        JSONObject eventNode = new JSONObject();
        JSONObject contentNode = new JSONObject();
        JSONObject contentSubNode = new JSONObject();

        contentNode.put("name", event.getName());
        contentNode.put("description", event.getDescription());
        contentNode.put("accountEmail", event.getLinkedAccount().getIdentifier());
        contentNode.put("date", TimeUnit.MILLISECONDS.toSeconds(event.getDate().getMillis()));
        contentNode.put("isPonctual", event.getCycleState());

        contentSubNode.put("minutes", event.getCycle().getMinutes());
        contentSubNode.put("hours", event.getCycle().getHeures());
        contentSubNode.put("days", event.getCycle().getJours());
        contentSubNode.put("months", event.getCycle().getMois());
        contentSubNode.put("years", event.getCycle().getAnnee());

        contentNode.put("timeCycle", contentSubNode);
        eventNode.put("event", contentNode);

        return eventNode;
    }
}
