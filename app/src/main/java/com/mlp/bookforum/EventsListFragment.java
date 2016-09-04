package com.mlp.bookforum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventsListFragment extends Fragment{
    private RecyclerView mEventsRecyclerView;
    List<Events> forumEventsList;

    public static EventsListFragment newInstance(){
        return new EventsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        temp_events_test();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events_recyclerview, container, false);
        mEventsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_recyclerView_list);
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter(forumEventsList);

        return v;
    }

    private void temp_events_test(){
        forumEventsList = new ArrayList<>();
        Events temp_event1 = new Events();
        Events temp_event2 = new Events();
        temp_event1.setEventName("My Awesome Event");
        temp_event2.setEventName("My Even More Awesome Event");
        forumEventsList.add(temp_event1);
        forumEventsList.add(temp_event2);
    }

    private void setupAdapter(List<Events> eventsList) {
        if (isAdded()) {
            mEventsRecyclerView.setAdapter(new EventsAdapter(eventsList));
        }
    }

    private class EventsViewHolder extends RecyclerView.ViewHolder {
        private Events mForumEvent;
        private TextView mEventName;

        public EventsViewHolder(View itemView) {
            super(itemView);
            mEventName = (TextView) itemView.findViewById(R.id.forum_event_name);
        }

        public void bindEvent(Events forumEvent){
            mForumEvent = forumEvent;
            mEventName.setText(mForumEvent.getEventName());
        }
    }

    private class EventsAdapter extends RecyclerView.Adapter<EventsViewHolder> {
        private List<Events> mEventsList;

        public EventsAdapter(List<Events> eventsList) {
            mEventsList = eventsList;
        }

        @Override
        public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EventsViewHolder(LayoutInflater.from(getActivity())
                    .inflate(R.layout.fragment_events_linearlayout, parent, false));
        }

        @Override
        public void onBindViewHolder(EventsViewHolder holder, int position) {
            holder.bindEvent(mEventsList.get(position));
        }

        @Override
        public int getItemCount() {
            return mEventsList.size();
        }
    }
}
