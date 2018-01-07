package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;

/**
 * Created by Nicolas on 07/01/2018.
 */

public class APIFriends extends APIRequester {

    private final String route = this.baseURL + "/friends/";

    public void Add(final Context context, final String requesterEmail, final String receiverEmail) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("ownerEmail", requesterEmail);
        content.put("friendEmail", receiverEmail);

        try{
            this.readFromUrl(this.route + "add-friend", content, "POST", context, new APICallback() {
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

    public void Delete(final Context context, final String requesterEmail, final String receiverEmail) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("ownerEmail", requesterEmail);
        content.put("friendEmail", receiverEmail);

        try{
            this.readFromUrl(this.route + "delete-friend", content, "DELETE", context, new APICallback() {
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

    public void Update(final Context context, final String requesterEmail, final String receiverEmail, final String status /* Deciding type */) throws JSONException {
        JSONObject content = new JSONObject();

        content.put("ownerEmail", requesterEmail);
        content.put("friendEmail", receiverEmail);
        content.put("status", status);

        try{
            this.readFromUrl(this.route + "update-friend-status", content, "PUT", context, new APICallback() {
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
