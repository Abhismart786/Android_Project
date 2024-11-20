package com.example.whatsapp;

import android.content.Intent;
import android.location.Address;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    EditText mEditText;
    Button mButton,mBackButton;
    MapView mMapView;
    GoogleMap mGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
        mEditText = findViewById(R.id.address);
        mButton = findViewById(R.id.getaddress);
        mMapView = findViewById(R.id.mapview);
        mMapView.getMapAsync(this);
        mMapView.onCreate(savedInstanceState);
        mBackButton = findViewById(R.id.getback);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use Intent to navigate back to MainActivity
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
                // Optionally finish MapActivity so it doesn't stay on the back stack
                finish();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = mEditText.getText().toString();
                if(!address.isEmpty())
                {
                    getAddressLocation(address);
                }
                else {
                    mEditText.setError("Please enter address");
                }
            }



        });
    }
    private void getAddressLocation(String address )
    {
        Geocoder geocoder = new Geocoder(this);
        try{
            List<Address> addresses = geocoder.getFromLocationName(address,1);
            if(addresses!=null && !addresses.isEmpty())
            {
                Address location = addresses.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                mGoogleMap.clear();

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("Your Location"));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}