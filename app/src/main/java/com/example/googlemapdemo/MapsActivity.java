package com.example.googlemapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.googlemapdemo.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    Button btnEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //if click end session button go back to the main activity
        btnEnd =findViewById(R.id.btnEndSession);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        LatLng UOM = new LatLng(6.795127683263096, 79.90085769594994);
        //mMap.addMarker(new MarkerOptions().position(UOM).title("Marker in UOM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UOM));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18)); // to set zoom up to see the building when open google map
        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                Toast.makeText(getApplicationContext(), "Map clicked [" + latLng.latitude +
                        " / " + latLng.longitude + "]", Toast.LENGTH_LONG).show();
                LatLng serverPoint = new LatLng(latLng.latitude, latLng.longitude);
                // pin the location when user click the any location in the map
                mMap.addMarker(new MarkerOptions().position(serverPoint).title("Server Point"));
                // to send tatitude and longitude value to serverpoint recording activity
                Intent intent = new Intent(getApplicationContext(), ServerPointRecordingActivity.class);
                intent.putExtra("latitude", latLng.latitude);
                intent.putExtra("longitude", latLng.longitude);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}