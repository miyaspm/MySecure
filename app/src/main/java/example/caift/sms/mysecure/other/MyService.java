package example.caift.sms.mysecure.other;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        handler.postDelayed(this,5000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
        handler.removeCallbacks(this);
    }
    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            Log.w("Location", "From Service: " + location.getLatitude() + "," + location.getLongitude());
            String data=location.getLatitude() + "," + location.getLongitude();
            Toast.makeText(getBaseContext(),(String)data, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent("android.intent.action.LOCATION");
            intent.putExtra("latitude", location.getLatitude());
            intent.putExtra("longitude", location.getLongitude());

            //String[] myLoc = new String[] {location.getLatitude(), location.getLongitude()};
            sendBroadcast(intent);
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
        handler.postDelayed(this,1000);
    }


}
