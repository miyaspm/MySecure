package example.caift.sms.mysecure.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;


public class Raleway_Bold extends AppCompatTextView {

    public Raleway_Bold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Raleway_Bold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Raleway_Bold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Bold.ttf");
            setTypeface(tf);
        }
    }

}