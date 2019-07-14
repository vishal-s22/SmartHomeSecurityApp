package com.example.smarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class profile extends AppCompatActivity {

    public static String strn ,stri, stre, stra, strm, strp;
    private TextView name ,admin, email, id , mobile;
    String str=activity_login.str1;
    private Button edit ,allusers;
    private static final String TAG = "activity_profile";
    ArrayList<NameValuePair> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = (TextView) findViewById(R.id.uemail);
        id = (TextView) findViewById(R.id.uid);
        name = (TextView) findViewById(R.id.name);
        mobile = (TextView) findViewById(R.id.phone);
        admin = (TextView) findViewById(R.id.admin);
        edit = (Button) findViewById(R.id.edit);
        allusers=(Button) findViewById(R.id.allu);
        info(str);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile.this , editactivity.class);
                startActivity(i);
            }
        });

        allusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=activity_login.str;
                if( s.equals("true"))
                {
                    Intent i= new Intent(profile.this,all_user.class);
                    startActivity(i);
                }

            }
        });
    }

    private void info(final String u_email) {

        String url = activity_login.urlM+"profile";
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

                    if (response.has("user_name") ) {
                        strn= response.getString("user_name");
                        name.setText(strn);
                        stri=u_email;
                        id.setText(u_email);

                    }

                    if (response.has("user_email") ) {
                        stre= response.getString("user_email");
                        email.setText(stre);
                    }
                    if (response.has("admin") ) {
                        stra= response.getString("admin");
                        admin.setText(stra);
                    }

                    if (response.has("user_mobile") ) {
                        strm= response.getString("user_mobile");
                        mobile.setText(strm);
                    }
                    else
                        Toast.makeText(profile.this, "Network Issue", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: Error in server response");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profile.this, "Server Error",Toast.LENGTH_SHORT).show();
                Log.e(TAG,   error.getLocalizedMessage() + " "  + " " + error.getMessage());

            }
        });
        Volley.newRequestQueue(profile.this).add(post);


    }

}

