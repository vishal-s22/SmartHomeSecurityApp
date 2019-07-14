package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class registeruser extends AppCompatActivity {



    private EditText emailf;
    private EditText passwordf;
    private EditText namef;
    private EditText phonenof;
    private EditText adminf,idf;

    private static final String TAG = "registeruser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);


        emailf = (EditText) findViewById(R.id.email);
        passwordf = (EditText) findViewById(R.id.password);
        namef = (EditText) findViewById(R.id.name);
        phonenof =(EditText) findViewById(R.id.phoneno);
        adminf =(EditText) findViewById(R.id.admin);
        idf=(EditText) findViewById(R.id.id);
        final Button register = (Button) findViewById(R.id.registerid);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passwordf.getText().toString().isEmpty() || passwordf.getText().toString().length() < 3 || passwordf.getText().toString().length() > 10) {
                    passwordf.setError("between 3 and 10 alphanumeric characters");
                }

                else if (emailf.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailf.getText().toString()).matches()) {
                    emailf.setError("enter a valid email address");
                }
                else if (phonenof.getText().toString().isEmpty() || phonenof.getText().toString().length() < 3 || phonenof.getText().toString().length() > 10) {
                    phonenof.setError("enter a valid email address");
                }
                else if (adminf.getText().toString().isEmpty() || !(adminf.getText().toString().equals("true") || adminf.getText().toString().equals("false"))) {
                    adminf.setError("enter a valid admin status");
                }
                else if(namef.getText().toString().isEmpty())
                {
                    namef.setError("enter name");
                }
                else if(idf.getText().toString().isEmpty())
                {
                    idf.setError("user id can't be empty");
                }
                else {
                    Toast.makeText(registeruser.this,"done",Toast.LENGTH_LONG).show();
                    register_user(idf.getText().toString(), emailf.getText().toString(), passwordf.getText().toString(), namef.getText().toString(), phonenof.getText().toString(), adminf.getText().toString());
                }
            }
        });


    }

    private void register_user(String u_id, String u_email, String u_password, String u_name, String u_phoneno, String u_admin) {

        String url = activity_login.urlM+"register";
        JSONObject obj = new JSONObject();
        try{
            obj.put("id",u_id);
            obj.put("password",u_password);
            obj.put("name",u_name);
            obj.put("admin",u_admin);
            obj.put("email",u_email);
            obj.put("mobile",u_phoneno);
        }catch(JSONException e){
            Log.e(TAG, "registeruser: Error setting JSON" );
        }
        JsonObjectRequest post = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: Response is " + response.toString() );
                try {

                    if (response.has("response") && response.getString("response").matches("Done")) {

                        Toast.makeText(registeruser.this, "successfully registered", Toast.LENGTH_SHORT).show();

                    }

                    else if (response.has("msg") && response.getString("msg").matches("Unsuccessful")) {
                        Toast.makeText(registeruser.this, "process Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(registeruser.this, "Network Issue", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: Error in server response");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(registeruser.this, "Server Error",Toast.LENGTH_SHORT).show();
                Log.e(TAG,   error.getLocalizedMessage() + " "  + " " + error.getMessage());

            }
        });
        Volley.newRequestQueue(registeruser.this).add(post);
    }
}