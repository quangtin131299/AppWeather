package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    TextView txtname;
    ImageView imgback;
    ListView lst;
    ArrayList<ThoiTiet> thoiTiets;
    ThoiTietAdapter thoiTietAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        String city = i.getStringExtra("name");
        AnhXa();
        get7DayData(city);

    }

    private void AnhXa() {
        lst = findViewById(R.id.lstview);
        imgback = findViewById(R.id.imgback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtname = findViewById(R.id.txttenthanhpho);
        thoiTiets = new ArrayList<>();
        thoiTietAdapter = new ThoiTietAdapter(getApplicationContext(), thoiTiets);
        lst.setAdapter(thoiTietAdapter);


    }

    private void get7DayData(String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + data + "&appid=79bcb14add2fe9d446dcd1abe98f6a44&cnt=7&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String tenthanhpho = jsonObjectCity.getString("name");
                    txtname.setText(tenthanhpho);

                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String ngay = item.getString("dt");

                        long l = Long.valueOf(ngay);
                        Log.d("aaaaaa", String.valueOf(l * 1000L));
                        Date date = new Date(l * 1000L);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");
                        ngay = simpleDateFormat.format(date);

                        JSONObject jsonmain = item.getJSONObject("main");
                        String max = jsonmain.getString("temp_max");

                        Double tempmax = Double.parseDouble(max);
                        max = String.valueOf(tempmax.intValue());

                        String min = jsonmain.getString("temp_min");

                        Double tempmin = Double.parseDouble(min);
                        min = String.valueOf(tempmax.intValue());

                        JSONArray jsonweather = item.getJSONArray("weather");
                        JSONObject jsonObjectweather = jsonweather.getJSONObject(0);
                        String descrip = jsonObjectweather.getString("description");
                        String icon = jsonObjectweather.getString("icon");

                        thoiTiets.add(new ThoiTiet(
                                ngay,
                                descrip,
                                icon,
                                max,
                                min
                        ));
                    }

                    thoiTietAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

    }
}
