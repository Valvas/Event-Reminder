package fr.hexus.aprivate.mondayreminder.API;


import org.json.JSONObject;

/**
 * Created by Nicolas on 02/01/2018.
 */

public interface APICallback {
    void onSuccessResponse(JSONObject result);

    //void onErrorResponse(VolleyError error);
    // Handling of error request and action on the activity

    // Can have more stuffs in the future
}
