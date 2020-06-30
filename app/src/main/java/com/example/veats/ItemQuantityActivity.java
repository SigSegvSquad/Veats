package com.example.veats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemQuantityActivity extends AppCompatActivity {
    private int quantity = 0;
    private String item;


    public void addToCart(View view) {
        if (quantity > 0) {

            TextView itemName = findViewById(R.id.itemName);
            Intent itemQty = getIntent();
            item = itemQty.getStringExtra("itemName");
            int price = itemQty.getIntExtra("itemPrice", 0);
            String id = itemQty.getStringExtra("itemId");

            SharedPreferences CartActivity = this.getSharedPreferences("com.example.veats", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = CartActivity.edit();
            String orderString = CartActivity.getString("CartActivity", "");
            StringBuilder sb = new StringBuilder();
            String[] orderArray = orderString.split(",");
            for (int i = 1; i < orderArray.length; i++) {
                String[] elements = orderArray[i].split("@#@");
                if (elements[0].equals(item)) {
                } else {
                    sb.append("," + elements[0] + "@#@" + elements[1] + "@#@" + elements[2]);
                }
            }

            sb.append("," + item + "@#@" + price + "@#@" + quantity);
            editor.putString("CartActivity", sb.toString()).apply();

            Intent goBack = new Intent(ItemQuantityActivity.this, MainPageActivity.class);
            finish();
            startActivity(goBack);
        }
    }

    public void plus(View view) {
        TextView quant = findViewById(R.id.qty);
        quantity++;
        quant.setText("" + quantity);
    }


    public void minus(View view) {
        TextView quant = findViewById(R.id.qty);
        if (quantity > 0) {
            quantity--;
            quant.setText("" + quantity);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_quantity);

        TextView itemName = findViewById(R.id.itemName);
        Intent itemQty = getIntent();
        item = itemQty.getStringExtra("itemName");
        itemName.setText(item);
    }
}
