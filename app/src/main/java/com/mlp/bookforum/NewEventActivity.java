package com.mlp.bookforum;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class NewEventActivity extends AppCompatActivity {
    private static final String ADD_DATE= "com.mlp.bookforum.add_date";

    public static Events mNewEvent;
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
                FragmentManager manager = getSupportFragmentManager();
                SetEventDate calendar = new SetEventDate();
                calendar.show(manager, ADD_DATE);

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
                        .setNegativeButton("Not yet", new DialogInterface.OnClickListener() {
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

    public static class SetEventDate extends DialogFragment {
        private DatePicker mDatePicker;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            View v = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker, null);
            mDatePicker = (DatePicker) v.findViewById(R.id.calendar_date_picker);
            mDatePicker.init(year, month, day, null);

            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle("Set event date")
                    .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Calendar set_date = Calendar.getInstance();
                            set_date.set(Calendar.YEAR, mDatePicker.getYear());
                            set_date.set(Calendar.MONTH, mDatePicker.getMonth());
                            set_date.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
                            mNewEvent.setEventDate(set_date.getTime());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();
        }

    }

}
