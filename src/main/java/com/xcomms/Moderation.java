package com.xcomms;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

public class Moderation {
    
    public boolean isAppropriate(String text){
        String response = "";

        try {
            response = google("Does this text '" + text + "' contain any inappropriate, sensitive, or harmful content?");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(response);
        
        if(response.toLowerCase().split(" ")[0].contains("yes")){
            return false;
        }
        return true;
    }



    private final String API_KEY = "AIzaSyA5-AQAa2wcrzuk7dAK70VulqyxL5anBV4";
    private final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    private String google(String message) throws Exception{

        JSONObject json = new JSONObject();
    
        json.put("contents", new JSONArray().put(new JSONObject().put("parts", new JSONArray().put(new JSONObject().put("text", message)))));



        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(URL))
            .header("Content-Type", "application/json;charset=UTF-8")
            .POST(BodyPublishers.ofString(json.toString()))
            .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.body());
        JSONObject parsedResponse = new JSONObject(response.body());

        return parsedResponse.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
    }

    



}
