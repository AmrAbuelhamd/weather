package com.blogspot.soyamr.weather;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MyDeserializer<T> implements JsonDeserializer<T> {
    String jsonObjName;

    public MyDeserializer(String jsonObjName) {
        this.jsonObjName = jsonObjName;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // Get the "location" element from the parsed JSON
        JsonElement content = json.getAsJsonObject().get(jsonObjName);

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, typeOfT);
    }
}
