package fr.hexus.aprivate.mondayreminder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fr.hexus.aprivate.mondayreminder.GlobalVariables;
import fr.hexus.aprivate.mondayreminder.R;

public class Home extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        welcomeMessage.setText(welcomeMessage.getText() + " " + GlobalVariables.CurrentAccount.getFirstName());
    }

    public void openMenu(View view)
    {
        Intent intent = new Intent(this, Menu.class);

        startActivity(intent);
    }
}
