package tw.edu.pu.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements SearchFragment.onButtonPressListener{

    //Final Value
    final String forest_Url = "https://api.openweathermap.org/data/2.5/forecast?";
    final String weather_Url = "https://api.openweathermap.org/data/2.5/weather?";
    final String key_API = "&appid=e9bead3ce6662fb07b5f377564f90b93";
    final int REQUEST_CODE = 101;
    final long MIN_TIME = 5000;
    final float MAX_DISTANCE =1000;

    //Constructor
    int checkI = 0;
    String lat, lon, searchCity;

    //gpsSetting
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    String Location_Provider = LocationManager.GPS_PROVIDER;

    //NavSetting
    NavHostFragment navHostFragment;
    NavController navController;

    //MySettings
    LoadingDialog loadingDialog;
    FavNewAdapter favNewAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Loading DialogProgressBar
        loadingDialog = new LoadingDialog(MainActivity.this);
        loadingDialog.startLoadingDialog();

        //findFragmentID
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NavHostFragments);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        //findViewID
        BottomNavigationView btmNav = findViewById(R.id.btmNavigationView);

        //NavigationSetup
        NavigationUI.setupWithNavController(btmNav, navController);

        //gpsGetWeather
        getWeatherFromGPS();
    }

    private void getWeatherFromGPS() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(@NonNull Location location) {
                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                lat = "lat=" + Latitude;
                lon = "&lon=" + Longitude + "&cnt=25";

                VolleyApi_GPS volleyApi1 = new VolleyApi_GPS(forest_Url + lat + lon + key_API, MainActivity.this);
                volleyApi1.getWeatherGPS();

                VolleyApi_GPS volleyApi = new VolleyApi_GPS(weather_Url + lat + lon + key_API, MainActivity.this);
                volleyApi.volleyWeatherRT_GPS();

                checkI ++;
                if (checkI != 0)
                    loadingDialog.dismissDialog();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        }

        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MAX_DISTANCE, mLocationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeatherFromGPS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Get!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "If need to use GPS, Please go to setting turn it on.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onOKButtonPressed() {
        ShareViewModel_Search ss = new ViewModelProvider(MainActivity.this).get(ShareViewModel_Search.class);
        ss.getIoCity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                searchCity = s;
            }
        });
    }
}