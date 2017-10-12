package com.example.user.lookup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.lookup.Adapter.PlacesArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener,
        GoogleApiClient.OnConnectionFailedListener ,
        GoogleApiClient.ConnectionCallbacks,
        Button.OnClickListener{

    Intent intent;
    GoogleApiClient googleApiClient;
    AutoCompleteTextView autocompleteAddress;

    String lat_,lng_;
    Location current_location;
    TextView locationstatus;

    // Autocomplete address variable
    static final String LOG_TAG = "MainActivity";
    PlacesArrayAdapter mPlaceArrayAdapter;
    static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    double lat, lon;
    LatLng loc;

    // end Auto-complete variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LookUp!");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button lookup = (Button) findViewById(R.id.lookuphere);
        locationstatus = (TextView) findViewById(R.id.locationstatus);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.bounce);
        lookup.setVisibility(View.INVISIBLE);
        lookup.startAnimation(anim);

        autocompleteAddress = (AutoCompleteTextView) findViewById(R.id.autocomplete);

        this.googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        this.googleApiClient.connect();

        autocompleteAddress.setThreshold(2);
        autocompleteAddress.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceArrayAdapter = new PlacesArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);

        mPlaceArrayAdapter.setGoogleApiClient(googleApiClient);

        autocompleteAddress.setAdapter(mPlaceArrayAdapter);

        lookup.setOnClickListener(this);

        // Display device location status
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            locationstatus.setText("Location : OFF");

        }else{

            locationstatus.setText("Location : ON");

        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "Location permission access is denied for this application. " +
                    "Go to device permission setting and allow " +
                            "this application to access your device location permission.",
                    Toast.LENGTH_SHORT).show();
        }else{

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    locationstatus.setText("Location : ON");
                    Toast.makeText(MainActivity.this, "Location Turned ON", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProviderDisabled(String provider) {
                    locationstatus.setText("Location : OFF");
                    Toast.makeText(MainActivity.this, "Location Turned OFF", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlacesArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);

            Log.i(LOG_TAG, "Selected: " + item.description);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(googleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    public ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {

            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }

            // Selecting the first object buffer.
            final Place place = places.get(0);

            //distance calculation purpose
            loc = place.getLatLng();

            lat = loc.latitude;
            lon = loc.longitude;

        }
    };

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            intent = new Intent(this, MainActivity.class);

        }else if (id == R.id.nav_search) {

            intent = new Intent(this, SearchActivity.class);

        }else if (id == R.id.nav_around_you){

            intent = new Intent(this, LookAroundActivity.class);
            intent.putExtra("INTERACTIONCENTER","Around Me");

        }else if(id == R.id.nav_about){

            intent = new Intent(this, AboutActivity.class);

        }
//        else if (id == R.id.nav_fare) {
//
//            intent = new Intent(this, FareActivity.class);
//
//        }

        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

        Intent interaction = new Intent(this, InteractionActivity.class);
        interaction.putExtra("INTERACTIONPOINT",lat+","+lon);
        this.startActivity(interaction);

    }
}
