package com.mlp.bookforum;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EventsListFragment extends Fragment{
    private RecyclerView mEventsRecyclerView;
    List<Events> forumEventsList;
    private TextView mEventName;
    private TextView mEventLocation;
    private TextView mEventDate;
    private TextView mEventStartTime;
    private TextView mEventFinishTime;

    public static EventsListFragment newInstance(){
        return new EventsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        forumEventsList = new ManageSharedPref().loadEvents(getActivity());
        //new AsyncTaskForEvents().execute();
        if(forumEventsList.size() == 0){
            Toast.makeText(getActivity(), R.string.toast_add_events, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events_recyclerview, container, false);
        mEventsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_recyclerView_list);
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setSwipeDeleter();
        setupAdapter(forumEventsList);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add_event:
                startActivity(NewEventActivity.newIntent(getActivity()));
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void setupAdapter(List<Events> eventsList) {
        if (isAdded()) {
            EventsAdapter adapter = new EventsAdapter(eventsList);
            mEventsRecyclerView.setItemViewCacheSize(adapter.getItemCount() + 5);
            mEventsRecyclerView.setAdapter(adapter);
        }
    }

    private void setSwipeDeleter(){
        ItemTouchHelper swipeToDelete = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final int adapterPosition = viewHolder.getAdapterPosition();
                        new AlertDialog.Builder(getActivity())
                                .setTitle(R.string.title_confirmation)
                                .setMessage(R.string.delete_text)
                                .setPositiveButton(R.string.yes_button_yep, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new ManageSharedPref().removeEvent(getActivity(), adapterPosition);
                                        forumEventsList = new ManageSharedPref().loadEvents(getActivity());
                                        setupAdapter(forumEventsList);
                                    }
                                })
                                .setNegativeButton(R.string.no_text, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        setupAdapter(forumEventsList);
                                    }
                                })
                                .show();
                    }
                });
        swipeToDelete.attachToRecyclerView(mEventsRecyclerView);
    }

    private class EventsViewHolder extends RecyclerView.ViewHolder{
        private Events mForumEvent;

        public EventsViewHolder(View itemView) {
            super(itemView);
            mEventName = (TextView) itemView.findViewById(R.id.forum_event_name);
            mEventLocation = (TextView) itemView.findViewById(R.id.forum_event_location);
            mEventDate = (TextView) itemView.findViewById(R.id.forum_event_date);
            mEventStartTime = (TextView) itemView.findViewById(R.id.forum_event_time_start);
            mEventFinishTime = (TextView) itemView.findViewById(R.id.forum_event_time_finish);
        }

        public void bindEvent(Events forumEvent){
            mForumEvent = forumEvent;
            mEventName.setText(mForumEvent.getEventName());
            mEventLocation.setText(mForumEvent.getEventLocation());
            if(mForumEvent.getEventDate() != null)
                mEventDate.setText(DateFormat.format("dd MMM", mForumEvent.getEventDate()));
            if(mForumEvent.getEventStartTime() != null)
                mEventStartTime.setText(DateFormat.format("HH:mm", mForumEvent.getEventStartTime()));
            if(mForumEvent.getEventFinishTime() != null)
                mEventFinishTime.setText(DateFormat.format("HH:mm", mForumEvent.getEventFinishTime()));
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

    private class AsyncTaskForEvents extends AsyncTask<String,Void,List<Events>> {
        private List<Events> mEventsFromAPI;

        @Override
        protected List<Events> doInBackground(String... str) {
            return new ManageJsonFetching().getJson();
        }

        @Override
        protected void onPostExecute(List<Events> events) {
            mEventsFromAPI = events;
            setupAdapter(mEventsFromAPI);
        }
    }
}
