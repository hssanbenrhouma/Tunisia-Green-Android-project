package com.example.dell.tunisiagreen;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class DeclarationConsultActivity extends FragmentActivity implements OnMapReadyCallback {
    String Descrip="";
    private GoogleMap mMap;
    int id;
    Double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_consult);
        Intent intent = getIntent();

        if (intent != null) {
            id=intent.getIntExtra("id",0);
            Descrip=intent.getStringExtra("Description");
             longitude=intent.getDoubleExtra("long",0);
             latitude=intent.getDoubleExtra("latit",0);}
        TextView description=(TextView)findViewById(R.id.desc);
        description.setText(Descrip);
        ImageView im=(ImageView)findViewById(R.id.imageViewConsult);
        try {

            Picasso.with(getApplicationContext()).load("http://androidhssan.net16.net/fichiersphp/images/" + Integer.toString(id) + ".jpg").into(im);        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        }
        catch (IllegalArgumentException e)
        {
            Toast.makeText(getApplicationContext(),"Photo loading problem !!!",Toast.LENGTH_SHORT).show();

        }
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(longitude, latitude);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(sydney);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(100);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }
}
