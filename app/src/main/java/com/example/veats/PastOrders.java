package com.example.veats;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PastOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);
        ListView pastOrdersList=findViewById(R.id.pastOrdersList);
        SharedPreferences sp=getSharedPreferences("com.example.veats",MODE_PRIVATE);
        final int userId=sp.getInt("userId",-1);
        final Cursor c;
        final SQLiteDatabase myDatabase= openOrCreateDatabase("veats", MODE_PRIVATE,null);
        if(userId==0){
            c=myDatabase.rawQuery("SELECT * FROM orders",null);
        } else {
            c=myDatabase.rawQuery("SELECT * FROM orders WHERE NOT userId="+userId,null);
        }
        c.moveToFirst();
        int orderIdIndex=c.getColumnIndex("orderId");
        int contentIndex=c.getColumnIndex("content");
        int amountIndex=c.getColumnIndex("price");
        final ArrayList<MenuData> orderArray = new ArrayList<MenuData>();
        final ArrayList<String> orderInfo= new ArrayList<>();
        while(!c.isAfterLast()){
            int orderId=Integer.parseInt(c.getString(orderIdIndex));
            int amount=c.getInt(amountIndex);
            String content=c.getString(contentIndex);
            orderArray.add(new MenuData("Order: "+orderId,amount,orderId));
            orderInfo.add(content);
            c.moveToNext();
        }
        MenuAdapter menuAdapter=new MenuAdapter(PastOrders.this,orderArray);
        pastOrdersList.setAdapter(menuAdapter);
        pastOrdersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PastOrders.this);
                builder.setTitle("Order Content");
                builder.setMessage(orderInfo.get(i));

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
    }
}
