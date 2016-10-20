package com.example.roe.lab1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatWindow extends AppCompatActivity {
    ListView chatList;
    EditText chatText;
    Button sendBtn;
    ArrayList<String> array = new ArrayList<String>();
    protected static final String ACTIVITY_NAME = "ChatWindow";
    ChatAdapter messageAdapter;
    protected SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatList = (ListView)findViewById(R.id.listview);
        chatText = (EditText)findViewById(R.id.chatText);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        messageAdapter = new ChatAdapter( this );
        chatList.setAdapter (messageAdapter);
        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper( this );
        db = dbHelper.getWritableDatabase();
        Cursor results1 = db.rawQuery("Select * from CHATS", null);
        results1.moveToFirst();


        while (!results1.isAfterLast()) {
            array.add(results1.getString( results1.getColumnIndex( "MESSAGE") ));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:”" + results1.getString( results1.getColumnIndex( "MESSAGE") ) );
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + results1.getColumnCount() );

            for(int i =0; i < results1.getColumnCount(); i++){
                results1.getColumnName(i);
            }

            results1.moveToNext(); //move the cursor to the next row
        }


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                array.add(chatText.getText().toString());
                ContentValues newValues = new ContentValues();
                newValues.put("MESSAGE", chatText.getText().toString());
                db.insert(ChatDatabaseHelper.CHAT_TABLE_NAME, null, newValues);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView()
                chatText.setText("");
            }
        });





    }




    private class ChatAdapter extends ArrayAdapter<String> {


        public ChatAdapter(Context ctx) {

            super(ctx, 0);
        }

        public int getCount() {
            return array.size();
        }

        public String getItem(int position){
            return array.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }
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
        db.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }






}
