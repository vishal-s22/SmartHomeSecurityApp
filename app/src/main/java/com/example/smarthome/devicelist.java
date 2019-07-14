package com.example.smarthome;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class devicelist extends AppCompatActivity {


    private Context mContext;
    //public int a=0;

    private String mJSONURLString = activity_login.urlM + "dataView";

    private static final String TAG = "activity_devicelist";

    //final ArrayList<Entry> yValue= new ArrayList();
    Button btn_insert;
    // private LineChart mChart;
    ArrayList<Entry> x;
    ArrayList<String> y;
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicelist);
        mChart = (LineChart) findViewById(R.id.graphView);
        x = new ArrayList<Entry>();
        y = new ArrayList<String>();
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        //mChart.setPinchZoom(true);
        mChart.setDoubleTapToZoomEnabled(true);
        mContext = getApplicationContext();
        XAxis x1 = mChart.getXAxis();
        x1.setAvoidFirstLastClipping(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(true);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                mJSONURLString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject data = response.getJSONObject(i);
                                int value = data.getInt("temperature");
                                String date = data.getString("time");
                                x.add(new Entry(value, i));
                                y.add(date);
                            }
                            LineDataSet set1 = new LineDataSet(x, "temperature");
                            set1.setLineWidth(3f);
                            set1.setFillAlpha(110);
                            set1.setColor(Color.RED);
                            set1.setCircleRadius(4f);
                            LineData data = new LineData(y, set1);
                            mChart.setData(data);
                            mChart.setScaleMinima(2f, 1f);
                            mChart.invalidate();
                            for (int i = 0; i < 3; i++) {
                               // Log.e(TAG, "bhenchoo  fb " + i + "  " + a[i] + "   " + " " + b[i] + "\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(devicelist.this, "Server Error", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,   error.getLocalizedMessage() + " " + " " + error.getMessage());

                    }
                });

        requestQueue.add(jsonArrayRequest);


    }
}


