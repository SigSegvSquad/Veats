package com.example.veats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    Context context;
    ArrayList<MenuData> arr;

    public MenuAdapter(Context context, ArrayList<MenuData> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.menu_view,viewGroup,false);
        TextView item=view.findViewById(R.id.item);
        TextView price=view.findViewById(R.id.price);

        item.setText(arr.get(i).getItem());
        price.setText(arr.get(i).getPrice()+"Rs");

        return view;
    }
}
