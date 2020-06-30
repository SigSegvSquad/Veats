package com.example.veats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button loginBTN;
    EditText username;
    EditText password;

    public void login() {
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("veats", MODE_PRIVATE, null);
        username = findViewById(R.id.name);
        password = findViewById(R.id.password);
        Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);
        int userIdIndex = c.getColumnIndex("userId");
        int nameIndex = c.getColumnIndex("name");
        int passwordIndex = c.getColumnIndex("password");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(nameIndex).toString().equals(username.getText().toString())) {
                if (c.getString(passwordIndex).equals(password.getText().toString())) {
                    int userId = Integer.parseInt(c.getString(userIdIndex));
                    SharedPreferences sp = getSharedPreferences("com.example.veats", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("userId", userId).commit();
                    Toast.makeText(this, "Hello " + c.getString(nameIndex), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                    intent.putExtra("name", c.getString(nameIndex));
                    startActivity(intent);
                    break;
                } else {
                    Toast.makeText(this, "Wrong Combination of Email & Password", Toast.LENGTH_SHORT).show();
                    break;
                }
            } else if (c.isLast()) {
                Toast.makeText(this, "Wrong Combination of Email & Password", Toast.LENGTH_SHORT).show();
            }
            c.moveToNext();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBTN = findViewById(R.id.LoginBTN);
        setContentView(R.layout.activity_main);
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("veats", MODE_PRIVATE, null);

        //The below two lines were used to enter values in the database without a registration page
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (userId INT,name VARCHAR, password VARCHAR,balance INT)");

        //myDatabase.execSQL("INSERT INTO users(userId,name,password,balance) VALUES(0,'Admin','abc123',10000)");
        //myDatabase.execSQL("INSERT INTO users(userId,name,password,balance) VALUES(1,'Prabhav','abc123',1000)");
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }


}
