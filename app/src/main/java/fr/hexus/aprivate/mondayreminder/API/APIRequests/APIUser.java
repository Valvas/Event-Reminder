package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Contracts.Account;
import fr.hexus.aprivate.mondayreminder.Activities.Home;
import fr.hexus.aprivate.mondayreminder.Activities.Logon;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

/**
 * Created by Nicolas on 19/12/2017.
 */

public class APIUser extends APIRequester {

    private final String route = this.baseURL + "/public/accounts/";

    public void Login(final Context context, final String email, final String firstName, final String lastName) throws JSONException {
        JSONObject accountNode = new JSONObject();
        JSONObject contentNode = new JSONObject();

        contentNode.put("email", email);
        contentNode.put("lastname", firstName);
        contentNode.put("firstname", lastName);

        accountNode.put("account", contentNode);

        try {
            readFromUrl(route + "create-account", accountNode, Request.Method.POST, context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject resultapi) {
                    // Instructions for what to do after the register
                    // EXEMPLE
                    try {
                        if(resultapi.getBoolean("result") == true){
                            ((Logon)context).finishAffinity();
                            Intent intent = new Intent(context, Home.class);
                            GlobalVariables.CurrentAccount = new Account(lastName, firstName, email, null, resultapi.getString("token"));
                            context.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle the error with a Toast or MessageBox to show on UI
                    Log.i(APIUser.class.getName(), "Callback Error :\nMessage : " + error.getMessage());
                    ((Logon)context).showError("Error : " + error.getMessage());
                    String test = new String(error.networkResponse.data).toString();
                    Log.i("response", test);
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
