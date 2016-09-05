package com.mlp.bookforum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NewEventActivity extends AppCompatActivity {
    private static final String ADD_EVENT= "com.mlp.bookforum.add_event";

    private Button mSetDateButton;
    private Button mSetStartTimeButton;
    private Button mSetFinishTimeButton;
    private TextView mSetEventName;
    private TextView mSetEventLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        setTitle("Add new Event");

    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, NewEventActivity.class);
        return intent;
    }

}
