package com.company;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FixerData {
    public static void call_me() throws Exception {
        String access_key = "843336df7a0e10eefde3d05d342154a2";
        String request = "http://data.fixer.io/api/latest?access_key=" + access_key;
        URL obj = new URL(request);
        HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(request.length());
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();
        JSONObject myResponse = new JSONObject(response);
        System.out.println("result after Reading JSON Response");
        System.out.println("success- "+myResponse.getBoolean("success"));
        System.out.println("base- "+myResponse.getString("base"));
        Map myMap = myResponse.getJSONObject("rates").toMap();
        HashMap<String,String> myHashMap = new HashMap<String, String>(myMap);
        System.out.println("///////");
        System.out.println(myHashMap.toString());
    }
}
