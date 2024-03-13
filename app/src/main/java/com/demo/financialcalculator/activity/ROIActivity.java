package com.demo.financialcalculator.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;

public class ROIActivity extends AppCompatActivity {
    Button btnCalculate;
    Button btnShare;
    EditText etInvest;
    EditText etReturn;
    CardView llResult;
    AlertDialog mMyDialog;
    ScrollView rootLayout;
    Toolbar toolBar;
    TextView txtRoi;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_roi);
        init();
        setUpToolbar();
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((int) R.string.return_on_investment_calculator);
        setSupportActionBar(this.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    private void init() {
        this.etInvest = (EditText) findViewById(R.id.etInvest);
        this.etReturn = (EditText) findViewById(R.id.etReturn);
        this.txtRoi = (TextView) findViewById(R.id.txtRoi);
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ROIActivity.this.calculate();
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ROIActivity.this.checkPermission();
            }
        });
    }

    public void calculate() {
        try {
            if (validationDetails()) {
                double parseDouble = Double.parseDouble(this.etInvest.getText().toString());
                double parseDouble2 = Double.parseDouble(this.etReturn.getText().toString());
                double d = parseDouble2 - parseDouble;
                TextView textView = this.txtRoi;
                Object[] objArr = new Object[1];
                objArr[0] = Utils.decimalFormat.format(parseDouble2 > parseDouble ? (d / parseDouble) * 100.0d : (100.0d * d) / parseDouble);
                textView.setText(String.format("%s%%", objArr));
                AppConstant.hideKeyboard(this);
                AppConstant.visibleResult(this.llResult);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid decimal value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean validationDetails() {
        if (!this.etInvest.getText().toString().isEmpty()) {
            double doubleValue = Double.valueOf(this.etInvest.getText().toString()).doubleValue();
            double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            if (doubleValue != d) {
                if (!this.etReturn.getText().toString().isEmpty() && Double.valueOf(this.etReturn.getText().toString()).doubleValue() != d) {
                    return true;
                }
                this.etReturn.setError("Please fill out this field");
                return false;
            }
        }
        this.etInvest.setError("Please fill out this field");
        return false;
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.return_on_investment_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.return_on_investment_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.return_on_investment_calculator));
                return;
            }
            int length = strArr.length;
            int i2 = 0;
            while (i2 < length) {
                String str = strArr[i2];
                if (iArr[i2] != -1 || (Build.VERSION.SDK_INT >= 23 && shouldShowRequestPermissionRationale(str))) {
                    i2++;
                } else {
                    show_alert();
                    return;
                }
            }
        }
    }

    public void show_alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((CharSequence) "This application requires permission. Please ensure that this is enabled in settings, then press the back button to continue ");
        builder.setCancelable(false);
        builder.setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", ROIActivity.this.getPackageName(), (String) null));
                ROIActivity.this.startActivityForResult(intent, 112);
                ROIActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }
}
