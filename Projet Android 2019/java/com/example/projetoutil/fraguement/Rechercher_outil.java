package com.example.projetoutil.fraguement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoutil.Annonce;
import com.example.projetoutil.AnnonceView;
import com.example.projetoutil.DbHandler;
import com.example.projetoutil.ListAdapter;
import com.example.projetoutil.R;
import com.example.projetoutil.apresinscription;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Rechercher_outil extends Fragment {
    ImageButton googlemaps;
DbHandler BD;
List<Annonce> lstAnnonce = new ArrayList<Annonce>();
    public Rechercher_outil() {
        // Required empty public constructor
    }
    Bitmap[] images ;

    String[] version ;

    String[] versionNumber ;

    ListView lView;

    ListAdapter lAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      final   View view = inflater.inflate(R.layout.fragment_rechercher_outil, container, false);

         BD = new DbHandler(getContext());
         Bitmap image;

        String[] arraySpinner = new String[] {
                "Chicoutimi", "Jonquiere", "Alma", "La Baie", "Saint Felicien", "Arvida", "Lac kenogamie"
        };
        final    Spinner s = (Spinner)view.findViewById(R.id.lol);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);






        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                lstAnnonce = BD.Annonce_Par_Ville(s.getSelectedItem().toString());

                version = new String[lstAnnonce.size()];
                versionNumber=new String[lstAnnonce.size()];
                images= new Bitmap[lstAnnonce.size()];

                for(int cpt =0;cpt <lstAnnonce.size();cpt++)
                {
                    Bitmap bmp = ByteArrayToBitmap(lstAnnonce.get(cpt).getImage());

                    version [cpt]= lstAnnonce.get(cpt).getName();
                    Toast.makeText(getActivity(), version[cpt], Toast.LENGTH_SHORT).show();
                    versionNumber[cpt]= lstAnnonce.get(cpt).getTelephone();
                    images[cpt] = bmp;
                }

                lView = (ListView)view.findViewById(R.id.androidList);

                lAdapter = new ListAdapter(getContext(), version, versionNumber, images);

                lView.setAdapter(lAdapter);

                lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Toast.makeText(getContext(), version[i]+" "+versionNumber[i], Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), AnnonceView.class);
                        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
                        intent.putExtra("Name",lstAnnonce.get(i).getName());
                        intent.putExtra("Telephone",lstAnnonce.get(i).getTelephone());
                        intent.putExtra("Ville",lstAnnonce.get(i).getVille());
                        intent.putExtra("Description",lstAnnonce.get(i).getDescription());
                        intent.putExtra("Image",lstAnnonce.get(i).getImage());
                        startActivity(intent, options.toBundle());

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


return view;
    }
    public Bitmap ByteArrayToBitmap(byte[] byteArray)
    {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }
}

