package com.example.smarthome;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Collections;

public class Person extends AppCompatActivity {
    private Button b;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        t = (TextView) findViewById(R.id.tex);
        //GET request

       /* b = (Button) findViewById(R.id.butt);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGetRequest();
            }
        });*/

        final Handler handler = new Handler();
        final int delay = 5000; //milliseconds

        sendGetRequest();
        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                sendGetRequest();
                handler.postDelayed(this, delay);
            }
        }, delay);


    }

    public void sendGetRequest() {
        String url = "https://lastdetail.azurewebsites.net/lastDetail";
       JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("recordsets").getJSONArray(0);
                            String s = jsonArray.toString();

//                            JSONArray jsonArray = response.getJSONArray("recordsets");
//                            for(int i=0;i<jsonArray.length();i++)
//                            {
//                                JSONObject emp = jsonArray.getJSONObject(i);
////                                String text = emp.getSt
//                            }
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject j = jsonArray.getJSONObject(i);
                                int id = j.getInt("EID");
                                String name = j.getString("person");
                                String date = j.getString("dateval");
                                String time = j.getString("timeval");
                                t.append("ID: "+id+"\nName: "+name+"\nDate: "+date+"\nTime: "+time + "\n");

                            }

//                            String text = jsonArray.toString();
//                            mTextViewResult.setText("json: "+text);
//                            Toast.makeText(getApplicationContext(),"JSON: "+response.toString(),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"try: "+e, Toast.LENGTH_LONG).show();
                            t.setText("try: "+e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"message: "+error,Toast.LENGTH_SHORT).show();
                t.setText("Error Message: "+error);
            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(this);
        mQueue.add(request);
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

    }
}
