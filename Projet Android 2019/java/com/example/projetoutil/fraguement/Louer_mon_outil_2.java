package com.example.projetoutil.fraguement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projetoutil.BlankFragment;
import com.example.projetoutil.DbHandler;
import com.example.projetoutil.R;

import java.io.ByteArrayOutputStream;

import static com.example.projetoutil.apresinscription.currentuser;
import static com.example.projetoutil.fraguement.Louer_mon_outil.Nomoutils;
import static com.example.projetoutil.fraguement.Louer_mon_outil.Ville;
import static com.example.projetoutil.fraguement.Louer_mon_outil.imageOutils;
import static com.example.projetoutil.fraguement.Louer_mon_outil.telephones;


public class Louer_mon_outil_2 extends Fragment {
    Bundle bundles;
    //
DbHandler BD ;
    public Louer_mon_outil_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_louer_mon_outil_2, container, false);
        BD = new DbHandler(getContext());
        Button btnDeposer = (Button) view.findViewById(R.id.Deposerbtn);
        final ImageView imgOuti2 = (ImageView) view.findViewById(R.id.imageView3);
        imgOuti2.setImageBitmap(imageOutils);


        final EditText Description = (EditText)view.findViewById(R.id.Description);


        btnDeposer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               BD.addAnnonce(Nomoutils,Ville,Description.getText().toString(),telephones,getBytesFromBitmap(imageOutils),currentuser.getId());
               CrgarFragmento(new BlankFragment());
            }
        });

return view;
    }
    public  void CrgarFragmento(Fragment fraguement){
        FragmentManager manager = getFragmentManager() ;
        manager.beginTransaction().replace(R.id.conteneurfraguement, fraguement).commit();
    }




    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }


}
