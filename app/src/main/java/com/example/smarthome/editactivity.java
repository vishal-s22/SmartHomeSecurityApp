package com.example.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class editactivity extends AppCompatActivity {

    private EditText name,pass,uid,phone,uemail;
    private static final String TAG = "editactivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editactivity);

        //private EditText id,ad,nm,ph,em;
        Button update =(Button) findViewById(R.id.update);

        name= (EditText) findViewById(R.id.name);
        pass= (EditText) findViewById(R.id.pass);
        //ad= (EditText) findViewById(R.id.admine);
        uid= (EditText) findViewById(R.id.uid);
        phone= (EditText) findViewById(R.id.phone);
        uemail= (EditText) findViewById(R.id.uemail);
        //po=(EditText) findViewById(R.id.passwordeo);

        name.setText(profile.strn);
        //ad.setText(activity_login.str);
        pass.setText(activity_login.pas);
        phone.setText(profile.strm);
        uid.setText(activity_login.str1);
        uemail.setText(profile.stre);
        //pn.setText(activity_login.pas);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass.getText().toString().isEmpty() || pass.getText().toString().length() < 3 || pass.getText().toString().length() > 10) {
                    pass.setError("between 3 and 10 alphanumeric characters");
                }

                else if (uemail.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(uemail.getText().toString()).matches()) {
                    uemail.setError("enter a valid email address");
                }
                else if (phone.getText().toString().isEmpty() || phone.getText().toString().length() < 3 || phone.getText().toString().length() > 10) {
                    phone.setError("enter a valid email address");
                }

                else if(name.getText().toString().isEmpty())
                {
                    name.setError("enter name");
                }
                else if(uid.getText().toString().isEmpty())
                {
                    uid.setError("user id can't be empty");
                }
                else {
                    user_update(uid.getText().toString(), activity_login.str1, pass.getText().toString(), phone.getText().toString(), uemail.getText().toString());
                }
            }
        });



    }

    private void user_update(String u_idn, String u_ido, String u_pn, String u_ph,  String u_em) {

        String url = activity_login.urlM+"updateUserAccount";
        JSONObject obj = new JSONObject();
        try{
            obj.put("id",u_ido);
            obj.put("newId",u_idn);
            obj.put("password",u_pn);
            //obj.put("old_password",u_po);
            //obj.put("admin",u_ad);
            obj.put("email",u_em);
            obj.put("mobile",u_ph);
        }catch(JSONException e){
            Log.e(TAG, "user update: Error setting JSON" );
        }
        JsonObjectRequest post = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: Response is " + response.toString() );
                try {

                    if (response.has("message") && response.getString("message").matches("account updated")) {

                        Toast.makeText(editactivity.this, "successfully updated", Toast.LENGTH_SHORT).show();

                    }

                    else if (response.has("error_msg") && response.getString("error_msg").matches("error found in update account")) {
                        Toast.makeText(editactivity.this, "process Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(editactivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: Error in server response");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editactivity.this, "Server Error",Toast.LENGTH_SHORT).show();
                Log.e(TAG,  error.getLocalizedMessage() + " "  + " " + error.getMessage());

            }
        });
        Volley.newRequestQueue(editactivity.this).add(post);
    }
}

