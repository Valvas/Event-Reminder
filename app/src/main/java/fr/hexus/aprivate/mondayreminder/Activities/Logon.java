package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIUser;
import fr.hexus.aprivate.mondayreminder.R;

public class Logon extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_page);
    }

    public void goToHomePage(View view)
    {
        // Initialisation de l'addresse de base de l'API
        APIRequester.baseURL = getText(R.string.API_ADDRESS) + ":" + getResources().getInteger(R.integer.API_PORT);

        // I know it's bad, but, a singleton would be appropriate here ?
        APIUser UserEndAPI = new APIUser();
        
        try {
            UserEndAPI.Login(this, "nicolas.demoncourt@gmail.com", "Nicolas", "Cornu");
        } catch (Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    public void showError(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
