package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
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
    private FriendService friendService;


    public HttpServices(Context ctx) {
        context = ctx;
        queue = NetworkQueue.getInstance(context).getRequestQueue();
        friendService = new FriendService(context);
    }


    /*
* add user to server
* */

    //int result = preference.getInt(MainActivity.CURRENT_USER_ID,-1);

    public void addUserToServer(final UserEntity entity){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendPath("add")
                .appendQueryParameter("name", entity.getUsername())
                .appendQueryParameter("email", entity.getEmail())
                .appendQueryParameter("cigs_per_day", entity.getCigsPerDay())
                .appendQueryParameter("price_per_pack", entity.getPricePerPack());

        String url = builder.build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String serverId) {
                        //result may be json so need to parse.
                        DatabaseOperations db = new DatabaseOperations(context);
                        db.updateServerIdForUser(db, serverId, entity.getID() );
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

    public void addBuddyToUser(String friendId, String email, final UserEntity entity) {
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

                params.put("name", entity.getUsername());
                params.put("email", entity.getEmail());
                params.put("cigs_per_day", entity.getCigsPerDay());
                params.put("price_per_pack", entity.getPricePerPack());
                return params;
            }
        };
        queue.add(postRequest);
    }

/*
*
* get user stats
* */

    public void getUserStats(int id) {
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

/*
* increment field on server
* */

    public void incrementFieldOnServer(final String field, final UserEntity entity) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(ENDPOINT)
                .appendPath("user")
                .appendPath("add")
                .appendQueryParameter("name", entity.getUsername())
                .appendQueryParameter("email", entity.getEmail())
                .appendQueryParameter("cigs_per_day", entity.getCigsPerDay())
                .appendQueryParameter("price_per_pack", entity.getPricePerPack());

        String url = builder.build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        // Result handling
                        Log.d("htt:add:postExecute", "**********  updated field: " + result);
                    //userService.updateUser();
                    if(entity.getServerId() != -1 ){
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


    /* get buddies for user*/

    public void getBuddies(final UserEntity userEntity) {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null

                    HttpRunner runner = new HttpRunner();
                    String result = runner.getAllBuddies(userEntity.getServerId() + "");
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);

                    try {
                        JSONArray arr = new JSONArray(result);
                        FeedPost[] posts = new FeedPost[arr.length()];


                        for (int i = 0; i < arr.length(); i++) {
                            String name = arr.getJSONObject(i).getString("name");

                            if(!name.equals(userEntity.getUsername()) ) {
                                JSONObject json = arr.getJSONObject(i);
                                String time = !json.isNull("time") ? json.getString("time"):"";
                                String total_days_free = !json.isNull("days_free") ? json.getString("days_free"):"";
                                String money_saved = !json.isNull("money_saved") ? json.getString("money_saved"):"";
                                String life_regained = !json.isNull("life_regained") ? json.getString("life_regained"):"";
                                String cigs_per_day = !json.isNull("cigs_per_day") ? json.getString("cigs_per_day"):"";
                                String email = !json.isNull("email") ? json.getString("email"):"";

                                //not on jsonobject
                                String current_streak = "0";
                                String longest_streak = "0";
                                String num_cravings = "0";
                                String cravings_resisted = "0";
                                String num_cigs_smoked = "0";

                                FriendEntity friend = new FriendEntity();

                                friend.setFriendObject(name, email, time, total_days_free, longest_streak, current_streak, num_cravings, cravings_resisted, num_cigs_smoked, money_saved, life_regained);

                                friend.setParentId(userEntity.getID());

                                friendService.addFriendStats(friend, userEntity.getID());
                            }
                        }

                    } catch (JSONException e){

                    }
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }
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
