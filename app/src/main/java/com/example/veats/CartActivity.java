package com.example.veats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        contextOfApplication = getApplicationContext();
        final Button placeOrder = findViewById(R.id.placeOrder);


        SharedPreferences sp = this.getSharedPreferences("com.example.veats", Context.MODE_PRIVATE);
        final String order = sp.getString("CartActivity", "null");
        if (!order.equals("null")) {
            ListView cartItems = findViewById(R.id.listView);
            final ArrayList<CartData> itemArrayList = new ArrayList<>();
            int total = 0;
            String[] cartArray = order.split(",");
            int sizeOfCartArray = cartArray.length;
            for (int i = 1; i < sizeOfCartArray; i++) {
                String[] itemElements = cartArray[i].split("@#@");
                itemArrayList.add(new CartData(itemElements[0], Integer.parseInt(itemElements[1]), Integer.parseInt(itemElements[2])));
                total += Integer.parseInt(itemElements[1]) * Integer.parseInt(itemElements[2]);
            }
            final int finalTotal = total;
            itemArrayList.add(new CartData("Total:", total, 1));
            CartAdapter cartAdapter = new CartAdapter(CartActivity.this, itemArrayList);
            cartItems.setAdapter(cartAdapter);
            placeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    placeOrder(itemArrayList, finalTotal);
                    clearCart();
                    Toast.makeText(CartActivity.this, "Order Has Been Placed!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public String arrayToOrderFormat(ArrayList<CartData> arrayList, int total) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size() - 1; i++) {
            sb.append(arrayList.get(i).getItemName() + " x " + arrayList.get(i).getQuantity() + "\n");
        }
        sb.append("Total: " + total);
        return sb.toString();
    }

    public void placeOrder(ArrayList<CartData> arrayList, int total) {
        String content = arrayToOrderFormat(arrayList, total);
        int finalTotal = total;
        SharedPreferences sp = getSharedPreferences("com.example.veats", MODE_PRIVATE);
        int userId = sp.getInt("userId", -1);
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("veats", MODE_PRIVATE, null);
        Cursor c = myDatabase.rawQuery("SELECT * FROM orders", null);
        int orderId;
        if (c.moveToFirst()) {
            c.moveToLast();
            orderId = Integer.parseInt(c.getString(c.getColumnIndex("orderId"))) + 1;
        } else {
            orderId = 1;
        }
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS orders (orderId INT,content VARCHAR,price INT,status VARCHAR,userId INT)");
        myDatabase.execSQL("INSERT INTO orders(orderId,content,price,status,userId) VALUES(" + orderId + ",'" + content + "'," + finalTotal + ",'y'," + userId + ")");

    }

    @Override
    public void onBackPressed() {
        Intent backPress = new Intent(CartActivity.this, MainPageActivity.class);
        startActivity(backPress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.clear_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void clearCart() {
        SharedPreferences sp = this.getSharedPreferences("com.example.veats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
        Intent intent = new Intent(CartActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearCart:
                clearCart();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

}


