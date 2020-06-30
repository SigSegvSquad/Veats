package com.example.veats;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class UserSettingsFragment extends Fragment {

    String[] settingsOptions={"Change Password","Order History","Logout"};
    ArrayList<String> settings=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        View view =  inflater.inflate(R.layout.activity_user_settings_fragment,
                container, false);
        SharedPreferences sp=getActivity().getSharedPreferences("com.example.veats", Context.MODE_PRIVATE);
        int userId=sp.getInt("userId",-1);

        settings.add("Change Password");
        settings.add("Order History");
        if(userId==0){
            settings.add("Edit Menu");
            settings.add("Add Balance");
        }
        settings.add("Logout");

        ListView settingsList=view.findViewById(R.id.settingsList);
        ArrayAdapter<String> settingsArrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,settings);
        settingsList.setAdapter(settingsArrayAdapter);

        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(settings.get(i).equals("Logout")){
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else if(settings.get(i).equals("Order History")){
                    Intent intent=new Intent(getActivity(),PastOrders.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
