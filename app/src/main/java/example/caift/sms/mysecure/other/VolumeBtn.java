package example.caift.sms.mysecure.other;

/**
 * Created by CAIFT on 03/06/17.
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class VolumeBtn extends BroadcastReceiver {

    public static boolean sIsReceived; // this is made true and false after each timer clock
    public static Timer sTimer = null;
    public static int i;
    final int MAX_ITERATION = 15;
    boolean clickedDown = false;
    long lastPress = 0;
    int seconds = 0;
    long millis = 0;
    int beginningTime = 0;
    public static boolean sIsAppWorkFinished = true;



    private Camera camera;
    private Camera.Parameters params;
    private boolean isFlashOn = false;

    public String Lat1;
    public String Lng1;





    @Override
    public void onReceive(final Context context, Intent intent) {

        Toast.makeText(context, "On Receive" + i, Toast.LENGTH_LONG).show();

        sIsReceived = true; // Make this true whenever isReceived called

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sIsReceived) {  // if its true it means user is still pressing the button
                    i++;

                }
                if(i>=80) {
                    Toast.makeText(context, "Volume Changed CHECK SMS" + i, Toast.LENGTH_SHORT).show();

                    BlinkFlash();

                    SharedPreferences prefs = context.getSharedPreferences("MyServiceLocation", Context.MODE_PRIVATE);
                    Lat1 = prefs.getString("Latitude", "");
                    Lng1 = prefs.getString("Longitude", "");

                    Log.i("Latitude", Lat1);
                    Log.i("Longitude", Lng1);

                    String textmsg = " Here I am https://www.google.com/maps/@"+Lat1+","+Lng1+",19z";
                    String numbers[] = {"9388808013","7356622545"};
                    //String numbers[] = {no1, no2};

                    SmsManager sendsm = SmsManager.getDefault();
                    for(String number : numbers) {
                        sendsm.sendTextMessage(number, null, textmsg, null, null);
                        Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show();
                        Log.i("else", number);
                    }

                    String url = "tel:9388808013";
                    Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                    dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    context.startActivity(dialIntent);
                    i=0;
                    Toast.makeText(context, "Volume Changed SMS" + i, Toast.LENGTH_SHORT).show();
                }

                sIsReceived=false;

            }
        }, 2000);
    }


    ////////////////// Start of Flash Light Blinking Code for Panic Button/////////////////
    private void BlinkFlash(){


        String myString = "0101010101010101010101010101";
        long blinkDelay = 100; //Delay in ms
        camera = Camera.open();

        for (int i = 0; i < myString.length(); i++) {



            if (myString.charAt(i) == '0') {
                params = camera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();
                isFlashOn = true;



            } else {
                params = camera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(params);
                camera.stopPreview();
                isFlashOn = false;

            }
            try {
                Thread.sleep(blinkDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

////////////////// End of Flash Light Blinking Code for Panic Button/////////////////






}
