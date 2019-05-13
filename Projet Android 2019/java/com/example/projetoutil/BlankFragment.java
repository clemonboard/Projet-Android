package com.example.projetoutil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.projetoutil.fraguement.NearestTools;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;

public class BlankFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    GoogleMap mGoogleMap;
    View Mview;
    DbHandler BD;
    List<Annonce> lstAnnonce = new ArrayList<Annonce>();
    public  static  double longu;
    public  static  double latit;
    GoogleMap.InfoWindowAdapter rr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)          {
        Mview  = inflater.inflate(R.layout.fragment_blank, container, false);





        FloatingActionButton near = (FloatingActionButton) Mview.findViewById(R.id.floatmenu);

        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragManager = getActivity().getSupportFragmentManager();
               fragManager.beginTransaction().replace(R.id.conteneurfraguement, new NearestTools()).commit();


            }
        });



        BD = new DbHandler(getContext());
        return Mview;
    }
    @Override
    public void  onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        mMapView =(MapView) Mview.findViewById(R.id.map);
        if(mMapView!=null)
        {
mMapView.onCreate(null);
mMapView.onResume();
mMapView.getMapAsync(this);
        }

    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
MapsInitializer.initialize(getContext());
mGoogleMap=googleMap;
googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // fetch last location if any from provider - GPS.
        final LocationManager locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (loc == null) {

            final LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {

                    // getting location of user
                    final double latitude = location.getLatitude();
                    latit=latitude;
                    final double longitude = location.getLongitude();
                    longu=longitude;
                    mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(40,50)).title("salue").snippet("salutt"));
                    CameraPosition Liberty = CameraPosition.builder().target(new LatLng(latitude,longitude)).zoom(13).bearing(0).tilt(45).build();
                    mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
                    // do something with Latlng
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                    // do something
                }

                @Override
                public void onProviderDisabled(String provider) {
                    // notify user "GPS or Network provider" not available
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 500, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);
        }
        else {
            // do something with last know location
        }


          lstAnnonce= BD.Annonce_ALL();
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.gloupiilogo);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

          for (int cpt = 0; cpt < lstAnnonce.size();cpt++) {

              if (Geocoder.isPresent()) {
                  try {
                      String location = lstAnnonce.get(cpt).ville + " Quebec";
                      Geocoder gc = new Geocoder(getContext());
                      List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects

                      List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                      for (Address a : addresses) {
                          if (a.hasLatitude() && a.hasLongitude()) {
                              ll.add(new LatLng(a.getLatitude(), a.getLongitude()));




                              MarkerOptions markerOpt = new MarkerOptions();
                              markerOpt.position(new LatLng(a.getLatitude(), a.getLongitude()))
                                      .title(lstAnnonce.get(cpt).name)
                                      .snippet(lstAnnonce.get(cpt).Description)
                                      .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                              InfoWindoData info = new InfoWindoData();
                              info.setImage(lstAnnonce.get(cpt).image);
                              info.setTelephone("Telephone : "+lstAnnonce.get(cpt).telephone);
                              info.setDescription("Description : " +lstAnnonce.get(cpt).Description);


                              CustomInfoWindowAdapter customInfoWindow = new CustomInfoWindowAdapter(getActivity());
                              mGoogleMap.setInfoWindowAdapter(customInfoWindow);

                              Marker m = mGoogleMap.addMarker(markerOpt);
                              m.setTag(info);






                          }
                      }
                  } catch (IOException e) {
                      // handle the exception
                  }
              }
          }


    }



    public View getInfoWindow(Marker marker) {
        return null; // Use default info window background
    }



}