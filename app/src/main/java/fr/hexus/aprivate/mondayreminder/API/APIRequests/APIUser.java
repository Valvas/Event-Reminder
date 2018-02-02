package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Contracts.Account;
import fr.hexus.aprivate.mondayreminder.Activities.Home;
import fr.hexus.aprivate.mondayreminder.Activities.Logon;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;

/**
 * Created by Nicolas on 19/12/2017.
 */

public class APIUser extends APIRequester {

    private final String route = baseURL + "/public/accounts/";

    /**
     * Stores the connected Google Account in the application and logs in the user
     * @param context Context
     * @param email Email of the currently connected user
     * @param firstName Firstname of the currently connected user
     * @param lastName Lastname of the currently connected user
     * @throws JSONException JSONException describing the anomaly within the JSON object sent
     */
    public void Login(final Context context, final String email, final String firstName, final String lastName) throws JSONException {
        JSONObject accountNode = new JSONObject();
        JSONObject contentNode = new JSONObject();

        contentNode.put("email", email);
        contentNode.put("lastname", lastName);
        contentNode.put("firstname", firstName);
        contentNode.put("firebase_token", FirebaseInstanceId.getInstance().getToken());

        accountNode.put("account", contentNode);

        try {
            readFromUrl(route + "create-account", accountNode, Request.Method.POST, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        if(result.getBoolean("result")){
                            ((Logon)context).finishAffinity();
                            Intent intent = new Intent(context, Home.class);
                            GlobalVariables.CurrentAccount = new Account(lastName, firstName, email, result.getString("token"));
                            context.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle the error with a Toast or MessageBox to show on UI
                    Log.e(APIUser.class.getName(), "Callback Error :\nMessage : " + error.getMessage());
                    ((Logon)context).showError("Error: " + error.getMessage());

                    if(error.networkResponse == null) return;

                    String response = new String(error.networkResponse.data);
                    Log.i("response", response);
                }
            });
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * WIP: Sends a refreshed token to the specified account in the database
     * @param context Context
     * @param email Email of the currently connected user
     * @param firstName Firstname of the currently connected user
     * @param lastName Lastname of the currently connected user
     * @throws JSONException JSONException describing the anomaly within the JSON object sent
     */
    public void refreshUserToken(final Context context, final String email, final String firstName, final String lastName, final String token) throws JSONException{
        JSONObject contentNode = new JSONObject();

        contentNode.put("email", email);
        contentNode.put("firstname", firstName);
        contentNode.put("lastname", lastName);
        contentNode.put("token", token);
    }

    /**
     * WIP: Removes the disconnected user' token from the database
     * @param context Context
     * @param email Email of the currently connected user
     * @throws JSONException JSONException describing the anomaly within the JSON object sent
     */
    public void removeUserToken(final Context context, final String email) throws JSONException{
        JSONObject contentNode = new JSONObject();

        contentNode.put("email", email);
        contentNode.put("token", FirebaseInstanceId.getInstance().getToken());
    }
}
