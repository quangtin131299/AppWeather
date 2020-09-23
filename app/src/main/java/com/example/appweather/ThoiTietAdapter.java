package com.example.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThoiTietAdapter extends BaseAdapter {

    Context context;
    ArrayList<ThoiTiet> thoiTiets = new ArrayList<>();

    public ThoiTietAdapter(Context context, ArrayList<ThoiTiet> thoiTiets) {
        this.context = context;
        this.thoiTiets = thoiTiets;
    }

    @Override
    public int getCount() {
        return thoiTiets.size();
    }

    @Override
    public Object getItem(int position) {
        return thoiTiets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.dong_listview, null);

        ThoiTiet thoiTiet = thoiTiets.get(position);
        TextView txtday = convertView.findViewById(R.id.txtngay);
        TextView txttrangthai = convertView.findViewById(R.id.txttrangthai);
        TextView txtmaxtemp = convertView.findViewById(R.id.txtmax);
        TextView txtmintemp = convertView.findViewById(R.id.txtmin);
        ImageView img = convertView.findViewById(R.id.imgtrangthai);


        txtday.setText(thoiTiet.getDay());
        txttrangthai.setText(thoiTiet.getStatus());
        txtmaxtemp.setText(thoiTiet.getNhietdomax() + "°C");
        txtmintemp.setText(thoiTiet.getNhietdomin() + "°C");
        Picasso.with(context).load("https://openweathermap.org/img/wn/" + thoiTiet.getImg() + ".png").into(img);

        return convertView;
    }
}
