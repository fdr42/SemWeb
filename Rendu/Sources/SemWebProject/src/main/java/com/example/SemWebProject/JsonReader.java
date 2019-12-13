package com.example.SemWebProject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return null;
    }

    public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return null;
    }

    public static void main(String[] args) throws IOException, JSONException, org.json.JSONException {
        JSONObject json = readJsonFromUrl("https://swapi.co/api/people/1/");
        System.out.println(json.toString());
        System.out.println(json.get("id"));
    }
}
