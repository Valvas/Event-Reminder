package fr.hexus.aprivate.mondayreminder.API;


import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Nicolas on 02/01/2018.
 */

public interface APICallback {
    // Handling the response from web server
    void onSuccessResponse(JSONObject result);

    // Handling of error request and action on the activity
    void onErrorResponse(VolleyError error);
}
