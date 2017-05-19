package example.caift.sms.mysecure.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
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

    public String MY_LAT = "";
    public String MY_LONG = "";



    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double lat = intent.getDoubleExtra("latitude", 0);
            double lng = intent.getDoubleExtra("longitude", 0);
            Log.i("BROADCAST", String.valueOf(lat).concat(String.valueOf(lng)));
        }
    };


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

        Intent intent = new Intent(getActivity(), MyService.class);




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

                if (no1 == "" && no2 == ""){

                    Intent in = new Intent(getActivity(), AddNumbers.class);
                    getActivity().startActivity(in);
                    Log.i("if", no1);
                    Log.i("if", no2);

                }

                else{

//                    String textmsg = "https://www.google.com/maps/@"+var1+","+var2+",19z";
//                    String number = "9388808013";
//
//                    SmsManager sendsm = SmsManager.getDefault();
//                    sendsm.sendTextMessage(number, null, textmsg, null,null);
//                    Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_LONG).show();
//                    Log.i("else", number);

                }


            }
        });






        btn1.setOnClickListener(this);

        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);


        return rootView;

        //return inflater.inflate(R.layout.fragment_home, container, false);

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
