package com.example.veats;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageActivity extends AppCompatActivity {
    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Fragment loggedIn = new MenuFragment();
        loadFragment(loggedIn);

        navBar = findViewById(R.id.navigation);
        navBar.setOnNavigationItemSelectedListener(selectedListener);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment liveOrdersFragment = new LiveOrdersFragment();
            Fragment loggedIn = new MenuFragment();
            Fragment userSettingsFragment = new UserSettingsFragment();
            switch (item.getItemId()) {
                case R.id.orderNow:
                    liveOrdersFragment.onPause();
                    loadFragment(loggedIn);
                    return true;
                case R.id.myOrders:
                    loadFragment(liveOrdersFragment);
                    return true;
                case R.id.userSettings:
                    loadFragment(userSettingsFragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        return currentFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartItems:
                Intent intent = new Intent(MainPageActivity.this, CartActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

