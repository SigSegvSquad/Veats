package com.example.veats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    View view;
    ArrayList<MenuData> menuArray = new ArrayList<MenuData>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_logged_in, container, false);
        ListView menuList = view.findViewById(R.id.menu);


        menuArray.add(new MenuData("Vadapav", 12, 1));
        menuArray.add(new MenuData("Sandwich", 45, 2));
        menuArray.add(new MenuData("Samosa", 12, 3));
        menuArray.add(new MenuData("Schezwan Noodles", 60, 4));
        menuArray.add(new MenuData("Uttappa", 64, 5));
        menuArray.add(new MenuData("Masala Dosa", 45, 6));
        menuArray.add(new MenuData("Maggi", 20, 7));
        menuArray.add(new MenuData("Noodles", 60, 8));
        menuArray.add(new MenuData("Rice Plate", 70, 9));


        MenuAdapter myAdapter = new MenuAdapter(getActivity(), menuArray);
        menuList.setAdapter(myAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent itemQty = new Intent(getActivity(), ItemQuantityActivity.class);
                itemQty.putExtra("itemName", menuArray.get(i).getItem());
                itemQty.putExtra("itemPrice", menuArray.get(i).getPrice());
                itemQty.putExtra("itemId", menuArray.get(i).getId());
                startActivity(itemQty);
            }
        });

        return view;
    }
}


