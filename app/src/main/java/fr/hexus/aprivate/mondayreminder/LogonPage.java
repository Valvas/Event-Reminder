package fr.hexus.aprivate.mondayreminder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fr.hexus.aprivate.mondayreminder.API.APIRequester;
import fr.hexus.aprivate.mondayreminder.API.APIRequests.APIUser;

public class LogonPage extends Activity
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

        APIUser testAPI = new APIUser();

        try {
            testAPI.CreateAccount(this, "nicolas.demoncourt@gmail.com", "Nicolas", "Cornu");
        } catch (Exception exp){
            System.out.println(exp.getMessage());
        }

        // Things below should be erased in the future.

        finishAffinity();

        Intent intent = new Intent(this, HomePage.class);

        Account test = new Account("Lefebvre", "Olivier", "olivier.lefebvre@gmail.com");

        intent.putExtra(getResources().getString(R.string.ACCOUNT), test);

        startActivity(intent);
    }

    public void setText(String text){
        TextView textView = (TextView) findViewById(R.id.appTitle);
        textView.setText(text);
    }
}
