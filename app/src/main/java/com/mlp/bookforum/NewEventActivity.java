package com.mlp.bookforum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewEventActivity extends AppCompatActivity {
    private static final String ADD_EVENT= "com.mlp.bookforum.add_event";

    private Events mNewEvent;
    private Button mSetDateButton;
    private Button mSetStartTimeButton;
    private Button mSetFinishTimeButton;
    private Button mAddEvent;
    private EditText mSetEventName;
    private EditText mSetEventLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        setTitle("Add new Event");
        mNewEvent = new Events();

        mSetEventName = (EditText) findViewById(R.id.set_event_name);
        mSetEventName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewEvent.setEventName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSetEventLocation = (EditText) findViewById(R.id.set_event_location);
        mSetEventLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewEvent.setEventLocation(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSetDateButton = (Button) findViewById(R.id.set_date_button);
        mSetDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setDate
            }
        });

        /*
        mSetStartTimeButton = (Button) findViewById(R.id.set_start_time_button);
        mSetStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mSetFinishTimeButton = (Button) findViewById(R.id.set_finish_time_button);
        mSetFinishTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        */

        mAddEvent = (Button) findViewById(R.id.add_event_button);
        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(NewEventActivity.this).setTitle("Confirmation")
                        .setMessage("Add this event to list?")
                        .setPositiveButton("Yep!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new ManageSharedPref().addEvent(NewEventActivity.this, mNewEvent);
                            }
                        })
                        .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });


    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, NewEventActivity.class);
        return intent;
    }

}
