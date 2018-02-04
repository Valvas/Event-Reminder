package fr.hexus.aprivate.mondayreminder.API.Firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIUser;
import fr.hexus.aprivate.mondayreminder.GlobalVariables;

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
        try{
            new APIUser().sendNotificationToken(
                    this, GlobalVariables.CurrentAccount.getIdentifier(), token);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
