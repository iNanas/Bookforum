package com.mlp.bookforum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class EventsListFragment extends Fragment{
    private RecyclerView mEventsRecyclerView;

    public static EventsListFragment newInstance(){
        return new EventsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events_recyclerview, container, false);
        mEventsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_recyclerView_list);
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    private class EventsViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mEventsLinearLayout;

        public EventsViewHolder(View itemView) {
            super(itemView);
            mEventsLinearLayout = (LinearLayout) itemView.findViewById(R.id.events_linearLayout);
        }
    }

    private class EventsAdapter extends RecyclerView.Adapter<EventsViewHolder> {

        @Override
        public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EventsViewHolder(LayoutInflater.from(getActivity())
                    .inflate(R.layout.fragment_events_linearlayout, parent, false));
        }

        @Override
        public void onBindViewHolder(EventsViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
