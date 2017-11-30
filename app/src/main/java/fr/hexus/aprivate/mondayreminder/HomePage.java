package fr.hexus.aprivate.mondayreminder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

public class HomePage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Account account = (Account) getIntent().getSerializableExtra(Constants.ACCOUNT);

        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);

        welcomeMessage.setText(welcomeMessage.getText() + " " + account.getAccountFirstname());
    }

    public void openMenu(View view)
    {
        Intent intent = new Intent(this, Menu.class);

        intent.putExtra(Constants.ACCOUNT, getIntent().getSerializableExtra(Constants.ACCOUNT));

        startActivity(intent);
    }
}
