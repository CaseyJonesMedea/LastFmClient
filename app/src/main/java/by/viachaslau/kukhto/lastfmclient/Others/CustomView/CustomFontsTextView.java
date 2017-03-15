package by.viachaslau.kukhto.lastfmclient.Others.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import by.viachaslau.kukhto.lastfmclient.Others.SingletonFonts;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 29.12.2016.
 */

public class CustomFontsTextView extends TextView {
    public CustomFontsTextView(Context context) {
        super(context);
    }

    public CustomFontsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomFontsTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setFonts(AttributeSet attributeSet, Context context){
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.CustomFontsTextView,
                0, 0);
        a.recycle();
        int fonts = a.getInt(R.styleable.CustomFontsTextView_fonts,-1);
        SingletonFonts singltonFonts = SingletonFonts.getInstance(context);
        switch (fonts){
            case (0):
                setTypeface(singltonFonts.getFont1());
                break;
            case (1):
                setTypeface(singltonFonts.getFont2());
                break;
            case (2):
                setTypeface(singltonFonts.getFont3());
                break;
        }
    }

}