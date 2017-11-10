package com.chamodev.infoinputexample.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Koo on 2017. 11. 8..
 */

public class CommonUtils {

    private static boolean isAM(String time) {
        return Integer.parseInt(time.substring(0, 2)) < 12;
    }

    private static String get12HourTime(String rawTime) {
        int time = Integer.parseInt(rawTime);
        int hourOfDay = time / 100;
        int minute = time - hourOfDay * 100;
        int hour = hourOfDay == 0 ? 12 : (hourOfDay > 12 ? hourOfDay - 12 : hourOfDay);

        return String.format(" %s%d:%s%d", hour < 10 ? "0" : "", hour, minute < 10 ? "0" : "", minute);
    }

    public static String getFormattedTime(String time) {
        return isAM(time) ? "오전" + get12HourTime(time) : "오후" + get12HourTime(time);
    }

    public static int convertToPixelFromDP(Context context, int DP) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP,
                r.getDisplayMetrics());
        return (int) px;
    }

    public static void hideSoftKeyboard(Context context) {
        if (((Activity) context).getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    context.getSystemService(context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity) context)
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static String substituteUserName(String content){
        String userName = "정 휘인";
        if (userName.split(" ").length == 2){
            userName = userName.split(" ")[1];
        }

        return content.replace("{$이름}", userName);

    }

    public static String substituteUserName(Context context, int resourceId){
        String userName = "정 휘인";
        if (userName.split(" ").length == 2){
            userName = userName.split(" ")[1];
        }

        return context.getResources().getString(resourceId).replace("{$이름}", userName);

    }

}
