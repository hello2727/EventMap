package com.example.android.eventmap.repository;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("body")
    @Expose
    private Body body;

    public Header getHeader(){
        return header;
    }

    public void setHeader(Header header){
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @NonNull
    @Override
    public String toString() {
        return "[body: " +
                body +
                "header: " +
                header +
                "]";
    }
}
