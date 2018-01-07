package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Event;

/**
 * Created by Nicolas on 07/01/2018.
 */

public class APIEvents extends APIRequester {

    private final String route = this.baseURL + "/events/";

    public void Get(final Context context, final String requesterEmail) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("email", requesterEmail);

        try{
            this.readFromUrl(this.route + "get-my-events", content, "PUT", context, new APICallback() {
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

    public void Create(final Context context, final Event newEvent) throws JSONException {
        JSONObject eventNode = buildEventJSON(newEvent);

        try{
            this.readFromUrl(this.route + "create-new-event", eventNode, "POST", context, new APICallback() {
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

    public void Update(final Context context, final Event newEvent) throws JSONException {
        JSONObject eventNode = buildEventJSON(newEvent);

        try{
            this.readFromUrl(this.route + "update-event", eventNode, "PUT", context, new APICallback() {
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
            this.readFromUrl(this.route + "delete-event", eventNode, "DELETE", context, new APICallback() {
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

        contentNode.put("name", event.getEventName());
        contentNode.put("description", event.getEventDescription());
        contentNode.put("accountEmail", event.getLinkedAccount().getAccountIdentifier());
        contentNode.put("date", event.getEventDate());
        contentNode.put("isPonctual", event.getEventCycleBoolean());

        for(Map.Entry<String, Long> entry : event.getDetailsEventCycle().entrySet()){
            contentSubNode.put(entry.getKey(), entry.getValue());
        }

        contentNode.put("timeCycle", contentSubNode);
        eventNode.put("event", contentNode);

        return eventNode;
    }

}
