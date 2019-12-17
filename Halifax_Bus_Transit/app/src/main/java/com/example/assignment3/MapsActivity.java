package com.example.assignment3;

//importing all the required libraries
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.location.Location;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;

//MapActivity class in which the current location and realtime location of buses logic is present
public class MapsActivity extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,OnMapReadyCallback {

    public final int MY_LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    //list to store the buses information
    List<FeedEntity> busData = new ArrayList<>();
    Marker currMarker;

    //list to store all the markers to be displayed
    List<Marker> markerList = new ArrayList<Marker>();
    Button bus_selector;

    String route_num="";

    //variable for dialogue box while loading the buses
    ProgressDialog loading_info;

    //variable to check if the app is loading first time
    boolean check = true;

    Vehicle bmarker;
    CameraPosition campos;
    ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor();

    //start of oncreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        bus_selector=findViewById(R.id.filter_btn);
        //getting the input bus number from user
        route_num = getIntent().getStringExtra("bus");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);

        //redirecting to FilterActivity screen to receive the input bus number from user after clicking "filter the buses" button
        bus_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopping the existing thread while redirecting to new screen

                Intent intent=new Intent(getApplicationContext(), FilterActivity.class);
                startActivity(intent);
            }
        });

        // loading markers on rotation of device
        if (savedInstanceState != null) {
            bmarker = savedInstanceState.getParcelable("bmarker");
        }
    }


    //start of onMapReady method
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        // Before enabling the My Location layer, request location permission from the user.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            Toast.makeText(this, "please provide the permission to access location", Toast.LENGTH_LONG).show();
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        // restoring camera position before app termination
        SharedPreferences session = getSharedPreferences("campos", 0);
        float lat = session.getFloat("lat", 0);
        float lon = session.getFloat("lon", 0);
        float zoomLevel = session.getFloat("zoom", 0);
        if (lon != 0.0 && lat != 0.0) {
            LatLng startPosition = new LatLng((double) lat, (double) lon);
            campos = new CameraPosition.Builder()
                    .target(startPosition).zoom(zoomLevel).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(campos));
        }

        // adding markers to map on screen rotation
        if (bmarker != null) {
            List<Vehicle> vehicles = bmarker.getBusMarkers();
            for (Vehicle v : vehicles) {
                String vehicleID = v.getVehicleID();
                double latitude = v.getLatitude();
                double longitude = v.getLongitude();

                LatLng busPosition = new LatLng(latitude, longitude);
                currMarker = mMap.addMarker(new MarkerOptions().position(busPosition)
                        .title(vehicleID));
                currMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus3));
            }
        }

        //creating the dialogue box with a message
        if (check && bmarker == null) {
            loading_info = ProgressDialog.show(MapsActivity.this, "Loading","please wait for a while..");
            loading_info.show();
            check = false;
        }

        //killing existing thread and running new one
        executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("bus", "new thread");

                    //fetching the realtime buses information from halifax transit
                    URL url = new URL("http://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb");
                    FeedMessage feed = FeedMessage.parseFrom(url.openStream());

                    //storing all the buses information in a list
                    busData = feed.getEntityList();

                    runOnUiThread(new Runnable() {
                     @Override
                     public void run() {

                         //to close the dialogue box message
                         if (loading_info != null) {
                             loading_info.dismiss();
                         }
                         //calling changemarker method to update the markers of buses in UI
                        changeMarker(busData,route_num);
                     }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        //executing the thread for every 10 seconds
        executor.scheduleAtFixedRate(thread, 0, 10, TimeUnit.SECONDS);

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    //changeMarker method in which the marker positions of buses will be updated
    private void changeMarker(List<FeedEntity> busData, String s){
        //clearing all the old markers
        markerList.clear();
        mMap.clear();
        List<Vehicle> vehicles = new ArrayList<>();

        //if the filter buses button is clicked and user has given some input
           if (route_num != null)
           {
               //if the user input is "all" display all the buses in maps
               if((route_num.toLowerCase()).equals("all"))
               {
                   for (FeedEntity entity : busData) {
                       String vehicleID = entity.getVehicle().getTrip().getRouteId();
                       String delay =  entity.getTripUpdate().getDelay()+"";
                       String tripid=entity.getVehicleOrBuilder().getTripOrBuilder().getTripId()+"";
                       float latitude = entity.getVehicle().getPosition().getLatitude();
                       float longitude = entity.getVehicle().getPosition().getLongitude();
                       vehicles.add(new Vehicle(vehicleID, latitude, longitude));

                       LatLng busPosition = new LatLng(latitude, longitude);
                       currMarker = mMap.addMarker(new MarkerOptions().position(busPosition)
                               .title(vehicleID)
                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus3))
                               .snippet("Trip: "+tripid+", Delay: "+delay)
                               .flat(true));
                       markerList.add(currMarker);

                       bmarker = new Vehicle(vehicles);
                   }
               }
               //if the user input is not "all"
               else
               {
                   int count=0;
                   //filtering the markers based on user provided bus number
                   for (FeedEntity entity : busData) {
                       String vehicleID = entity.getVehicle().getTrip().getRouteId();
                        if ((route_num.toLowerCase()).equals(vehicleID.toLowerCase()))
                        {
                             count++;
                             String delay =  entity.getTripUpdate().getDelay()+"";
                             String tripid=entity.getVehicleOrBuilder().getTripOrBuilder().getTripId()+"";
                             float latitude = entity.getVehicle().getPosition().getLatitude();
                             float longitude = entity.getVehicle().getPosition().getLongitude();
                             LatLng busPosition = new LatLng(latitude, longitude);
                            currMarker = mMap.addMarker(new MarkerOptions().position(busPosition)
                                    .title(vehicleID)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus3))
                                    .snippet("Trip: "+tripid+", Delay: "+delay)
                                    .flat(true));
                             markerList.add(currMarker);
                        }
                   }

                   //if the bus number provided by user is not present in the realtime data, displaying a message
                   if(count==0){
                       Toast.makeText(this, "bus numbered: "+route_num+" not found", Toast.LENGTH_LONG).show();
                   }
               }
           }

           //if the filter buses button is not clicked, route_num will be null so all the buses will be displayed in UI
           else {
               for (FeedEntity entity : busData) {
                   String delay =  entity.getTripUpdate().getDelay()+"";
                   String tripid=entity.getVehicleOrBuilder().getTripOrBuilder().getTripId()+"";
                   String vehicleID = entity.getVehicle().getTrip().getRouteId();
                   float latitude = entity.getVehicle().getPosition().getLatitude();
                   float longitude = entity.getVehicle().getPosition().getLongitude();
                   LatLng busPosition = new LatLng(latitude, longitude);
                   currMarker = mMap.addMarker(new MarkerOptions().position(busPosition)
                           .title(vehicleID)
                           .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus3))
                           .snippet("Trip: "+tripid+", Delay: "+delay)
                           .flat(true));
                   markerList.add(currMarker);
           }}
    }

    //getting the user permission for accessing the location of user
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    // Show rationale and request permission.
                }
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    // saving markers to reuse them on screen rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("bmarker", bmarker);
    }

    // to store camera position on application pause
    @Override
    protected void onPause() {
        super.onPause();
        if (mMap != null) {
            campos = mMap.getCameraPosition();
        }
    }

    // to restore camera position on application resume
    @Override
    protected void onResume() {
        super.onResume();
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        if (campos != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(campos));
            campos = null;
        }
    }

    // to store camera position on application termination
    @Override
    protected void onStop() {
        super.onStop();

        campos = mMap.getCameraPosition();
        double latitude = campos.target.latitude;
        double longitude = campos.target.longitude;
        float zoomLevel = campos.zoom;

        SharedPreferences session = getSharedPreferences("campos", 0);
        SharedPreferences.Editor editor = session.edit();
        editor.putFloat("lon", (float) longitude);
        editor.putFloat("lat", (float) latitude);
        editor.putFloat("zoom", zoomLevel);
        editor.commit();
    }

}
