package com.example.roe.lab1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

public class StartActivity extends AppCompatActivity {


    protected static final String ACTIVITY_NAME = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public void toolbarClick(View v) {
        Intent intent = new Intent(StartActivity.this, TestToolbar.class);
        startActivity(intent);
        Log.i(ACTIVITY_NAME, "User clicked test toolbar");
    }

    public void chatClick(View v) {
        Intent intent = new Intent(StartActivity.this, MessageListActivity.class);
        startActivity(intent);
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
    }

    public void startBtnClick(View v) {
        Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
        startActivityForResult(intent,5);
    }

    public void weatherClick(View v) {
        Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
        startActivity(intent);
        Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
    }



    public void onActivityResult(int requestCode, int responseCode, Intent data){
        String messagePassed = data.getStringExtra("Response");
        if(responseCode == Activity.RESULT_OK){
            Toast toast;
            String text;
            int duration;

            text = getString(R.string.toast_message) + " " +messagePassed;
            duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
            toast = Toast.makeText(this, text, duration);
            toast.show(); //display your message box

        }
        if(requestCode == 5){

            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
    }



}
