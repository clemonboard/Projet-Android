package com.example.projetoutil.fraguement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetoutil.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;
import static com.example.projetoutil.apresinscription.currentuser;

public class Compte extends Fragment {

    public Compte() {
        // Required empty public constructor
    }

String image;
    ImageView imagecompte;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_compte, container, false);


       imagecompte = (ImageView)view.findViewById(R.id.imageView4);





        return  view;
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                 Bitmap Picture;
                final Uri imageUri = data.getData();
                image= imageUri.getPath();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Picture= selectedImage;
                imagecompte.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();


            }

        }else {


        }
    }

}

