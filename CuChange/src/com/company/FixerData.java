package com.company;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class FixerData {

    final static String access_key = "843336df7a0e10eefde3d05d342154a2";
    public static Database getCurrency() throws Exception {
        boolean success;
        String date;
        String request = "http://data.fixer.io/api/latest?access_key=" + access_key;
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(request.length());
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) > -1;) {
            sb.append((char) c);
        }
        String response = sb.toString();

        JSONObject myResponse = new JSONObject(response);
        success = myResponse.getBoolean("success");
        Map<String, String> mapRates = new HashMap(myResponse.getJSONObject("rates").toMap());
        ArrayList CurrencyList = new ArrayList<Currency>();
        for (Map.Entry temp :mapRates.entrySet()) {
            CurrencyList.add(new Currency(temp.getKey(),temp.getValue()));
        }
        Collections.sort(CurrencyList);
        System.out.println(CurrencyList);
        date = "";
        return new Database(success,date,CurrencyList); //date - дата на компьютере до секунд
    }
}