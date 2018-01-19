package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;

/**
 * Created by Nicolas on 07/01/2018.
 */

public class APIParticipations extends APIRequester {

    private final String route = this.baseURL + "/participations/";

    public void AddParticipant(final Context context, final String participantID, final int eventID) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("email", participantID);
        content.put("event", eventID);

        try{
            this.readFromUrl(this.route + "add-participant-to-event", content, Request.Method.POST, context, new APICallback() {
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

    // To debate
    public void AddParticipant(final Context context, final String[] participantsID, final int eventID){
        throw new UnsupportedOperationException("API has no such operation yet.");
    }

    public void UpdateStatus(final Context context, final String participantID, final int eventID, final int status) throws JSONException {
        JSONObject updateNode = new JSONObject();
        JSONObject content = new JSONObject();

        content.put("accountEmail", participantID);
        content.put("eventId", eventID);
        content.put("status", status);

        try{
            this.readFromUrl(this.route + "update-participation-status", content, Request.Method.PUT, context, new APICallback() {
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

    public void RemoveParticipant(final Context context, final String participantID, final int eventID) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("email", participantID);
        content.put("event", eventID);

        try{
            this.readFromUrl(this.route + "remove-participant-from-event", content, Request.Method.DELETE, context, new APICallback() {
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

    // To debate
    public void RemoveParticipant(final Context context, final String[] participantsID, final int eventID){
        throw new UnsupportedOperationException("API has no such operation yet.");
    }

    public void GetParticipants(final Context context, final int eventID) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("event", eventID);

        try{
            this.readFromUrl(this.route + "get-participants-to-event", content, Request.Method.PUT, context, new APICallback() {
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

    public void GetParticipation(final Context context, final String participantID, final int eventID) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("email", participantID);
        content.put("event", eventID);

        try{
            this.readFromUrl(this.route + "get-my-participation-status-for-one-event", content, Request.Method.PUT, context, new APICallback() {
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

    public void GetParticipation(final Context context, final String participantID) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("email", participantID);

        try{
            this.readFromUrl(this.route + "get-my-participation-status-for-all-events", content, Request.Method.PUT, context, new APICallback() {
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
}
