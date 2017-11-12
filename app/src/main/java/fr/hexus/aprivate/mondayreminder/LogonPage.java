package fr.hexus.aprivate.mondayreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        Account test = new Account("Doe", "John", "12345678");

        startActivity(new Intent(this, HomePage.class));
    }
}
