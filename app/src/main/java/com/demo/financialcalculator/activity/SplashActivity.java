package com.demo.financialcalculator.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.demo.financialcalculator.R;
import com.demo.financialcalculator.util.AppPref;

public class SplashActivity extends AppCompatActivity {
    @SuppressLint("RestrictedApi")
    private static int SPLASH_TIME_OUT = PathInterpolatorCompat.MAX_NUM_POINTS;
    TextView versiontxt;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_splash);
        TextView textView = (TextView) findViewById(R.id.versiontxt);
        this.versiontxt = textView;
        textView.setText(getVersion(getApplicationContext()));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (AppPref.IsTermsAccept(SplashActivity.this)) {
                    SplashActivity splashActivity = SplashActivity.this;
                    splashActivity.startActivity(new Intent(splashActivity, MainActivity.class));
                } else {
                    SplashActivity splashActivity2 = SplashActivity.this;
                    splashActivity2.startActivity(new Intent(splashActivity2, Disclosure.class));
                }
                SplashActivity.this.finish();
            }
        }, (long) SPLASH_TIME_OUT);
    }

    public static String getVersion(Context context) {
        String str = "";
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.valueOf("Version " + str);
    }
}
