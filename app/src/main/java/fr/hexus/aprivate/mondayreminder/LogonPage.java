package fr.hexus.aprivate.mondayreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LogonPage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_page);
    }

    public void goToHomePage(View view)
    {
        finishAffinity();

        Intent intent = new Intent(this, HomePage.class);

        Account test = new Account("Lefebvre", "Olivier", "olivier.lefebvre@gmail.com");

        intent.putExtra(getResources().getString(R.string.ACCOUNT), test);

        startActivity(intent);
    }
}
