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

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
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
    long beginningTime = 0;
    int seconds = 0;
    long millis = 0;

    //public static boolean sIsAppWorkFinished=true;
    @Override
    public void onReceive(final Context context, Intent intent) {
        // beginningTime = System.currentTimeMillis();
        // long totalTimePressed = System.currentTimeMillis() - beginningTime;

        // Toast.makeText(context, "volum  Changed CHECK SMS"+beginningTime , Toast.LENGTH_SHORT).show();

        if (!clickedDown) {
            millis = System.currentTimeMillis() - beginningTime;
            seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if (beginningTime + 12 < seconds) {
                Toast.makeText(context, "volum  Changed CHECK SMS" + seconds, Toast.LENGTH_SHORT).show();
                String url = "tel:9750377439";
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                context.startActivity(dialIntent);

// Ok, the button has been clicked down for 2 seconds
            }

            seconds = 0;


        }
    }
}
