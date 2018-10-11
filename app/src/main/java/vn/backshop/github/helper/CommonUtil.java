package vn.backshop.github.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

public class CommonUtil {

    private static CommonUtil instance;
    public static CommonUtil getInstance() {
        if (instance == null) {
            synchronized (CommonUtil.class) {
                if (instance == null) {
                    instance = new CommonUtil();
                }
            }
        }
        return instance;
    }
    private CommonUtil() {

    }

    public void spannableString(Context context, AppCompatTextView textView, int color, final SpannableListener listener) {
        String sourceString = textView.getText().toString();
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(sourceString);
        if(null != listener) {
            String strTmp = listener.onClicked(false);
            int posTmp = sourceString.indexOf(strTmp);
            if (0 <= posTmp) {
                int eosTmp = posTmp + strTmp.length();
                spannable.setSpan(clickableSpan(ContextCompat.getColor(context, color), listener),
                        posTmp, eosTmp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(spannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    public interface SpannableListener{
        String onClicked(boolean isClicked);
    }
    private ClickableSpan clickableSpan(final int color, final SpannableListener listener) {
        return new ClickableSpan() {
            @Override
            public void onClick(final View view) {
                listener.onClicked(true);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                if(0 != color) {
                    ds.setColor(color);
                }
                //Remove default underline associated with spans
                //ds.setUnderlineText(false);
            }
        };
    }
}
