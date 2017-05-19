package example.caift.sms.mysecure.other;

/**
 * Created by CAIFT on 19/05/17.
 */

public interface IGPSActivity {

    public void locationChanged(double longitude, double latitude);
    public void displayGPSSettingsDialog();
}
