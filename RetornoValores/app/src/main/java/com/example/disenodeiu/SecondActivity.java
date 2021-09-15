package com.example.disenodeiu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;

public class SecondActivity extends Activity {
    EditText name;
    EditText lastname;
    EditText age;
    EditText  address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_second);
        ImageView txtimage = findViewById(R.id.imageU);
        TextView txtname = findViewById(R.id.textViewname);
        TextView txtlastname = findViewById(R.id.textViewlastname);
        TextView txtage = findViewById(R.id.textViewage);
        TextView txtaddress = findViewById(R.id.textViewaddress);
        TextView result = findViewById(R.id.text_view_result);
        recibirDatos();

        Button buttonOpenActivity1 = findViewById(R.id.button_open_activity1);
        buttonOpenActivity1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra("name1", name.getText().toString());
                intent.putExtra("lastname1", lastname.getText().toString());
                startActivityForResult(intent, 1);
            }

        });
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
