package com.example.disenodeiu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MainActivity extends Activity{
    EditText nameE, lastnameE, ageE, addressE;
/*
    public static String nameS = "";
    public static String lastnameS = "";
    public static String ageS = "";
    public static String addressS = "";
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);

        ImageView image = findViewById (R.id.imageU);
        TextView name = findViewById (R.id.textViewname);
        TextView lastname = findViewById (R.id.textViewlastname);
        TextView age = findViewById (R.id.textViewage);
        TextView address = findViewById (R.id.textViewaddress);
        nameE = findViewById (R.id.editname);
        lastnameE = findViewById (R.id.editlastname);
        ageE = findViewById (R.id.editage);
        addressE = findViewById (R.id.editaddress);

        Button buttonOpenActivity2 = findViewById(R.id.button_open_activity2);
        buttonOpenActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("name2", nameE.getText().toString());
                    intent.putExtra("lastname2",lastnameE.getText().toString());
                    intent.putExtra("age2",ageE.getText().toString());
                    intent.putExtra("address2",addressE.getText().toString());
                    startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editN", nameE.getText().toString());
        outState.putString("editL", lastnameE.getText().toString());
        outState.putString("editA", ageE.getText().toString());
        outState.putString("editAd", addressE.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String name = savedInstanceState.getString("editN");
        nameE.setText (name);

        String lastname = savedInstanceState.getString("editL");
        nameE.setText (lastname);

        String age = savedInstanceState.getString("editA");
        nameE.setText (age);
        
        String address = savedInstanceState.getString("editAd");
        nameE.setText (address);
    }

}