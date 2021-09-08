package com.example.disenodeiu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MainActivity extends Activity{
    EditText nameE, lastnameE, ageE, addressE;
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