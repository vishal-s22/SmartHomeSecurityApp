package com.example.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class activity_login extends AppCompatActivity {

    static String jsonstr;
    private EditText email;
    private EditText password;
    private Button signIn;
    public static String str1 ;
    public  static String str;
    public static String pas;
    public static String urlM="http://23.100.24.183:3000/";

    private static final String TAG = "activity_login";

    ArrayList<NameValuePair> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FirebaseApp.initializeApp(this);
        //Log.e(TAG,"onCreate:Token is"+ FirebaseInstanceId.getInstance().getToken());


        email = (EditText) findViewById(R.id.userid);
        password = (EditText) findViewById(R.id.passwordid);
        signIn = (Button) findViewById(R.id.loginbuttonid);



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (password.getText().toString().isEmpty() || password.getText().toString().length() < 3 || password.getText().toString().length() > 10) {
                    password.setError("between 3 and 10 alphanumeric characters");
                }

                else if(email.getText().toString().isEmpty())
                {
                    email.setError("user id can't be empty");
                }
                else {
                    // validateLogin(email.getText().toString(), password.getText().toString());
                    Intent i = new Intent(activity_login.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    private void validateLogin(final String u_email, final String u_password) {



        String url = urlM+"login";
        JSONObject obj = new JSONObject();
        try{
            obj.put("id",u_email);
            obj.put("password",u_password);
        }catch(JSONException e){
            Log.e(TAG, "validateLogin: Error setting JSON" );
        }
        JsonObjectRequest post = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: Response is " + response.toString() );
                try {
                    if (response.has("admin") && response.getString("admin").matches("true")) {
                        str="true";
                        str1=u_email;
                        pas=u_password;

                        Toast.makeText(activity_login.this, "Admin login", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("message", str);
                        //Intent i = new Intent(activity_login.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else if(response.has("admin")&& response.getString("admin").matches("false")){
                        str="false";
                        Toast.makeText(activity_login.this,"login successful", Toast.LENGTH_SHORT).show();
                        str1=u_email;
                        pas=u_password;
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("message", str);
                        //Intent i =new Intent(activity_login.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else if (response.has("msg") && response.getString("msg").matches("Unsuccessful")) {
                        Toast.makeText(activity_login.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(activity_login.this, "Network Issue", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: Error in server response");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity_login.this, "Server Error",Toast.LENGTH_SHORT).show();
                Log.e(TAG, error.getLocalizedMessage() + " "  + " " + error.getMessage());

            }
        });
         Volley.newRequestQueue(activity_login.this).add(post);
    }


    //String getstr(){return str;}
    }







