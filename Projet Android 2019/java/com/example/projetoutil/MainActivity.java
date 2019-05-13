package com.example.projetoutil;

import android.Manifest;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.projetoutil.fraguement.Compte;
import com.example.projetoutil.fraguement.Contact;
import com.example.projetoutil.fraguement.FAQ;
import com.example.projetoutil.fraguement.Louer_mon_outil;
import com.example.projetoutil.fraguement.Rechercher_outil;
import com.example.projetoutil.fraguement.mes_annonce;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.projetoutil.apresinscription.currentuser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AnimationDrawable animationDrawable, annimationav;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");


        animationDrawable = (AnimationDrawable) toolbar.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //View navigationView = layoutInflater.inflate(R.layout.nav_header_main, null);
        navigationView.setNavigationItemSelectedListener(this);
        Bitmap bmp = BitmapFactory.decodeByteArray(currentuser.image, 0, currentuser.image.length);
        // ((ImageView)navigationView.findViewById(R.id.imageViewMenu)).setImageBitmap(bmp);
        ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageViewMenu)).setImageBitmap(bmp);
        // headerView = (NavigationView) navigationView.getHeaderView(0);
        ImageView image = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageViewMenu);
        image.setImageBitmap(bmp);
        ImageButton search =(ImageButton)findViewById(R.id.action_loop);
        annimationav = (AnimationDrawable) navigationView.getHeaderView(0).getBackground();
        annimationav.setEnterFadeDuration(4500);
        annimationav.setExitFadeDuration(4500);
        annimationav.start();
        // ImageView imgvw = (ImageView)headerView.findViewById(R.id.imageViewMenu);
        // ((TextView)navigationView.findViewById(R.id.textView_Email_Current_User)).setText(currentuser.email);


        //  imgvw.setImageBitmap(bmp);

        TextView tv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView_Email_Current_User);
        TextView nm = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textview_current_name);
        tv.setText(currentuser.email);
        nm.setText(currentuser.name);
        CrgarFragmento(new com.example.projetoutil.BlankFragment());


         search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

CrgarFragmento(new Search());

            }
        });
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            CrgarFragmento(new BlankFragment());
        } else if (id == R.id.nav_Loueroutil) {

           CrgarFragmento(new Rechercher_outil());

        } else if (id == R.id.nav_louermonoutil) {
            CrgarFragmento(new Louer_mon_outil());
        } else if (id == R.id.nav_Compte) {
         CrgarFragmento(new Compte());
        } else if (id == R.id.nav_share) {
          CrgarFragmento(new FAQ());
        } else if (id == R.id.Nous_Joindre) {
         CrgarFragmento(new Contact());
        }
        else if (id == R.id.nav_Mes_annonce) {
            CrgarFragmento(new mes_annonce());
        }
        else if (id==R.id.Deconnexion)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   public  void CrgarFragmento(Fragment fraguement){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.conteneurfraguement, fraguement).commit();
    }


}
