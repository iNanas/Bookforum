package com.mlp.bookforum;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Events implements Parcelable {
    private String mEventName;
    private String mEventLocation;
    private Date mEventDate;

    public Events() {
    }

    public Date getEventDate() {
        return mEventDate;
    }

    public void setEventDate(Date eventDate) {
        mEventDate = eventDate;
    }

    public String getEventLocation() {
        return mEventLocation;
    }

    public void setEventLocation(String eventLocation) {
        mEventLocation = eventLocation;
    }

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String eventName) {
        mEventName = eventName;
    }

    protected Events(Parcel in) {
        mEventName = in.readString();
        mEventLocation = in.readString();
        long tmpMEventDate = in.readLong();
        mEventDate = tmpMEventDate != -1 ? new Date(tmpMEventDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEventName);
        dest.writeString(mEventLocation);
        dest.writeLong(mEventDate != null ? mEventDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Events> CREATOR = new Parcelable.Creator<Events>() {
        @Override
        public Events createFromParcel(Parcel in) {
            return new Events(in);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };
}