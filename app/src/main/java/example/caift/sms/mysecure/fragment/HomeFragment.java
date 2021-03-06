package example.caift.sms.mysecure.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import example.caift.sms.mysecure.R;
import example.caift.sms.mysecure.activity.AddNumbers;
import example.caift.sms.mysecure.activity.Login_activity;
import example.caift.sms.mysecure.other.MyService;
import mehdi.sakout.fancybuttons.FancyButton;


public class HomeFragment extends Fragment implements View.OnClickListener {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String SMSNumbers = "SMSNum";
    public static final String PrimaryNo = "Phoneno1";
    public static final String SecondaryNo = "Phoneno2";

    private FloatingActionButton fab;

    private String mParam1;
    private String mParam2;

    public String Lat1;
    public String Lng1;

    private Camera camera;
    private Camera.Parameters params;
    private boolean isFlashOn = false;

    private AudioManager audioManager;
    private Ringtone ringtone;
    boolean isRinging = false;


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {

    }



    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.i("Dummy1", "Dummmy");




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Button btn1 = (Button) rootView.findViewById(R.id.btn1);
        Button btn2 = (Button) rootView.findViewById(R.id.btn2);
        Button btn3 = (Button) rootView.findViewById(R.id.btn3);
        Button btn4 = (Button) rootView.findViewById(R.id.btn4);


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Regular.ttf");
        btn1.setTypeface(font);
        btn2.setTypeface(font);
        btn3.setTypeface(font);
        btn4.setTypeface(font);


        fab = (FloatingActionButton) rootView.findViewById(R.id.fabadd);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), AddNumbers.class);
                getActivity().startActivity(in);
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.ubercab");
                if (intent != null) {
                    // We found the activity now start the activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    // Bring user to the market or let them choose an app?
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "com.ubercab"));
                    startActivity(intent);
                }
            }
        });


        ImageButton mainbtn = (ImageButton) rootView.findViewById(R.id.MainBtn);

        SharedPreferences sharedPrefe = getActivity().getSharedPreferences(SMSNumbers, 0);
        final String no1 = (sharedPrefe.getString(PrimaryNo, ""));
        final String no2 = (sharedPrefe.getString(SecondaryNo, ""));

        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startService(new Intent(getActivity(),MyService.class));

                SharedPreferences prefs = getActivity().getSharedPreferences("MyServiceLocation", Context.MODE_PRIVATE);
                Lat1 = prefs.getString("Latitude", "");
                Lng1 = prefs.getString("Longitude", "");

                Log.i("Latitude", Lat1);
                Log.i("Longitude", Lng1);

                if (no1 == "" && no2 == ""){

                    Intent in = new Intent(getActivity(), AddNumbers.class);
                    getActivity().startActivity(in);
                    Log.i("if", no1);
                    Log.i("if", no2);

                }

                else{

                    String textmsg = " Here I am https://www.google.com/maps/@"+Lat1+","+Lng1+",19z";
                    String numbers[] = {"9388808013","7356622545"};
                    //String numbers[] = {no1, no2};

                    SmsManager sendsm = SmsManager.getDefault();
                    for(String number : numbers) {
                        sendsm.sendTextMessage(number, null, textmsg, null, null);
                        Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_LONG).show();
                        Log.i("else", number);
                    }

                    String phno1="9388808013";
                    Intent i = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phno1));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                    startActivity(i);

                    BlinkFlash();

                    //AlarmPlay();
                }


            }
        });

        btn1.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        return rootView;

        //return inflater.inflate(R.layout.fragment_home, container, false);

    }



////////////////// Start of Flash Light Blinking Code for Panic Button/////////////////
    private void BlinkFlash(){

        String myString = "0101010101010101010101010101";
        long blinkDelay = 100; //Delay in ms
        camera = Camera.open();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
        r.play();


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


//    private void AlarmPlay(){
//
//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
//        r.play();
//    }




    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        //String title = "";
       // Log.w("myapp", "onClick");
        switch (v.getId()){
            case R.id.btn1:
                fragment = new PhotosFragment();
                //title = "Current Location";
                replaceFragment(fragment);
                break;

            case R.id.btn3:
                fragment = new NotificationsFragment();
               // title = "HelpLine";
                replaceFragment(fragment);
                break;

            case R.id.btn4:
                fragment = new SettingsFragment();
                //title = "About Us";
                replaceFragment(fragment);
                break;




        }
    }

    public void replaceFragment(Fragment someFragment) {
        Log.w("myapp", "replaceFragment");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
       // getSupportActionBar().setTitle(title);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

         void onFragmentInteraction(Uri uri);
    }


}
