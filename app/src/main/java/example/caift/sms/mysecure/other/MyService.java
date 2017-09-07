package example.caift.sms.mysecure.other;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Filter;
import android.widget.Toast;

public class MyService extends Service implements LocationListener, Runnable {
    private LocationManager locationManager;
    private Handler handler = new Handler();
    private int i = 0;

    public VolumeBtn myBroadcast;

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30 * 1000, 0, this);
        handler.postDelayed(this, 30 * 1000);


        IntentFilter filter  = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
        myBroadcast =new VolumeBtn();
        getApplicationContext().registerReceiver(myBroadcast, filter);
        Toast.makeText(getBaseContext(),"MyService", Toast.LENGTH_SHORT).show();
    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        locationManager.removeUpdates(this);
//        handler.removeCallbacks(this);
//
//        unregisterReceiver(myBroadcast);
//    }







    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            Log.w("Location", "From Service: " + location.getLatitude() + "," + location.getLongitude());
            String data=location.getLatitude() + "," + location.getLongitude();
            Toast.makeText(getBaseContext(),(String)data, Toast.LENGTH_SHORT).show();
            String lat = Double.valueOf(location.getLatitude()).toString();
            String lng = Double.valueOf(location.getLongitude()).toString();

            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyServiceLocation", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("Latitude", lat);
            editor.putString("Longitude", lng);
            editor.commit();
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void run() {
        Log.w("Timer","From Service "+i);
        i = i+1;
        handler.postDelayed(this, 30 * 1000);
    }


}
