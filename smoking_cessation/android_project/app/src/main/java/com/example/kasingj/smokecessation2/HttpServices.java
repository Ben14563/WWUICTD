package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kasingj on 10/13/16.
 */
public class HttpServices {

    private static final String ENDPOINT = "198.199.67.166";
    private static Context context;
    private RequestQueue queue;

    public HttpServices(Context ctx) {
        context = ctx;
        queue = NetworkQueue.getInstance(context).getRequestQueue();
    }


    /*
* add user to server
* */


    public void addUserToServer() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendPath("add")
                .appendQueryParameter("name", User.getInstance().getUsername())
                .appendQueryParameter("email", User.getInstance().getEmail())
                .appendQueryParameter("cigs_per_day", User.getInstance().getCigsPerDay())
                .appendQueryParameter("price_per_pack", User.getInstance().getPricePerPack());

        String url = builder.build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String serverId) {
                        // Result handling
                        User.getInstance().setServerId(Integer.parseInt(serverId)); //result may be json so need to parse.
                        DatabaseOperations db = new DatabaseOperations(context);
                        db.updateServerIdForUser(db, serverId);
                        //db.addUserStats(db, username, serverId, time, "0", "0", "0", "0", "0", "0", "0.00", "0" , cigsPerDay,pricePerPack, numYearsSmoked);
                        Toast.makeText(context, "Server ID: " + serverId, Toast.LENGTH_LONG).show();
                        db.close();
                        //go to dashboard
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();
            }

        });
        queue.add(stringRequest);
    }

    /*
    * add buddy to user
    * */

    public void addBuddyToUser(String friendId, String email) {
        String url = ENDPOINT + "/" + friendId + "/add/" + email;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Log.d("addBuddyToUser", "user exists id = " + response);
                        } else {
                            Log.d("addBuddyToUser", "User does not exist");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("addBuddyToUser", "Something Went Wrong");
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                User user = User.getInstance();
                params.put("name", user.getUsername());
                params.put("email", user.getEmail());
                params.put("cigs_per_day", user.getCigsPerDay());
                params.put("price_per_pack", user.getPricePerPack());
                return params;
            }
        };
        queue.add(postRequest);
    }

/*
*
* get user stats
* */

    public void getUserStats(String id) {
        String url = ENDPOINT + "/user/" + id;

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject! , or use json array
                        try {
                            response = response.getJSONObject("args");

                            /*
                            * result will be  json so need to parse. need function to parse data add to database
                            do stuff with user data
                            * */

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(jsonRequest);
    }
}
/*
* increment field on server
* */
/*
    public void incrementFieldOnServer(final String field) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendPath("add")
                .appendQueryParameter("name", User.getInstance().getUsername())
                .appendQueryParameter("email", User.getInstance().getEmail())
                .appendQueryParameter("cigs_per_day", User.getInstance().getCigsPerDay())
                .appendQueryParameter("price_per_pack", User.getInstance().getPricePerPack());

        String url = builder.build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        // Result handling
                        Log.d("htt:add:postExecute", "**********  updated field: " + result);
                    //userService.updateUser();
                    if(User.getInstance().getServerId() != -1 ){
                        //getFeed();
                    }else {
                        //userService.updateUser();
                    }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }
/*

}


/* template get request
*
*

StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String serverId) {

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            // Error handling
            System.out.println("Something went wrong!");
            error.printStackTrace();
        }

    });
*/
