package com.example.smarthome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class all_user extends AppCompatActivity {

    private Context mContext;
    private static final String TAG = "activity_all_user";
    private CoordinatorLayout mCLayout;
    private TextView mTextView;
    private String mJSONURLString = activity_login.urlM+"users";
    Button deleteuser ;
    EditText input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        mContext = getApplicationContext();
        // Activity mActivity = devicelist.this;

        mCLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        //Button mButtonDo = (Button) findViewById(R.id.btn_do);
        mTextView = (TextView) findViewById(R.id.tv);
        deleteuser= (Button) findViewById(R.id.deleteuser);
        input= (EditText) findViewById(R.id.input);

        final Handler handler = new Handler();
        final int delay = 10000; //milliseconds

        usersinfo();

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                usersinfo();
                handler.postDelayed(this, delay);
            }
        }, delay);

        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete(input.getText().toString());
            }
        });

    }

    private void usersinfo()
    {
        mTextView.setText("");

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                mJSONURLString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{

                            for(int i=0;i<response.length();i++){

                                JSONObject data = response.getJSONObject(i);

                                String name = data.getString("user_name");
                                String id = data.getString("user_id");
                                String adm = data.getString("admin");

                                mTextView.append("Name: "+name +"\nid: " + id +"\nAdmin : " + adm);
                                mTextView.append("\n\n");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Snackbar.make(
                                mCLayout,
                                "Error...",
                                Snackbar.LENGTH_LONG
                        ).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    public void delete (final String u_email)
    {
        String url = activity_login.urlM+"deleteAccount";
        JSONObject obj = new JSONObject();
        try{
            obj.put("id",u_email);

        }catch(JSONException e){
            Log.e(TAG, "validateLogin: Error setting JSON" );
        }
        JsonObjectRequest post = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: Response is " + response.toString() );
                try {

                    if (response.has("message") && response.getString("message").matches("account deleted")) {


                        Toast.makeText(all_user.this, "account deleted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    else if (response.has("error_msg") && response.getString("error_msg").matches("error found in delete account")) {
                        Toast.makeText(all_user.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(all_user.this, "Network Issue", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: Error in server response");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(all_user.this, "Server Error",Toast.LENGTH_SHORT).show();
                Log.e(TAG,   error.getLocalizedMessage() + " "  + " " + error.getMessage());

            }
        });
        Volley.newRequestQueue(all_user.this).add(post);
    }
}
