package fr.hexus.aprivate.mondayreminder.API.APIRequests;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ContentHandler;

import fr.hexus.aprivate.mondayreminder.API.APICallback;
import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.LogonPage;

/**
 * Created by Nicolas on 19/12/2017.
 */

public class APIUser extends APIRequester {

    private String route = this.baseURL + "/accounts/create-account";

    public void CreateAccount(final Context context, String... args) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        jsonObject1.put("email", args[0]);
        jsonObject1.put("lastname", args[1]);
        jsonObject1.put("firstname", args[2]);

        jsonObject.put("account", jsonObject1);

        try {
            readFromUrl(route, jsonObject, "POST", context, new APICallback() {
                @Override
                public void onSuccessResponse(JSONObject resultapi) {
                    // EXPERIMENTAL
                    ((LogonPage)context).setText(resultapi.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
