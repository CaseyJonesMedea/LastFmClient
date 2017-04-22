package by.viachaslau.kukhto.llikelastfm.Others.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;



import by.viachaslau.kukhto.llikelastfm.Others.SingletonFonts;
import by.viachaslau.kukhto.llikelastfm.R;

/**
 * Created by kuhto on 29.12.2016.
 */

public class CustomFontsTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomFontsTextView(Context context) {
        super(context);
    }

    public CustomFontsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void setFonts(AttributeSet attributeSet, Context context){
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.CustomFontsTextView,
                0, 0);
        a.recycle();
        int fonts = a.getInt(R.styleable.CustomFontsTextView_fonts,-1);
        SingletonFonts singletonFonts = SingletonFonts.getInstance(context);
        switch (fonts){
            case (0):
                setTypeface(singletonFonts.getFont1());
                break;
            case (1):
                setTypeface(singletonFonts.getFont2());
                break;
            case (2):
                setTypeface(singletonFonts.getFont3());
                break;
        }
    }

}