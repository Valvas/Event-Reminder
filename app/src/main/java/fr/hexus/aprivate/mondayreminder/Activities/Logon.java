package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;

import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIUser;
import fr.hexus.aprivate.mondayreminder.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Logon extends Activity implements View.OnClickListener
{
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private SignInButton signInButton;
    private TextView status_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_page);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        /*signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        status_textView.findViewById(R.id.status_textView);*/

        // Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account == null){
            signInButton.setVisibility(View.VISIBLE);
            status_textView.setText("Signed out");
            return;
        }

        signInButton.setVisibility(View.INVISIBLE);
        status_textView.setText("Already signed in");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Log.e(TAG, "GOOGLE SIGN IN ACCOUNT");

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed
                Log.e(TAG, "Login error.\nException details:\n" + e);

                Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goToHomePage(FirebaseAuth firebaseAuth)
    {
        // Initialisation de l'addresse de base de l'API
        APIRequester.baseURL = getText(R.string.API_ADDRESS) + ":" + getResources().getInteger(R.integer.API_PORT);

        // I know it's bad, but, a singleton would be appropriate here ?
        APIUser UserEndAPI = new APIUser();

        try {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            String[] displayName = currentUser.getDisplayName().split(" ", 2);

            UserEndAPI.Login(this, currentUser.getEmail(), displayName[0], displayName[1], firebaseAuth);
        } catch (Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    public void showError(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + googleSignInAccount.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success
                    Log.d(TAG, "signInWithCredential:success");

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                    goToHomePage(firebaseAuth);
                } else {
                    // Sign in fails
                    Log.w(TAG, "signInWithCredential:failure", task.getException());

                    Toast.makeText(this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }
}
