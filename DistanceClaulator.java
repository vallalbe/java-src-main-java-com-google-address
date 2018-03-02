package com.google.address;

import com.google.common.base.CharMatcher;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DistanceClaulator {
    public static void main(String[] args) throws ParseException {
        Map result = getdistance("13.056073285859021,76.6916617497307", "12.311515420467039,75.73291681603725");
        System.out.println("distance = " + result.get("distance"));
        System.out.println("result = " + result.get("distance").toString().replace("\"", ""));
        System.out.println("duration = " + result.get("duration").toString().substring(2, result.get("duration").toString().length()-1));
    }

    public static Map getdistance(String SrcLatLon, String DestLatLon) throws ParseException {
        Map map=new HashMap();
        try {

            URL url = new URL("https://maps.googleapis" +
                    ".com/maps/api/distancematrix/json?origins=" + SrcLatLon.replace("|",",") + "&destinations=" +
                    DestLatLon.replace("|",",") +
                    "&mode=driving" +
                    "&language=pl-PL");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            //System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                if (output.contains("km"))
                {
                    String distance = output.substring(output.indexOf(":") + 1, output.indexOf(","));
                    map.put("distance",distance);
                }else if (output.contains("godz")){
                    String duration = output.substring(output.indexOf(":") + 1, output.indexOf(","));
                    map.put("duration",duration);
                }
                //System.out.println(">>>: "+output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return map;
    }
}
