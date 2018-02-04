package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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

    private final String publicRoute = baseURL + "/public/accounts/";
    private final String route = baseURL + "/accounts/";

    /**
     * Stores the connected Google Account in the application and logs in the user
     * @param context Context
     * @param email Email of the currently connected user
     * @param firstName Firstname of the currently connected user
     * @param lastName Lastname of the currently connected user
     * @throws JSONException JSONException describing the anomaly within the JSON object sent
     */
    public void Login(final Context context, final String email, final String firstName,
                      final String lastName) throws Exception {

        JSONObject accountNode = new JSONObject();
        JSONObject contentNode = new JSONObject();

        contentNode.put("email", email);
        contentNode.put("lastname", lastName);
        contentNode.put("firstname", firstName);
        contentNode.put("firebase_token", FirebaseInstanceId.getInstance().getToken());

        accountNode.put("account", contentNode);

        try {
            readFromUrl(publicRoute + "create-account", accountNode, Request.Method.POST, context,
                    new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        if(result.getBoolean("result")){
                            ((Logon)context).finishAffinity();
                            Intent intent = new Intent(context, Home.class);

                            GlobalVariables.CurrentAccount = new Account(lastName, firstName, email,
                                    result.getString("token"));

                            ((Logon)context).makeToast("Connexion réussie");
                            context.startActivity(intent);
                        }
                    } catch (Exception e) {
                        Log.e("onSuccessResponse:Failure", e.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Connexion échouée", Toast.LENGTH_SHORT).show();
                    Log.e(APIUser.class.getName(), "Callback Error :\nMessage : " +
                            error.getMessage());

                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Sends the token of the specified account to the database
     * @param context Context
     * @param email Email of the currently connected user
     * @param token Refreshed token to be sent
     * @throws JSONException JSONException describing the anomaly within the JSON object sent
     */
    public void sendNotificationToken(final Context context, final String email,
                                      final String token, final boolean refreshToken) throws  Exception{

        JSONObject contentNode = new JSONObject();

        contentNode.put("email", email);
        contentNode.put("token", token);

        try {
            readFromUrl(route + "add-notification-token", contentNode, Request.Method.POST,
                    context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        if(result.getBoolean("result")){
                            Log.d("sendNotificationToken", "Token sent: " + token);

                            if(refreshToken){
                                String lastName = GlobalVariables.CurrentAccount.getLastName();
                                String firstName = GlobalVariables.CurrentAccount.getFirstName();

                                GlobalVariables.CurrentAccount =
                                        new Account(lastName, firstName, email, token);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("onSuccessResponse:Failure", e.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse == null) return;

                    String errorData = new String(error.networkResponse.data);
                    Log.e("response", errorData);
                }
            });
        } catch (Exception e) {
            throw new  Exception(e.getMessage());
        }
    }
}
