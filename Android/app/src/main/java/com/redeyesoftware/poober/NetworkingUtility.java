package com.redeyesoftware.poober;

/**
 * Created by George on 20/11/2016.
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkingUtility {

    public static String url = "http://74.201.57.189:81/poober";
    public static RequestQueue queue;

    public static String comments[][];

    public static void setUpRequestQueue(MainActivity parentActivity) {
        queue = Volley.newRequestQueue(parentActivity);
    }

    private static void callMethodOnFinished(String key) {
        switch (key) {
            case "fillMap":
                MapFragment.me.addMapMarkers();
                return;
            /*case "addMoreToFeed":
                RefreshableScrollView.addCommentsToFeed(true, false, false, false);
                return;*/
        }
    }

    public static void getPoops(final String urlEnd, final String[] tags, final String methodKey) {
        comments = null;//if not rewritten, will send back empty array

        String newUrl = url + urlEnd;


        // Log.d("Sending to this url", newUrl);
        // Request a string response from the provided URL.
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, newUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            comments = new String[response.length()][tags.length];
                            for(int i = 0; i<response.names().length(); i++){
                                JSONObject poo = response.getJSONObject(response.names().getString(i));

                                for (int j = 0; j < tags.length; j++) {
                                    comments[i][j] = poo.getString(tags[j]).replaceAll("\\uFFFD","");
                                }
                            }
                            callMethodOnFinished(methodKey);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("Debug", "Error: " + e.getMessage());
                        }




                        /*Log.i(TAG,response.toString());
                        try{
                            JSONObject value = response.getJSONObject("label");
                            String essai = value.getString("pers_nom");
                            Toast.makeText(getActivity(), ""+essai, Toast.LENGTH_SHORT).show();
                            Log.d("volley output", essai);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Debug", "Error with Volley Get" +  error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(req);
    }

    /*public static String get(final String urlEnd, final String[] keys, final String[] values) {
        response= "ERROR";//if not rewriiten, will send back "ERROR"

        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append(urlEnd);
        for (int i=0; i<keys.length;i++) {
            if(i == 0) {
                stringBuilder.append("?" + keys[i] + "=" + values[i]);
            } else {
                stringBuilder.append("&" + keys[i] + "=" + values[i]);
            }
        }
        String newUrl = stringBuilder.toString();
        Log.d("Sending to this url", newUrl);
        // Request a string response from the provided URL.
        JsonArrayRequest req = new JsonArrayRequest(newUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("The JSON was:", response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            String jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);

                                jsonResponse += "Message #" + i + "\n";
                                JSONObject message = response.getJSONObject(i);

                                String name = message.getString("author_id");
                                String score = message.getString("msg_id");
                                String body = message.getString("text");
                                String timestamp = message.getString("timestamp");

                                jsonResponse += "author_id: " + name + "\n";
                                jsonResponse += "msgID: " + score + "\n";
                                jsonResponse += "text: " + body + "\n";
                                jsonResponse += "timestamp: " + timestamp + "\n\n";



                            }
                            Log.d("Debug", "Parsed JSON is: " + jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Debug", "Error: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Debug", "Error with Volley Get");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(req);
        return response;
    }*/


    public static void post(final String urlEnd, final String[] keys, final String[] values) {
        // Request a string response from the provided URL.
        String newUrl = url + urlEnd;


        Map<String, String> postParam= new HashMap<String, String>();
        for (int i =0; i<keys.length;i++ ) {
            postParam.put(keys[i], values[i]);
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, newUrl, new JSONObject(postParam), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Debug", "Response to post is: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Debug", "Error with Volley Post");

                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }

        };

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);


        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Debug", "Response to post is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Debug", "Error with Volley Post");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                for (int i =0; i<keys.length;i++ ) {
                    params.put(keys[i], values[i]);
                }
                return params;
            }

        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);*/


    }
}
