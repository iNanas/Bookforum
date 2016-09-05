package com.mlp.bookforum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
        temp_events_test();
        //forumEventsList = new ManageSharedPref().loadEvents(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events_recyclerview, container, false);
        mEventsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_recyclerView_list);
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

    private void temp_events_test(){
        forumEventsList = new ArrayList<>();
        Events temp_event1 = new Events();
        Events temp_event2 = new Events();
        Events temp_event3 = new Events();
        Events temp_event4 = new Events();
        temp_event1.setEventName("Автограф-сесія Девіда Саттера (США).");
        temp_event2.setEventName("Автограф-сесія Володимира Лиса та Надії Гуменюк.");
        temp_event3.setEventName("Автограф сесія перекладачів Рея Бредбері – Ірини Бондаренко, Богдана Стасюка та Олени Кіфенко.");
        temp_event4.setEventName("Творчий вечір Василя Шкляра");
        temp_event1.setEventLocation("Палац мистецтв, хол 2 поверху");
        temp_event2.setEventLocation("Стенд №2, Палац Мистецтв, 1 поверх");
        temp_event3.setEventLocation("вул. Коперника, 17, Палац мистецтв, хол 2 поверху");
        temp_event4.setEventLocation("вул. Леся Курбаса, 3, Львівський академічний театр імені Леся Курбаса");
        forumEventsList.add(temp_event1);
        forumEventsList.add(temp_event2);
        forumEventsList.add(temp_event3);
        forumEventsList.add(temp_event4);
    }

    private void setupAdapter(List<Events> eventsList) {
        if (isAdded()) {
            mEventsRecyclerView.setAdapter(new EventsAdapter(eventsList));
        }
    }

    private class EventsViewHolder extends RecyclerView.ViewHolder {
        private Events mForumEvent;
        private TextView mEventName;
        private TextView mEventLocation;

        public EventsViewHolder(View itemView) {
            super(itemView);
            mEventName = (TextView) itemView.findViewById(R.id.forum_event_name);
            mEventLocation = (TextView) itemView.findViewById(R.id.forum_event_location);
        }

        public void bindEvent(Events forumEvent){
            mForumEvent = forumEvent;
            mEventName.setText(mForumEvent.getEventName());
            mEventLocation.setText(mForumEvent.getEventLocation());
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
