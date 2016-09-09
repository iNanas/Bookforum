package com.mlp.bookforum;

import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManageJsonFetching {
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Events> getJson(){
        List<Events> eventsList = new ArrayList<>();

        try {
            String url = Uri.parse("http://bookforum.ua/wp-json/wp/v2/events/?per_page=15")
                    .buildUpon()
                    .build().toString();
            String jsonString = getUrlString(url);

            JSONArray jsonMain = new JSONArray(jsonString);
            parseJson(eventsList, jsonMain);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return eventsList;
    }

    private void parseJson(List<Events> eventsList, JSONArray jsonMainArray) throws JSONException {

        for (int i = 0; i < jsonMainArray.length(); i++){
            JSONObject event_object = jsonMainArray.getJSONObject(i);
            JSONObject title_object = event_object.getJSONObject("title");
            Events newEvent = new Events();
            newEvent.setEventName(title_object.getString("rendered"));

            eventsList.add(newEvent);
        }

    }


}
