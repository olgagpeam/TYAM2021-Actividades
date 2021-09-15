package com.example.disenodeiu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import androidx.annotation.Nullable;
public class SecondActivity extends Activity {
    EditText name;
    EditText lastname;
    EditText age;
    EditText  address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        recibirDatos();
    }

    private void recibirDatos(){
        Bundle extras= getIntent().getExtras();
        String nombre=extras.getString("name2");
        String apellidos=extras.getString("lastname2");
        String edad=extras.getString("age2");
        String direccion=extras.getString("address2");
        name = (EditText) findViewById(R.id.editname);
        name.setText(nombre);
        lastname = (EditText) findViewById(R.id.editlastname);
        lastname.setText(apellidos);
        age = (EditText) findViewById(R.id.editage);
        age.setText(edad);
        address = (EditText) findViewById(R.id.editaddress);
        address.setText(direccion);

    }
}