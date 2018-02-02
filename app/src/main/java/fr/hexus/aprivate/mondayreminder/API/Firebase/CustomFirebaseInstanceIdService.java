package fr.hexus.aprivate.mondayreminder.API.Firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIUser;

/**
 * Created by Olivier on 31/01/2018.
 */

public class CustomFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = CustomFirebaseInstanceIdService.class.getSimpleName();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String[] displayName;
        String firstName = null;
        String lastName = null;

        if(currentUser != null && currentUser.getDisplayName() != null){
            displayName = currentUser.getDisplayName().split(" ", 2);
            firstName = displayName[0];
            lastName = displayName.length > 1 ? displayName[1] : "";
        }

        APIUser apiUser = new APIUser();
        try {
            apiUser.refreshUserToken(this, currentUser.getEmail(), firstName, lastName, token);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
