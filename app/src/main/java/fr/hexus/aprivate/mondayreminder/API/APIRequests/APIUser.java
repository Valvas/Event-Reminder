package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ContentHandler;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.Account;
import fr.hexus.aprivate.mondayreminder.HomePage;
import fr.hexus.aprivate.mondayreminder.LogonPage;
import fr.hexus.aprivate.mondayreminder.R;

/**
 * Created by Nicolas on 19/12/2017.
 */

public class APIUser extends APIRequester {

    private final String route = this.baseURL + "/accounts/";

    public void Create(final Context context, final String email, final String firstName, final String lastName) throws JSONException {
        JSONObject accountNode = new JSONObject();
        JSONObject contentNode = new JSONObject();

        contentNode.put("email", email);
        contentNode.put("lastname", firstName);
        contentNode.put("firstname", lastName);

        accountNode.put("account", contentNode);

        try {
            readFromUrl(route + "create-account", accountNode, "POST", context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject resultapi) {
                    // Instructions for what to do after the register
                    // EXEMPLE
                    try {
                        if(resultapi.getBoolean("result") == true){
                            ((LogonPage)context).finishAffinity();
                            Intent intent = new Intent(context, HomePage.class);
                            Account test = new Account(lastName, firstName, email);
                            intent.putExtra(context.getResources().getString(R.string.ACCOUNT), test);
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
                    ((LogonPage)context).showError("Error : " + error.getMessage());
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // I dunno what to write here, because I don't know what Google Login yield
    public void Login(final Context context, String... args) throws JSONException {
        JSONObject accountNode = new JSONObject();
        final JSONObject contentNode = new JSONObject();

        contentNode.put("email", args[0]);
        accountNode.put("account", contentNode);

        try {
            readFromUrl(route + "login-account", accountNode, "POST", context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject resultapi) {
                    // Instructions for what to do after the logged account
                    // EXEMPLE
                    try {
                        if(resultapi.getBoolean("result") == true){
                            ((LogonPage)context).finishAffinity();
                            Intent intent = new Intent(context, HomePage.class);
                            Account test = new Account("Lefebvre", "Olivier", "olivier.lefebvre@gmail.com");
                            intent.putExtra(context.getResources().getString(R.string.ACCOUNT), test);
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
                    ((LogonPage)context).showError("Error : " + error.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
