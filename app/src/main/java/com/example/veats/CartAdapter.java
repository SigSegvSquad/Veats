package com.example.veats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<CartData> arr;

    public CartAdapter(Context context, ArrayList<CartData> arr) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.cart_list_layout, viewGroup, false);
        TextView itemNameWithQuantity = view.findViewById(R.id.itemNameWithQuantity);
        TextView price = view.findViewById(R.id.price);
        Button deleteItem = view.findViewById(R.id.deleteItem);
        int totalPrice = arr.get(i).getPrice() * arr.get(i).getQuantity();
        String itemNameWQty = arr.get(i).getItemName() + " x " + arr.get(i).getQuantity();
        itemNameWithQuantity.setText(itemNameWQty);
        price.setText(totalPrice + "Rs");
        if (i == arr.size() - 1) {
            deleteItem.setVisibility(View.INVISIBLE);
        }
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, arr.get(i).getItemName() + " Deleted", Toast.LENGTH_SHORT).show();
                arr.remove(i);
                deleteAndSave(arr);
                Intent intent = new Intent(context, CartActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

    public void deleteAndSave(ArrayList<CartData> arr) {
        arr.remove(arr.size() - 1);
        SharedPreferences CartActivity = context.getSharedPreferences("com.example.veats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = CartActivity.edit();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            sb.append("," + arr.get(i).getItemName() + "@#@" + arr.get(i).getPrice() + "@#@" + arr.get(i).getQuantity());
        }
        editor.putString("CartActivity", sb.toString()).commit();
    }
}
