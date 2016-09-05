package com.mlp.bookforum;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageSharedPref {
    private static final String SH_PREF = "my_pref";
    private static final String EVENTS_JSON = "events_json";


    public void storeEvents(Context context, List events_list) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonEvents = gson.toJson(events_list);
        editor.putString(EVENTS_JSON, jsonEvents);
        editor.commit();
    }
    public ArrayList<Events> loadEvents(Context context) {
        SharedPreferences settings;
        List<Events> forumEvents = new ArrayList<>();
        settings = context.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE);
        if (settings.contains(EVENTS_JSON)) {
            String jsonEvents = settings.getString(EVENTS_JSON, null);
            Gson gson = new Gson();
            Events[] arrayEvents = gson.fromJson(jsonEvents,Events[].class);
            forumEvents = Arrays.asList(arrayEvents);
            forumEvents = new ArrayList<>(forumEvents);
        } else{
            return (ArrayList<Events>) forumEvents;
        }
        return (ArrayList<Events>) forumEvents;
    }
    public void addEvent(Context context, Events newEvent) {
        List<Events> forumEvents = loadEvents(context);
        if (forumEvents == null)
            forumEvents = new ArrayList<>();
        forumEvents.add(newEvent);
        storeEvents(context, forumEvents);
    }
    /*
    public void removeEvent(Context context, Events receivedEvent) {
        ArrayList<Events> savedEvents = loadEvents(context);
        if (savedEvents != null) {
            for(int i = 0; i < savedEvents.size(); i++){
                if(savedEvents.get(i).getSomeIdParam().equals(receivedEvent.getSomeIdParam())){
                    savedEvents.remove(savedEvents.get(i));
                    storeEvents(context, savedEvents);
                    break;
                }

            }
        }
    }
    */

}
