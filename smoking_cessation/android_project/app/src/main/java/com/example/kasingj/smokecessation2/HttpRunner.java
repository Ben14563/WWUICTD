package com.example.kasingj.smokecessation2;

import android.net.Uri;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRunner{
    private static final String ENDPOINT = "http://app.Cessation.com/services/";

    String makeRequest(String urlSpec) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            StringBuffer buffer = new StringBuffer();

            //read from the connection
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String FinalJSON = buffer.toString();
            //return string-JSON


            return FinalJSON ; /*key1 + " " + key2; // return the json as a string*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public String getUrl(String urlSpec) throws IOException {
       return new String(makeRequest( urlSpec));
   }
    public String getFriendInformation(String friend){
        //this will retrieve friend information
        String JSONString = null;
        String url = Uri.parse(ENDPOINT).buildUpon().appendQueryParameter("", "")
                .appendQueryParameter("", "")
                .appendQueryParameter("","")
                .appendQueryParameter("","")
                .appendQueryParameter("","").build().toString();

        try {
             JSONString = getUrl(url);
        } catch(IOException e){
            e.printStackTrace();
        }
        return JSONString;
    }
}
/*
* post execute methods
*
* Given http result needs to update views or update database.
* */



/*           **********************************8*** CODE DUMP ********************************************************

            /*JSONObject parentJSON = new JSONObject(FinalJSON);
            JSONArray parentArray = parentJSON.getJSONArray("temporary"); //provide the key for the array
            JSONObject finalObject = parentArray.getJSONObject(0); //use final object and pass it keys associated with values
            String key1 = finalObject.getString("name_of_key1");
            String key2 = finalObject.getString("name_of_key2");

            catch (JSONException e) {
            e.printStackTrace();
        }
            */




