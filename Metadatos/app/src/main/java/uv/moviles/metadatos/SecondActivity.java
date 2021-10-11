package uv.moviles.metadatos;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class SecondActivity extends Activity
{
    public static final int REQUEST_CAMERA_OPEN = 4001;
    public static final int REQUEST_PERMISSION_CAMERA = 3001;
    TextView Exif;
    String Filename = "";

    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_second);
        iv = findViewById (R.id.ivSource);
        Button button = findViewById (R.id.btnSave);


        int perm = checkSelfPermission (Manifest.permission.CAMERA);

        if (perm != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION_CAMERA
            );
            return;
        }

        abrirCamara();

        button.setOnClickListener (v ->
        {
            Bitmap bitmap = getBitmapFromDrawable (iv.getDrawable ());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                saveImage (bitmap);
                //Exif.setText(ReadExif(filea));
            }
            else
            {
                String imageDir = Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES).toString ();
                File file = new File(imageDir, "/mypic.jpg");
                try
                {
                    OutputStream fos = new FileOutputStream(file);
                    bitmap.compress (Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close ();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace ();
                }
            }
        });
    }

    void abrirCamara ()
    {
        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult (intent, REQUEST_CAMERA_OPEN);
    }

    private Bitmap getBitmapFromDrawable (Drawable drble)
    {
        if (drble instanceof BitmapDrawable)
        {
            return  ((BitmapDrawable) drble).getBitmap ();
        }
        Bitmap bitmap = Bitmap.createBitmap (drble.getIntrinsicWidth (), drble.getIntrinsicHeight (), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drble.setBounds (0, 0, canvas.getWidth (), canvas.getHeight ());
        drble.draw (canvas);
        return bitmap;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void saveImage (Bitmap bitmap)
    {
        Filename ="myOtherPic.jpg";
        ContentResolver resolver = getContentResolver ();
        ContentValues values = new ContentValues ();
        values.put (MediaStore.MediaColumns.DISPLAY_NAME, Filename);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            values.put (MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            values.put (MediaStore.MediaColumns.IS_PENDING, true);
        }
        else
        {
            String pictureDirectory = String.format ("%s/%s", Environment.getExternalStorageDirectory (), Environment.DIRECTORY_PICTURES);
            values.put (MediaStore.MediaColumns.DATA, pictureDirectory);


        }
        Uri targetUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            targetUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else
        {
            targetUri = MediaStore.Files.getContentUri ("external");
        }
        Uri imageUri =  resolver.insert (targetUri, values);
        try
        {
            OutputStream fos = resolver.openOutputStream (imageUri);
            bitmap.compress (Bitmap.CompressFormat.JPEG,100, fos);
            fos.flush ();
            fos.close ();
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q)
            {
                values = new ContentValues ();
                values.put (MediaStore.Images.ImageColumns.IS_PENDING, false);
                resolver.update (imageUri, values, null, null);

            }
            showExif(imageUri);

        }
        catch (Exception ex)
        {
            ex.printStackTrace ();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA)
        {
            if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED)
            {
                abrirCamara ();
            }
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_OPEN && resultCode == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap (bitmap);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void showExif(Uri photoUri){
        if(photoUri != null){
            try {
                InputStream inputStream = getContentResolver().openInputStream(photoUri);
                ExifInterface exifInterface = new ExifInterface(inputStream);

                String exif="Exif: ";
                exif += "\nIMAGE_LENGTH: " +
                        exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
                exif += "\nIMAGE_WIDTH: " +
                        exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
                exif += "\n DATETIME: " +
                        exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                exif += "\n TAG_MAKE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_MAKE);
                exif += "\n TAG_MODEL: " +
                        exifInterface.getAttribute(ExifInterface.TAG_MODEL);
                exif += "\n TAG_ORIENTATION: " +
                        exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
                exif += "\n TAG_WHITE_BALANCE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
                exif += "\n TAG_FOCAL_LENGTH: " +
                        exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
                exif += "\n TAG_FLASH: " +
                        exifInterface.getAttribute(ExifInterface.TAG_FLASH);
                exif += "\nGPS related:";
                exif += "\n TAG_GPS_DATESTAMP: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
                exif += "\n TAG_GPS_TIMESTAMP: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
                exif += "\n TAG_GPS_LATITUDE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                exif += "\n TAG_GPS_LATITUDE_REF: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                exif += "\n TAG_GPS_LONGITUDE: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                exif += "\n TAG_GPS_LONGITUDE_REF: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                exif += "\n TAG_GPS_PROCESSING_METHOD: " +
                        exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);

                inputStream.close();

                Toast.makeText(getApplicationContext(),
                        exif,
                        Toast.LENGTH_LONG).show();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getApplicationContext(),
                    "photoUri == null",
                    Toast.LENGTH_LONG).show();
        }
    };

}

