package com.apollo.docker;

import com.google.common.base.Preconditions;
import org.json.JSONObject;

public class Image {
    private final JSONObject json;
    public final String id;

    public Image(String jsonString) {
        Preconditions.checkNotNull(jsonString, "jsonString can't be null!");
        this.json = new JSONObject(jsonString);
        this.id = json.getString("Id");
        // TODO: more
    }
}
