package com.example.kasingj.smokecessation2;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.net.ConnectivityManager;
import android.content.Context;
import android.net.NetworkInfo;



public class HttpRunner{
    private static final String ENDPOINT = "198.199.67.166";
    String makeRequest(String urlSpec) {
        Log.d("HTTP:MAKE REQUEST** ", "made it into makeRequest");
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d("***makeRequest***", "Not making a connection with the server" );
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
    public String getUserInformation(String friendId){
        //this will retrieve friend information
        String JSONString = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendPath(friendId);
        String url = builder.build().toString();
        try {
            JSONString = getUrl(url);
        } catch(IOException e){
            e.printStackTrace();
        }
        return JSONString;
    }



    public String addUser(String userName, String userEmail, String cigs_per_day, String price_per_pack){
        //this will retrieve friend information
        String JSONString = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendPath("add")
                .appendQueryParameter("name", userName)
                .appendQueryParameter("email", userEmail)
                .appendQueryParameter("cigs_per_day", cigs_per_day)
                .appendQueryParameter("price_per_pack", price_per_pack);
        String url = builder.build().toString();

        try {
            JSONString = getUrl(url);
        } catch(IOException e){
            e.printStackTrace();
        }
        return JSONString;
    }





    public String incrementField(String userId, String day, String Field){
        String JSONString = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendPath("add")
                .appendQueryParameter("id", userId)
                .appendQueryParameter("day", day)
                .appendQueryParameter("field", Field);
        String url = builder.build().toString();

        try {
            JSONString = getUrl(url);
        } catch(IOException e){
            e.printStackTrace();
        }
        return JSONString; //need to parse, is there anything to return
    }


    public String getFeedForUser(String userId){
        String JSONString = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendQueryParameter("id", userId);
        String url = builder.build().toString();
        try {
            JSONString = getUrl(url);
        } catch(IOException e){
            e.printStackTrace();
        }
        return JSONString; //need to parse, is there anything to return
    }
    //return info for given day (mm-dd-YYYY) and user
    public String getDataForDay(String userId,String day){
        String JSONString = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendQueryParameter("id", userId)
                .appendQueryParameter("day", day);
        String url = builder.build().toString();
        try {
            JSONString = getUrl(url);
        } catch(IOException e){
            e.printStackTrace();
        }
        return JSONString; //need to parse, is there anything to return
    }

    //add a new day for the given user all fields set to 0
    public String addNewCurrentDay(String userId){
        String JSONString = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendQueryParameter("id", userId);
        String url = builder.build().toString();
        try {
            JSONString = getUrl(url);
        } catch(IOException e){
            e.printStackTrace();
        }
        return JSONString; //need to parse, is there anything to return
    }

    //add buddy to user, user email must exist.
    public String addBuddyToUser(String userId, String email){
        String JSONString = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendPath(userId)
                .appendQueryParameter("email", email);
        String url = builder.build().toString();
        try {
            JSONString = getUrl(url);
        } catch(IOException e){
            e.printStackTrace();
        }
        return JSONString; //might not be anything to return
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

         String url = Uri.parse(ENDPOINT+"/user/").buildUpon().appendQueryParameter("", "")
                .appendQueryParameter("", "")
                .appendQueryParameter("","")
                .appendQueryParameter("","")
                .appendQueryParameter("","").build().toString();

            */

