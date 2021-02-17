package com.example.android.eventmap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventResult {
    @SerializedName("items")
    @Expose
    private List<Items> items = null;
}
