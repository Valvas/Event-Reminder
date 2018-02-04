package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter.EventAdapter;
import fr.hexus.aprivate.mondayreminder.Activities.EventDetails;
import fr.hexus.aprivate.mondayreminder.Activities.MyEvents;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.EventCycle;
import fr.hexus.aprivate.mondayreminder.Contracts.LiteAccount;
import fr.hexus.aprivate.mondayreminder.R;

/**
 * Created by Nicolas on 07/01/2018.
 */

public class APIEvents extends APIRequester {

    private final String route = baseURL + "/events/";
    private static List<Event> eventsCache;

    public static Event GetEventFromCache(int eventId){
        final Event[] event = {null};

        eventsCache.forEach((v) -> {
            if(eventId == v.getId()){
                event[0] = v;
            }
        });
        return event[0];
    }

    public void get(final Context context, final String requesterEmail) {
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
                    try {
                        List<Event> eventList = new ArrayList<>();
                        if(result.getBoolean("result")){
                            JSONArray events = result.getJSONArray("events");

                            for(int i = 0; i < events.length(); i++){
                                JSONObject event = events.getJSONObject(i);

                                int id = event.getInt("id");
                                String name = event.getString("name");
                                String description = event.getString("description");
                                boolean isPonctual = event.getBoolean("ponctual");
                                //int cycleMinutes = event.getInt("minutes");
                                int cycleHours = event.getInt("hours");
                                int cycleDays = event.getInt("days");
                                int cycleMonths = event.getInt("months");
                                int cycleYears = event.getInt("years");
                                DateTime date = new DateTime(event.getLong("date"));
                                EventCycle cycle = new EventCycle(0, cycleHours, cycleDays, cycleMonths, cycleYears);

                                JSONObject jsonAccount = event.getJSONObject("account");
                                LiteAccount account = new LiteAccount(jsonAccount.getString("lastname"), jsonAccount.getString("firstname"), jsonAccount.getString("email"));

                                Event eventForList = new Event(name, account, description, date, cycle, isPonctual, id);
                                eventList.add(eventForList);
                            }
                            eventsCache = eventList;

                            EventAdapter eventListAdapter = new EventAdapter(context, eventList);
                            ((MyEvents)context).setListAdapter(eventListAdapter);
                        }

                        TextView loadingText = ((MyEvents)context).findViewById(R.id.LoadText);
                        loadingText.setVisibility(View.INVISIBLE);
                    } catch (JSONException jsonEx) {
                        Log.e("onSuccessReponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception getEx){
            Log.e("get() Event", getEx.getMessage());
        }
    }

    public void create(final Context context, final Event newEvent) throws JSONException {
        JSONObject eventNode = buildEventJSON(newEvent);

        try{
            this.readFromUrl(this.route + "create-new-event", eventNode, Request.Method.POST, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        if(result.getBoolean("result")){
                            Toast.makeText(context, "Évènement créé", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException jsonEx) {
                        Log.e("onSuccessReponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception createEx){
            Log.e("create() Event", createEx.getMessage());
        }
    }

    public void update(final Context context, final Event newEvent) throws JSONException {
        JSONObject eventNode = buildEventJSON(newEvent);

        try{
            this.readFromUrl(this.route + "update-event", eventNode, Request.Method.PUT, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        if(result.getBoolean("result")){
                            Toast.makeText(context, "Évènement mis à jour", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException jsonEx) {
                        Log.e("onSuccessReponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception updateEx){
            Log.e("update() Event", updateEx.getMessage());
        }
    }

    public void delete(final Context context, final int idEvent) {
        try{
            JSONObject eventNode = new JSONObject();

            eventNode.put("event", idEvent);

            this.readFromUrl(this.route + "delete-event", eventNode, Request.Method.POST, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        if(result.getBoolean("result")){
                            Toast.makeText(context, "Évènement annulé", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException jsonEx) {
                        Log.e("onSuccessReponse:Failure", jsonEx.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception deleteEx){
            Log.e("delete() Event", deleteEx.getMessage());
        }
    }

    private JSONObject buildEventJSON(Event event) throws JSONException {
        JSONObject content = new JSONObject();
        JSONObject eventContent = new JSONObject();

        eventContent.put("name", event.getName());
        eventContent.put("description", event.getDescription());
        eventContent.put("date", TimeUnit.MILLISECONDS.toSeconds(event.getDate().getMillis()));
        eventContent.put("isPonctual", event.getCycleState());

        JSONObject timeCycleContent = new JSONObject();

        timeCycleContent.put("years", event.getCycle().getYear());
        timeCycleContent.put("months", event.getCycle().getMonth());
        timeCycleContent.put("days", event.getCycle().getDay());
        timeCycleContent.put("hours", event.getCycle().getHour());
        timeCycleContent.put("minutes", event.getCycle().getMinutes());

        eventContent.put("timeCycle", timeCycleContent);
        content.put("event", eventContent);

        Log.d("JSONObject Event", content.toString(4));

        return content;
    }
}
