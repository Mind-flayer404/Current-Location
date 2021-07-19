package com.example.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //  Initialize variables
    Button btnLocation;
    TextView textView1,textView2,textView3,textView4,textView5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Assign variables
        btnLocation = findViewById(R.id.btn_loc);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);

        //  Initialize fusedLocationProvider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Checking Permission
                if(ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    //  Permission Granted
                    getLocation();
                }
                else{
                    //  Permission Denied
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }
    private void getLocation(){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //  Initialize location
                Location location = task.getResult();
                if(location != null){

                    try {
                        //  Initialize geocoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                        //  Initialize address
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude()
                        ,location.getLongitude(),1);

                        //  Set Latitude on text view
                        textView1.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Latitude :</b><br></font>"
                                + addresses.get(0).getLatitude()
                        ));

                        //  Set Longitude on text view
                        textView2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longitude :</b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));

                        //  Set Country on text view
                        textView3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country :</b><br></font>"
                                        + addresses.get(0).getCountryName()
                        ));

                        //  Set Locality on text view
                        textView4.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Locality :</b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));

                        //  Set Address on text view
                        textView5.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address :</b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}