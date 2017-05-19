package example.caift.sms.mysecure.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by CAIFT on 21/04/17.
 */

public class Raleway_Regular extends AppCompatTextView {

    public Raleway_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Raleway_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Raleway_Regular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Regular.ttf");
            setTypeface(tf);
        }
    }
}
