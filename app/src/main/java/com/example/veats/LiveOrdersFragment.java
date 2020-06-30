package com.example.veats;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class LiveOrdersFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_live_orders_fragment, container, false);
        ListView liveOrdersList=view.findViewById(R.id.liveOrdersList);
        SharedPreferences sp=getActivity().getSharedPreferences("com.example.veats",MODE_PRIVATE);
        final int userId=sp.getInt("userId",-1);
        final Cursor c;
        final SQLiteDatabase myDatabase= getActivity().openOrCreateDatabase("veats", MODE_PRIVATE,null);
        if(userId==0){
            c=myDatabase.rawQuery("SELECT * FROM orders WHERE NOT status = 'n'",null);
        }
        else{
            c=myDatabase.rawQuery("SELECT * FROM orders WHERE NOT status = 'n' AND userId="+userId,null);
        }

        int orderIdIndex=c.getColumnIndex("orderId");
        int contentIndex=c.getColumnIndex("content");
        int amountIndex=c.getColumnIndex("price");
        c.moveToFirst();
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
        MenuAdapter menuAdapter=new MenuAdapter(getActivity(),orderArray);
        liveOrdersList.setAdapter(menuAdapter);
        liveOrdersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Order Content");
                builder.setMessage(orderInfo.get(i));

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                if(userId==0){
                    builder.setNegativeButton("Mark as Delivered", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDatabase.execSQL("UPDATE `orders` SET `status`='n' WHERE `orderId`="+orderArray.get(i).getId());
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Order:"+orderArray.get(i).getId()+" Marked Delivered.", Toast.LENGTH_SHORT).show();
                            Intent intent=getActivity().getIntent();
                            startActivity(intent);
                        }
                    });
                }

                builder.show();

            }
        });
        return view;
    }
}
