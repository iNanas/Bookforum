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
import android.widget.TimePicker;

import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity {
    private static final String ADD_DATE = "com.mlp.bookforum.add_date";
    private static final String ADD_START_TIME = "com.mlp.bookforum.add_start_time";
    private static final String ADD_FINISH_TIME = "com.mlp.bookforum.add_finish_time";

    public static Events mNewEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        setTitle(R.string.title_add_new_event);
        mNewEvent = new Events();

        Button mSetDateButton;
        Button mSetStartTimeButton;
        Button mSetFinishTimeButton;
        Button mAddEvent;
        EditText mSetEventName;
        EditText mSetEventLocation;

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
                setDateForOnClick(ADD_DATE);
            }
        });

        mSetStartTimeButton = (Button) findViewById(R.id.set_start_time_button);
        mSetStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateForOnClick(ADD_START_TIME);
            }
        });

        mSetFinishTimeButton = (Button) findViewById(R.id.set_finish_time_button);
        mSetFinishTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateForOnClick(ADD_FINISH_TIME);
            }
        });

        mAddEvent = (Button) findViewById(R.id.add_event_button);
        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(NewEventActivity.this).setTitle(R.string.title_confirmation)
                        .setMessage(R.string.alert_message_add)
                        .setPositiveButton(R.string.yes_button_yep, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new ManageSharedPref().addEvent(NewEventActivity.this, mNewEvent);
                            }
                        })
                        .setNegativeButton(R.string.no_button_notyet, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        });
    }

    private void setDateForOnClick(String TAG){
        FragmentManager manager = getSupportFragmentManager();
        SetEventDate calendar = new SetEventDate();
        calendar.show(manager, TAG);
    }

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext, NewEventActivity.class);
    }

    public static class SetEventDate extends DialogFragment {
        private DatePicker mDatePicker;
        private TimePicker mTimePicker;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            switch (getTag()){
                case ADD_DATE:
                    return getDateDialog();
                case ADD_START_TIME:
                    return getTimeDialog(R.string.set_event_start_time);
                case ADD_FINISH_TIME:
                    return getTimeDialog(R.string.set_event_finish_time);
                default:
                    return new AlertDialog.Builder(getActivity()).create();
            }

        }

        private Dialog getDateDialog() {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker, null);
            mDatePicker = (DatePicker) v.findViewById(R.id.calendar_date_picker);

            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.set_event_date)
                    .setPositiveButton(R.string.yes_button_set, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Calendar setDate = Calendar.getInstance();
                            setDate.set(Calendar.YEAR, mDatePicker.getYear());
                            setDate.set(Calendar.MONTH, mDatePicker.getMonth());
                            setDate.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
                            mNewEvent.setEventDate(setDate.getTime());
                        }
                    })
                    .setNegativeButton(R.string.no_button_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();
        }

        private Dialog getTimeDialog(int time){
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.time_picker, null);
            mTimePicker = (TimePicker) v.findViewById(R.id.time_picker);
            mTimePicker.setIs24HourView(true);

            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(time)
                    .setPositiveButton(R.string.yes_button_set, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Calendar setTime = Calendar.getInstance();
                            setTime.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
                            setTime.set(Calendar.MINUTE, mTimePicker.getCurrentMinute());
                            switch (getTag()){
                                case ADD_START_TIME:
                                    mNewEvent.setEventStartTime(setTime.getTime()); break;
                                case ADD_FINISH_TIME:
                                    mNewEvent.setEventFinishTime(setTime.getTime()); break;
                            }
                        }
                    })
                    .setNegativeButton(R.string.no_button_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();
        }


    }

}
