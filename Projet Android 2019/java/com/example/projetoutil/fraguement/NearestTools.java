package com.example.projetoutil.fraguement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.projetoutil.BlankFragment.latit;
import static com.example.projetoutil.BlankFragment.longu;

public class NearestTools extends Fragment {
    ImageButton googlemaps;
    DbHandler BD;
    List<Annonce> lstAnnonce = new ArrayList<Annonce>();

    public NearestTools() {
        // Required empty public constructor
    }
    Bitmap[] images ;

    String[] version ;

    String[] versionNumber ;

    ListView lView;

    int cpt1;
    ListAdapter lAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final   View view = inflater.inflate(R.layout.fragment_rechercher_outil, container, false);

        BD = new DbHandler(getContext());
        Bitmap image;

        String[] arraySpinner = new String[] {
                "5 KM", "10 KM", "20 KM", "50 KM"
        };
        final    Spinner s = (Spinner)view.findViewById(R.id.lol);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);






        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                lView = (ListView)view.findViewById(R.id.androidList);
            lstAnnonce = BD.Annonce_Nears(50,latit,longu,getContext());

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


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // haversine great circle distance approximation, returns meters
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60; // 60 nautical miles per degree of seperation
        dist = dist * 1852; // 1852 meters per nautical mile
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
