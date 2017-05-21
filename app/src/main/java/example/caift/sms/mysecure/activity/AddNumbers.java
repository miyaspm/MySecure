package example.caift.sms.mysecure.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.caift.sms.mysecure.R;

public class AddNumbers extends AppCompatActivity implements View.OnClickListener {


    public static final String SMSNumbers = "SMSNum";
    public static final String PrimaryNo = "Phoneno1";
    public static final String SecondaryNo = "Phoneno2";
    public static final String PrimaryName = "Primaryname";
    public static final String SecondaryName = "Secondaryname";
    private static final int CONTACT_PICKER_RESULT1 = 1001;
    private static final int CONTACT_PICKER_RESULT2 = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_numbers);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //changing status bar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.homeRight));
        }

        //Action Bar Gradient Color
        ActionBar bar = getSupportActionBar();
        Drawable gradient = getResources().getDrawable(R.drawable.action_bar_bg);
        bar.setBackgroundDrawable(gradient);


        // Hide Title in the Action Bar
        //ActionBar actionBar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(true);
            bar.setDisplayShowHomeEnabled(false);
            bar.setDisplayHomeAsUpEnabled(true);

        }


        TextView phone1 = (TextView) findViewById(R.id.number1);
        TextView phone2 = (TextView) findViewById(R.id.number2);
        SharedPreferences shared = getSharedPreferences(SMSNumbers, MODE_PRIVATE);

        String name1 = (shared.getString(PrimaryName, ""));
        String name2 = (shared.getString(SecondaryName, ""));

        phone1.setText(name1);
        phone2.setText(name2);




        ImageButton btn1 = (ImageButton) findViewById(R.id.add_btn1);
        ImageButton btn2 = (ImageButton) findViewById(R.id.add_btn2);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.add_btn1) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT1);
            Log.i("Button", "Button111111111");
        } else if (v.getId() == R.id.add_btn2) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT2);
            Log.i("Button", "Button222222222");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT1:
                    final TextView phoneInput = (TextView) findViewById(R.id.number1);

                    Cursor cur = null;
                    ContentResolver cr = getContentResolver();
                    try {
                        // getData() method will have the Content Uri of the selected contact
                        Uri uri = data.getData();
                        //Query the content uri
                        cur = cr.query(uri, null, null, null, null);
                        cur.moveToFirst();
                        // column index of the contact ID
                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        // column index of the contact name
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                        phoneInput.setText(name);        //print data


                        // column index of the phone number
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phone = pCur.getString(
                                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));




                            SharedPreferences storenum = getSharedPreferences(SMSNumbers, Context.MODE_PRIVATE);
                            String no1 = phone.toString();
                            SharedPreferences.Editor editor = storenum.edit();
                            editor.putString(PrimaryNo, no1);
                            editor.putString(PrimaryName, name);
                            editor.commit();
                            Toast.makeText(AddNumbers.this, "Successfully Inserted Number 1", Toast.LENGTH_LONG).show();
                            Log.i("ActivityResult1", no1);
                        }
                        pCur.close();
                        // column index of the email
                        Cursor emailCur = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCur.moveToNext()) {
                            // This would allow you get several email addresses
                            // if the email addresses were stored in an array
                            String email = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));

                        }
                        emailCur.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


                case CONTACT_PICKER_RESULT2:
                    final TextView phoneInput2 = (TextView) findViewById(R.id.number2);


                    //Cursor cur = null;
                    ContentResolver cr1 = getContentResolver();
                    try {
                        // getData() method will have the Content Uri of the selected contact
                        Uri uri = data.getData();
                        //Query the content uri
                        cur = cr1.query(uri, null, null, null, null);
                        cur.moveToFirst();
                        // column index of the contact ID
                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        // column index of the contact name
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        phoneInput2.setText(name);        //print data
                        // column index of the phone number
                        Cursor pCur = cr1.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phone = pCur.getString(
                                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));




                            SharedPreferences storenum = getSharedPreferences(SMSNumbers, Context.MODE_PRIVATE);
                            String no2 = phone.toString();
                            SharedPreferences.Editor editor = storenum.edit();
                            editor.putString(SecondaryNo, no2);
                            editor.putString(SecondaryName, name);
                            editor.commit();
                            Toast.makeText(AddNumbers.this, "Successfully Inserted Number 2", Toast.LENGTH_LONG).show();
                            Log.i("ActivityResult2", no2);
                        }
                        pCur.close();
                        // column index of the email
                        Cursor emailCur = cr1.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCur.moveToNext()) {
                            // This would allow you get several email addresses
                            // if the email addresses were stored in an array
                            String email = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));

                        }
                        emailCur.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
        } else {

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, home_activity.class);
        startActivity(intent);
    }
}
