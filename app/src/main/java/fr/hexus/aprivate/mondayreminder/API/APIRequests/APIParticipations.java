package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Activities.CustomAdapter.ParticipantAdapter;
import fr.hexus.aprivate.mondayreminder.Activities.EventDetails;
import fr.hexus.aprivate.mondayreminder.Activities.Participants;
import fr.hexus.aprivate.mondayreminder.Contracts.Event;
import fr.hexus.aprivate.mondayreminder.Contracts.LiteAccount;
import fr.hexus.aprivate.mondayreminder.Contracts.Participation;
import fr.hexus.aprivate.mondayreminder.Enums.ParticipatingStatus;
import fr.hexus.aprivate.mondayreminder.R;

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

    public void UpdateStatus(final Context context, final String participantID, final int eventID, final int status) {
        try {
            JSONObject updateNode = new JSONObject();
            JSONObject content = new JSONObject();

            content.put("accountEmail", participantID);
            content.put("eventId", eventID);
            content.put("status", status);
            updateNode.put("update", content);

            this.readFromUrl(this.route + "update-participation-status", content, Request.Method.PUT, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        if(result.getBoolean("result")){
                            ((EventDetails)context).UpdateParticipatingStatusUI(status);
                            Toast.makeText(context, context.getResources().getText(R.string.success_updating_participation_status), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    ((EventDetails)context).UpdateParticipatingStatusUI(ParticipatingStatus.WAIT.getValue());
                    Toast.makeText(context, context.getResources().getText(R.string.error_updating_participation_status), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception exp){

        }
    }

    public void RemoveParticipant(final Context context, final String participantID, final int eventID) {
        try{
            JSONObject content = new JSONObject();

            content.put("email", participantID);
            content.put("event", eventID);

            this.readFromUrl(this.route + "remove-participant-from-event", content, Request.Method.DELETE, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        if(result.getBoolean("result")){
                            new APIParticipations().GetParticipants(context, eventID);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                }
            });
        } catch (Exception exp){
            exp.printStackTrace();
        }
    }

    public void GetParticipants(final Context context, final int eventID) {
        try {
            JSONObject content = new JSONObject();

            content.put("event", eventID);

            this.readFromUrl(this.route + "get-participants-to-event", content, Request.Method.PUT, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        List<Participation> participantsList = new ArrayList<>();
                        if(result.getBoolean("result")){
                            JSONObject participants = result.getJSONObject("participants");
                            Iterator<?> keys = participants.keys();

                            while(keys.hasNext()){
                                JSONObject participant = participants.getJSONObject((String)keys.next());

                                Event event = APIEvents.GetEventFromCache(participant.getInt("id"));

                                String email = participant.getString("email");
                                String lastName = participant.getString("lastname");
                                String firstName = participant.getString("firstname");
                                LiteAccount account = new LiteAccount(lastName, firstName, email);

                                ParticipatingStatus status = ParticipatingStatus.getStatus(participant.getInt("status"));

                                Participation participation = new Participation(account, event, status);

                                participantsList.add(participation);
                            }
                        }
                        ParticipantAdapter participantAdapter = new ParticipantAdapter(context, participantsList);
                        ((Participants)context).setListAdapter(participantAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                }
            });
        } catch (Exception exp){

        }
    }

    public void GetParticipation(final Context context, final String participantID, final int eventID) {
        try {
            JSONObject content = new JSONObject();

            content.put("email", participantID);
            content.put("event", eventID);

            this.readFromUrl(this.route + "get-my-participation-status-for-one-event", content, Request.Method.PUT, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    // Handle response from server
                    try {
                        if(result.getBoolean("result")){
                            ((EventDetails)context).UpdateParticipatingStatusUI(result.getInt("participation"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                }
            });
        } catch (Exception exp){

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
