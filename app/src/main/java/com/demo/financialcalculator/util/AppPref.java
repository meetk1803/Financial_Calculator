package com.demo.financialcalculator.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPref {
    public static boolean IsTermsAccept(Context context) {
        return context.getApplicationContext().getSharedPreferences("userPref", 0).getBoolean("IS_TERMS_ACCEPT", false);
    }

    @SuppressLint({"ApplySharedPref"})
    public static void setIsTermsAccept(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences("userPref", 0).edit();
        edit.putBoolean("IS_TERMS_ACCEPT", z);
        edit.commit();
    }

    public static boolean IsRateUS(Context context) {
        return context.getApplicationContext().getSharedPreferences("userPref", 0).getBoolean("IS_RATEUS", false);
    }

    public static void setIsRateUS(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences("userPref", 0).edit();
        edit.putBoolean("IS_RATEUS", z);
        edit.commit();
    }
}
