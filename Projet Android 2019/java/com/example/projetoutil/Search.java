package com.example.projetoutil;

import android.app.ActivityOptions;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.projetoutil.apresinscription.currentuser;

public class Search extends Fragment {
    DbHandler BD;
    List<Annonce> lstAnnonce = new ArrayList<Annonce>();

    Bitmap[] images ;

    String[] version ;

    String[] versionNumber ;

    ListView lView;

    ListAdapter lAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final   View view = inflater.inflate(R.layout.fragment_search, container, false);

        BD = new DbHandler(getContext());
        Bitmap image;
final SearchView searchView= (SearchView)view.findViewById(R.id.lol2);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                lstAnnonce = BD.Annonce_Search(searchView.getQuery().toString());

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

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                callSearch(newText);
//              }
                return true;
            }

            public void callSearch(String query) {
                //Do searching
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

