package example.caift.sms.mysecure.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextView_Lato extends AppCompatTextView {

    public TextView_Lato(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView_Lato(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView_Lato(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Medium.ttf");
            setTypeface(tf);
        }
    }

}