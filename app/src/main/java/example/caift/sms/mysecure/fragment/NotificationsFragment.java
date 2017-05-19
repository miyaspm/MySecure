package example.caift.sms.mysecure.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import example.caift.sms.mysecure.R;
import example.caift.sms.mysecure.activity.home_activity;


public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NotificationsFragment() {

    }


    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button btn1  = (Button) rootView.findViewById(R.id.emergency_btn);
        Button btn2  = (Button) rootView.findViewById(R.id.police_btn);
        Button btn3  = (Button) rootView.findViewById(R.id.women_btn);
        Button btn4  = (Button) rootView.findViewById(R.id.child_btn);
        Button btn5  = (Button) rootView.findViewById(R.id.traffic_btn);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);


        return rootView;
    }




    public void onClick(View v) {

        switch (v.getId()){
            case R.id.emergency_btn:
                String phno1="112";

                Intent i = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phno1));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(i);
                break;

            case R.id.police_btn:
                String phno2="100";

                Intent j = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phno2));
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(j);
                break;

            case R.id.women_btn:
                String phno3="181";

                Intent k = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phno3));
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(k);
                break;

            case R.id.child_btn:
                String phno4="1098";

                Intent l = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phno4));
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(l);
                break;

            case R.id.traffic_btn:
                String phno5="103";

                Intent m = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phno5));
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(m);
                break;




        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onResume() {
        super.onResume();
        ((home_activity) getActivity()).setActionBarTitle("Helpline");
    }
}
