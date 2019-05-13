package com.example.projetoutil.fraguement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetoutil.MainActivity;
import com.example.projetoutil.R;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class Louer_mon_outil extends Fragment {


     ImageView selectedImg;
Bitmap Picture;
    Button btnImport ;
    Button btnSuivant;
    public static String telephones,Nomoutils,Ville;
    public static Bitmap imageOutils;
    static final int RESULT_LOAD_IMG = 1;
    public Louer_mon_outil() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_louer_mon_outil, container, false);
        btnImport = (Button) view.findViewById(R.id.button_Importer);
        btnSuivant = (Button) view.findViewById(R.id.button_Suivant);
        final EditText Nomoutil = (EditText) view.findViewById(R.id.input_outil_name);
final EditText telephone = (EditText)view.findViewById(R.id.input_telephone);
        selectedImg = (ImageView) view.findViewById(R.id.imageView2);

 final EditText addresse = (EditText) view.findViewById(R.id.input_outil_adresse);
 final EditText code = (EditText) view.findViewById(R.id.input_outil_code);




        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Ville= addresse.getText().toString() + " " + code.getText().toString();



                telephones= telephone.getText().toString();
                imageOutils = Picture;
                Nomoutils= Nomoutil.getText().toString();
                Louer_mon_outil myFrag = new Louer_mon_outil();
                myFrag.setArguments(bundle);
                FragmentManager frag = getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.conteneurfraguement,myFrag).addToBackStack(null).commit();

                CrgarFragmento(new Louer_mon_outil_2());
            }
        });


        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
        return  view;
    }



    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Picture = selectedImage;
                selectedImg.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Une erreur s'est produite",Toast.LENGTH_LONG).show();

            }

        }else {
            Toast.makeText(getActivity(),"Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();

        }
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