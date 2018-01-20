package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;

/**
 * Created by Nicolas on 07/01/2018.
 */

public class APIEvents extends APIRequester {

    private final String route = this.baseURL + "/events/";

    public void Get(final Context context, final String requesterEmail) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("email", requesterEmail);

        try{
            this.readFromUrl(this.route + "get-my-events", content, Request.Method.PUT, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT);
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
        contentNode.put("date", DateToTimestamp(event.getSimpleDate()));
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

    private long DateToTimestamp(String rawDate){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = (Date)formatter.parse(rawDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long output=date.getTime()/1000L;
        String str=Long.toString(output);
        long timestamp = Long.parseLong(str) * 1000;
        return timestamp;
    }
}
