package com.example.disenodeiu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;


public class MainActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);

        ImageView image = findViewById (R.id.imageU);
        TextView name = findViewById (R.id.textViewname);
        TextView lastname = findViewById (R.id.textViewlastname);
        TextView age = findViewById (R.id.textViewage);
        TextView address = findViewById (R.id.textViewaddress);
        EditText nameE = findViewById (R.id.editname);
        EditText lastnameE = findViewById (R.id.editlastname);
        EditText ageE = findViewById (R.id.editage);
        EditText addressE = findViewById (R.id.editaddress);

    }

}