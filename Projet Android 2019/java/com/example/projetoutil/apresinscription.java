package com.example.projetoutil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class apresinscription extends AppCompatActivity {
DbHandler bd;
String email,password,name,firstname,country,image;
Button BTNimporter;
Button btnGO;
 Bitmap Picture;
 public static User currentuser;

    ImageView img;
    static final int RESULT_LOAD_IMG = 1;
    private static final int REQUEST_LOCATION = 123;
    GoogleMap mGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresinscription);
        getSupportActionBar().hide();
        bd = new DbHandler(this);
         img = (ImageView)findViewById(R.id.imageuti);
        BTNimporter =(Button) findViewById(R.id.btn_importer_image);
     final    EditText nom = (EditText) findViewById(R.id.input_name);
      final   EditText prenom = (EditText) findViewById(R.id.input_firstname);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,

                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,

                            Manifest.permission.ACCESS_COARSE_LOCATION,

                            Manifest.permission.BLUETOOTH,

                            Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_LOCATION);

        } else {

            System.out.println("Location permissions available, starting location");

            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

        }

btnGO= (Button)findViewById(R.id.btn_loginIns);

        final ArrayList<String> listItems = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, listItems );
        String[] isoCountryCodes = Locale.getISOCountries();
        for (String countryCode : isoCountryCodes) {
            Locale locale = new Locale("", countryCode);
            String countryName = locale.getDisplayCountry();
            adapter.add(countryName);
         bd.addUser("clement","bricout","skate","france","clement.brioout@orange.fr",null);
        }

        BTNimporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });


        btnGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                email=extras.getString("email");
                password=extras.getString("password");
                name= nom.getText().toString();
                firstname=prenom.getText().toString();

                Intent intent = new Intent(apresinscription.this,MainActivity.class );
                startActivity(intent);
              bd.addUser(name,firstname,password,email,"canada",getBytesFromBitmap(Picture));
              currentuser = bd.getCurrentUser(email);
                Toast.makeText(apresinscription.this, currentuser.email,
                        Toast.LENGTH_LONG).show();



            }
        });


    }

@Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {

                final Uri imageUri = data.getData();
                image= imageUri.getPath();
                final InputStream imageStream = this.getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Picture= selectedImage;
                img.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Une erreur s'est produite",Toast.LENGTH_LONG).show();

            }

        }else {
            Toast.makeText(this,"Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();

        }
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode == REQUEST_LOCATION) {

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                System.out.println("Location permissions granted, starting location");



            }

        }

    }





}
