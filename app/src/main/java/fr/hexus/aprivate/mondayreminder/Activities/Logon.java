package fr.hexus.aprivate.mondayreminder.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;

import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIUser;
import fr.hexus.aprivate.mondayreminder.API.Firebase.CustomFirebaseInstanceIdService;
import fr.hexus.aprivate.mondayreminder.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class Logon extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_page);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            // If "wantsToBeDisconnected" boolean is true, then signing out
            if(getIntent().getBooleanExtra("wantsToBeDisconnected", false)){
                signOut();
            }
        }

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Log.i(TAG, "GOOGLE SIGN IN ACCOUNT");

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed
                Log.e(TAG, "Login aborted.\nException details:\n" + e);

                Toast.makeText(this, "Login aborted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }

    /**
     * Starts connection with the API and constructs JSON account object
     * @param firebaseAuth Current Firebase authentication
     */
    public void goToHomePage(FirebaseAuth firebaseAuth)
    {
        // Initialisation de l'addresse de base de l'API
        APIRequester.baseURL = getText(R.string.API_ADDRESS) + ":" + getResources().getInteger(R.integer.API_PORT);

        APIUser UserEndAPI = new APIUser();

        try {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            String[] displayName;

            if(currentUser != null && currentUser.getDisplayName() != null){
                displayName = currentUser.getDisplayName().split(" ", 2);
                String firstName = displayName[0];
                String lastName = displayName.length > 1 ? displayName[1] : "";

                UserEndAPI.Login(this, currentUser.getEmail(), firstName, lastName);

                return;
            }

            Log.e("goToHomePage()", "The current Firebase user is null or invalid.");
        } catch (Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    /**
     * Shows a Toast error
     * @param text Text to display
     */
    public void showError(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Link between Google and Firebase in order to authenticate the account on the app
     * @param googleSignInAccount GoogleSignInAccount object with which to interact
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + googleSignInAccount.getId());

        //CustomFirebaseInstanceIdService instanceIdService = new CustomFirebaseInstanceIdService();
        //instanceIdService.onTokenRefresh();

        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {

                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithCredential:success");

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                    goToHomePage(firebaseAuth);
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());

                    Toast.makeText(this, "Login failure", Toast.LENGTH_SHORT).show();
                }

            });
    }

    /**
     * Starts the sign in process
     */
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Sign out from Firebase and from Google Sign In Client
     */
    private void signOut(){
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this, task -> {});

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this, task -> {});

    }
}
