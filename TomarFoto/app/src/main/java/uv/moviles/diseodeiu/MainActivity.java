package uv.moviles.diseodeiu;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MainActivity extends Activity{
    EditText nameE, lastnameE, ageE, addressE;
    TextView result;
    public static final int REQUEST_CODE_CAMERA = 1001;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);


        TextView name = findViewById (R.id.textViewname);
        TextView lastname = findViewById (R.id.textViewlastname);
        TextView age = findViewById (R.id.textViewage);
        TextView address = findViewById (R.id.textViewaddress);
        result = findViewById(R.id.text_view_result);
        nameE = findViewById (R.id.editname);
        lastnameE = findViewById (R.id.editlastname);
        ageE = findViewById (R.id.editage);
        addressE = findViewById (R.id.editaddress);

        Button buttonOpenActivity2 = findViewById(R.id.button_open_activity2);
        buttonOpenActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("name2", nameE.getText().toString());//pasa los datos a otra actividad
                intent.putExtra("lastname2",lastnameE.getText().toString());
                intent.putExtra("age2",ageE.getText().toString());
                intent.putExtra("address2",addressE.getText().toString());
                startActivityForResult(intent, 1);
            }
        });
        Button btnphoto = findViewById (R.id.button_photo);

        imageView =(ImageView) findViewById(R.id.imageU);;

        btnphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate if user has grant permission of call to the app
                int permission = checkSelfPermission (Manifest.permission.CAMERA);
                if (permission != PackageManager.PERMISSION_GRANTED) { // if not, request it
                    requestPermissions (new String [] { Manifest.permission.CAMERA },
                            REQUEST_CODE_CAMERA);
                    return;
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//validar requestcode para evitar errores
        Bitmap bitmap=(Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editN", nameE.getText().toString());
        outState.putString("editL", lastnameE.getText().toString());
        outState.putString("editA", ageE.getText().toString());
        outState.putString("editAd", addressE.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
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

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String results = data.getStringExtra("result");
                result.setText("" + results);
            }
            if (resultCode == RESULT_CANCELED) {
                result.setText("Nothing selected");
            }
        }
    }*/



}