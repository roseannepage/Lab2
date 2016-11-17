package com.example.roe.lab1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    String snackbarmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hey there!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        snackbarmessage = "You selected item 1";
    }

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem mi){
        switch(mi.getItemId()){
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(findViewById(android.R.id.content), snackbarmessage, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.dialog_title);
// Add the buttons
                builder.setPositiveButton(R.string.titledialogyes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       finish();
                    }
                });
                builder.setNegativeButton(R.string.titledialogno, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                onCreateDialog();
                break;
            case R.id.action_four:
                Toast toast;
                String text;
                int duration;

                text = "Version 1.0, by Roseanne Page";
                duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
                toast = Toast.makeText(this, text, duration);
                toast.show(); //display your message box
                break;
        }
        return true;
    }


    public void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogview = inflater.inflate(R.layout.dialogcustom, null);

        builder.setView(dialogview);
        final EditText edt = (EditText)dialogview.findViewById(R.id.customdialog);
                // Add action buttons
                builder.setPositiveButton(R.string.titledialogyes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       snackbarmessage = edt.getText().toString();
                    }
                });
                builder.setNegativeButton(R.string.titledialogno, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
       AlertDialog b = builder.create();
        b.show();
    }

}
