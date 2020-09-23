package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnsearch, btnchuyenmanhinh;
    EditText editTextsearch;
    TextView txtname, txtcountry, txttemp, txtsatus, txthumcity, txtclound, txtwindy, txtdate;
    ImageView imgicon;

    String city = "Saigon";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        getCurrentWeatherData(city);


        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextsearch.getText().toString().equals("") == false){
                    city = editTextsearch.getText().toString().trim();
                }
                getCurrentWeatherData(city);
            }
        });
        btnchuyenmanhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                i.putExtra("name", city);
                startActivity(i);
            }
        });
    }

    public void getCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&appid=79bcb14add2fe9d446dcd1abe98f6a44&units=metric";
        Log.d("urlllll", url);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("abc", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String day = jsonObject.getString("dt");
                    String name = jsonObject.getString("name");
                    txtname.setText("Tên thành phố: " + name);

                    //Đổi ngày Unix -> ngày theo định dạng
                    long l = Long.valueOf(day);
                    Log.d("aaaaaa",String.valueOf(l * 1000L));
                    Date date = new Date(l * 1000L);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");
                    day = simpleDateFormat.format(date);
                    txtdate.setText(day);

                    //phần Weather (thời tiết)
                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String status = jsonObjectWeather.getString("main");
                    String icon = jsonObjectWeather.getString("icon");
                    Picasso.with(getApplicationContext()).load("https://openweathermap.org/img/wn/" + icon + ".png").into(imgicon);
                    txtsatus.setText(status);

                    //Phần main
                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String nhietdo = jsonObjectMain.getString("temp");
                    String doam = jsonObjectMain.getString("humidity");

                    Double a = Double.valueOf(nhietdo);
                    nhietdo = String.valueOf(a.intValue());
                    txttemp.setText(nhietdo + "°C");// phím tắt alt + 0176: °
                    txthumcity.setText(doam + "%");

                    //Phần wind (gio)
                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    txtwindy.setText(gio + "m/s");

                    //Phần cloud (Mây)
                    JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectCloud.getString("all");
                    txtclound.setText(may + "%");

                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    String country = jsonObjectSys.getString("country");
                    txtcountry.setText("Tên quốc gia: " + country);


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


    public void AnhXa() {
        btnsearch = findViewById(R.id.btnsearch);
        btnchuyenmanhinh = findViewById(R.id.btnchuyenmanhinh);
        editTextsearch = findViewById(R.id.edittsearch);
        txtname = findViewById(R.id.txtnamecity);
        txtcountry = findViewById(R.id.txttenquocgia);
        txttemp = findViewById(R.id.txtnhietdo);
        txtsatus = findViewById(R.id.txttrangthai);
        txthumcity = findViewById(R.id.txthumidity);
        txtclound = findViewById(R.id.txtmay);
        txtwindy = findViewById(R.id.txtgio);
        txtdate = findViewById(R.id.txtngaythang);
        imgicon = findViewById(R.id.imgicon);

    }
}
