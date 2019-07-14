package com.example.smarthome;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class fragmentnotification extends Fragment {

    View view;
    boolean flagb=false;
    boolean flagd=false;
    String base="http://192.168.1.125:8080";
    //public static String urlM="http://40.87.64.15:4000/ControlDevices";
    private static final String TAG = "fragmentnotification";
    ImageView bulb , door;
    public fragmentnotification() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.notification_fragment,container,false);

        bulb=(ImageView) view.findViewById(R.id.bulb);
        bulb.setImageResource(R.drawable.offffffffffffffff);

        door=(ImageView) view.findViewById(R.id.door);
        door.setImageResource(R.drawable.close2);


        bulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!flagb)
                {
                    bulb.setImageResource(R.drawable.bbb);
                    flagb=true;

                    String url = base+"/bulb/1/1";

                    JsonObjectRequest post = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: Response is " + response.toString() );

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getActivity(), "Server Error",Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "bhenchoo!!! " + error.getLocalizedMessage() + " "  + " " + error.getMessage());
                            /* Ye dikkat hai tere android version ki most probably koi aisa fone jisme kam ho android 9 se usse connect kar*/
                        }
                    });
                    Volley.newRequestQueue(getActivity()).add(post);

                }

                else
                {
                    bulb.setImageResource(R.drawable.offffffffffffffff);
                    flagb=false;

                    String url = base+"/bulb/1/0";


                    JsonObjectRequest post = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: Response is " + response.toString() );

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // Toast.makeText(getActivity(), "Server Error",Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "bhenchoo!!! " + error.getLocalizedMessage() + " "  + " " + error.getMessage());
                            /* Ye dikkat hai tere android version ki most probably koi aisa fone jisme kam ho android 9 se usse connect kar*/
                        }
                    });
                    Volley.newRequestQueue(getActivity()).add(post);

                }
            }
        });

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!flagd) {
                    door.setImageResource(R.drawable.open2);
                    flagd = true;

                    String url = base + "/door/1/1";

                    JsonObjectRequest post = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: Response is " + response.toString());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "bhenchoo!!! " + error.getLocalizedMessage() + " " + " " + error.getMessage());
                            /* Ye dikkat hai tere android version ki most probably koi aisa fone jisme kam ho android 9 se usse connect kar*/
                        }
                    });
                    Volley.newRequestQueue(getActivity()).add(post);

                }
                else
                {
                    door.setImageResource(R.drawable.close2);
                    flagd = false;

                    String url = base + "/door/1/0";

                    JsonObjectRequest post = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: Response is " + response.toString());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                          //  Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                            Log.e(TAG,  error.getLocalizedMessage() + " " + " " + error.getMessage());
                            /* Ye dikkat hai tere android version ki most probably koi aisa fone jisme kam ho android 9 se usse connect kar*/
                        }
                    });
                    Volley.newRequestQueue(getActivity()).add(post);
                }

            }
        });
        return view;
    }



}
