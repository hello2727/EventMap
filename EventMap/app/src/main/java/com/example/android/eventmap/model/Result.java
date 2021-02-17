package com.example.android.eventmap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("items")
    @Expose
    private EventResult eventResult;

    public EventResult getEventResult() {
        return eventResult;
    }

    public void setEventResult(EventResult eventResult){
        this.eventResult = eventResult;
    }
}
