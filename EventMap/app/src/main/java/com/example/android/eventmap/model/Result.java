package com.example.android.eventmap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("response_j")
    @Expose
    private Response_J response_j;

    public Response_J getResponse_j() {
        return response_j;
    }

    public void setResponse_j(Response_J response_j) {
        this.response_j = response_j;
    }
}
