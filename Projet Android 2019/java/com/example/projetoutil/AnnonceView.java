package com.example.projetoutil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

public class AnnonceView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blank_fragment2);

        getSupportActionBar().hide();
        TextView Nom = (TextView)findViewById(R.id.textView7);
        TextView Telephone = (TextView)findViewById(R.id.textView9);
        TextView Ville = (TextView)findViewById(R.id.textView8);
        TextView Description = (TextView)findViewById(R.id.textView);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();

        Nom.setText(intent.getStringExtra("Name"));
        Telephone.setText("TELEPHONE : "+intent.getStringExtra("Telephone"));
        Ville.setText("ADRESSE : "+intent.getStringExtra("Ville"));
        Description.setText(intent.getStringExtra("Description"));
        image.setImageBitmap(ByteArrayToBitmap(intent.getByteArrayExtra("Image")));
    }

    public Bitmap ByteArrayToBitmap(byte[] byteArray)
    {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }
}
