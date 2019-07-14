package com.example.smarthome;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.smarthome.activity_login.urlM;

public class devicenames extends AppCompatActivity {


    Button temp, humi;
    TextView Ltemp,Lhumi;
    ImageButton grapht, graphh;
    private static final String TAG = "activity_devicenames";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicenames);
        temp=(Button) findViewById(R.id.temp);
        humi= (Button) findViewById(R.id.humi);
        Ltemp=(TextView) findViewById(R.id.templive);
        Lhumi=(TextView) findViewById(R.id.humilive);
        grapht=(ImageButton) findViewById(R.id.grapht);
        graphh=(ImageButton) findViewById(R.id.graphh);
        Ltemp.setText("");
        Lhumi.setText("");
        blink(null);
        final Handler handler = new Handler();
        final int delay = 3000; //milliseconds

        live();

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                live();
                handler.postDelayed(this, delay);
            }
        }, delay);

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(devicenames.this,devicelist.class);
                startActivity(i);
            }
        });

        humi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(devicenames.this,devicelisthumi.class);
                startActivity(i);
            }
        });

        grapht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(devicenames.this,devicelist.class);
                startActivity(i);
            }
        });

        graphh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(devicenames.this,devicelisthumi.class);
                startActivity(i);
            }
        });
    }

    public void blink(View view){
        ImageView imaget = (ImageView)findViewById(R.id.livet);
        ImageView imageh = (ImageView)findViewById(R.id.liveh);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.blink);
        imaget.startAnimation(animation1);
        imageh.startAnimation(animation1);
    }

    private void live() {
        String url = urlM+"lastData";
        JSONObject obj = new JSONObject();

        JsonObjectRequest post = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: Response is " + response.toString() );
                try {

                    if (response.has("temperature") ) {
                        Log.e(TAG, "temperature is " + response.getString("temperature"));
                        Ltemp.setText(response.getString("temperature")+"°C");
                    }
                    if (response.has("humidity") ) {

                        Lhumi.setText(response.getString("humidity")+"%rh");
                    }
                    else if (response.has("message") && response.getString("message").matches("No data found")) {
                        Toast.makeText(devicenames.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(devicenames.this, "Network Issue", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: Error in server response");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(devicenames.this, "Server Error",Toast.LENGTH_SHORT).show();
                Log.e(TAG,  error.getLocalizedMessage() + " "  + " " + error.getMessage());

            }
        });
        Volley.newRequestQueue(devicenames.this).add(post);


    }
}
