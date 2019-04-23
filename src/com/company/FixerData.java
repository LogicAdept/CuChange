package com.company;

import com.company.Currency;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FixerData {
    private final static String access_key = "843336df7a0e10eefde3d05d342154a2";

    public static ArrayList<Currency> getCurrency() throws IOException { //Метод для получения курсов валют с сайта FIXER.IO
        //Создаю запрос
        String request = "http://data.fixer.io/api/latest?access_key=" + access_key;
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setConnectTimeout(7000);
        conn.getOutputStream().write(request.length());

        //получаю данные

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) > -1; ) {
            sb.append((char) c);
        }
        //PARSING
        String response = sb.toString();
        JSONObject myResponse = new JSONObject(response);
        ArrayList<Currency> CurrencyList = new ArrayList<Currency>();
        boolean success = myResponse.getBoolean("success");
        if (success) {
            Map<String, String> mapRates = new HashMap(myResponse.getJSONObject("rates").toMap());
            for (Map.Entry temp : mapRates.entrySet()) {
                CurrencyList.add(new Currency(temp.getKey(), temp.getValue()));
            }
            Collections.sort(CurrencyList);
        }
        //END PARSING
        return CurrencyList;
    }
}