package com.demo.financialcalculator.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.AppPref;

public class Disclosure extends AppCompatActivity implements View.OnClickListener {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_disclosure);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.agreeAndContinue) {
            AppPref.setIsTermsAccept(this, true);
            goToMainScreen();
        } else if (id == R.id.privacyPolicy) {
            AppConstant.openUrl(this, AppConstant.PRIVACY_POLICY_URL);
        } else if (id == R.id.termsOfService) {
            AppConstant.openUrl(this, AppConstant.TERMS_OF_SERVICE_URL);
        } else if (id == R.id.userAgreement) {
            agreeAndContinueDialog();
        }
    }

    public void agreeAndContinueDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(AppConstant.DISCLOSURE_DIALOG_DESC);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    private void goToMainScreen() {
        try {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
