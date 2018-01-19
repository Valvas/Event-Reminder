package fr.hexus.aprivate.mondayreminder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fr.hexus.aprivate.mondayreminder.Activities.Menu;
import fr.hexus.aprivate.mondayreminder.R;

public class Friends extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_home);
    }

    public void openMenu(View view)
    {
        Intent intent = new Intent(this, Menu.class);

        intent.putExtra(getResources().getString(R.string.ACCOUNT), getIntent().getSerializableExtra(getResources().getString(R.string.ACCOUNT)));

        startActivity(intent);
    }
}
