package com.demo.financialcalculator.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.demo.financialcalculator.model.GraphModel;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.GraphUtils;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

public class PresentValueCalculatorActivity extends AppCompatActivity {
    Button btnCalculate;
    Button btnShare;
    EditText futureValue;
    GraphModel graphModel;
    ArrayList<GraphModel> graphModelArrayList;
    LinearLayout graphlayot;
    CardView llResult;
    AlertDialog mMyDialog;
    EditText periods;
    PieChart pieChart;
    EditText rate;
    ScrollView rootLayout;
    Toolbar toolBar;
    TextView txtPresentValue;
    TextView txtTotalInterest;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_present_value_calculator);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((int) R.string.present_value_calculator);
        setSupportActionBar(this.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    private void init() {
        this.graphModelArrayList = new ArrayList<>();
        this.futureValue = (EditText) findViewById(R.id.futureValue);
        this.periods = (EditText) findViewById(R.id.periods);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.rate = (EditText) findViewById(R.id.rate);
        this.pieChart = (PieChart) findViewById(R.id.piechart);
        this.txtPresentValue = (TextView) findViewById(R.id.txtPresentValue);
        this.txtTotalInterest = (TextView) findViewById(R.id.txtTotalInterest);
        this.graphlayot = (LinearLayout) findViewById(R.id.graphlayot);
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PresentValueCalculatorActivity.this.calculate();
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PresentValueCalculatorActivity.this.checkPermission();
            }
        });
    }

    @SuppressLint({"SetTextI18n"})
    public void calculate() {
        try {
            if (validationDetails()) {
                Double valueOf = Double.valueOf(Double.parseDouble(this.futureValue.getText().toString()));
                Double valueOf2 = Double.valueOf(valueOf.doubleValue() * (1.0d / Double.valueOf(Math.pow(Double.valueOf(Double.valueOf(Double.parseDouble(this.rate.getText().toString())).doubleValue() / 100.0d).doubleValue() + 1.0d, Double.valueOf(Double.parseDouble(this.periods.getText().toString())).doubleValue())).doubleValue()));
                TextView textView = this.txtPresentValue;
                textView.setText("$" + Utils.decimalFormat.format(valueOf2));
                TextView textView2 = this.txtTotalInterest;
                textView2.setText("$" + Utils.decimalFormat.format(valueOf.doubleValue() - valueOf2.doubleValue()));
                setPieChart(valueOf2.doubleValue(), valueOf.doubleValue() - valueOf2.doubleValue());
                AppConstant.hideKeyboard(this);
                AppConstant.visibleResult(this.llResult);
                AppConstant.visibleGraph(this.graphlayot);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid decimal value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean validationDetails() {
        if (!this.futureValue.getText().toString().isEmpty()) {
            double parseDouble = Double.parseDouble(this.futureValue.getText().toString());
            double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            if (parseDouble != d) {
                if (this.periods.getText().toString().isEmpty() || Double.parseDouble(this.periods.getText().toString()) == d) {
                    this.periods.setError("Please fill out this field");
                    return false;
                } else if (!this.rate.getText().toString().isEmpty() && Double.parseDouble(this.rate.getText().toString()) != d) {
                    return true;
                } else {
                    this.rate.setError("Please fill out this field");
                    return false;
                }
            }
        }
        this.futureValue.setError("Please fill out this field");
        return false;
    }

    private void setPieChart(double d, double d2) {
        ArrayList<GraphModel> arrayList = this.graphModelArrayList;
        if (arrayList != null && arrayList.size() > 0) {
            this.graphModelArrayList.clear();
        }
        GraphModel graphModel2 = new GraphModel(getResources().getString(R.string.present) + "\n(" + Utils.decimalFormat.format(d) + ")", d, getResources().getColor(R.color.graphcolor1));
        this.graphModel = graphModel2;
        this.graphModelArrayList.add(graphModel2);
        GraphModel graphModel3 = new GraphModel(getResources().getString(R.string.total_interest) + "\n(" + Utils.decimalFormat.format(d2) + ")", d2, getResources().getColor(R.color.graphcolor2));
        this.graphModel = graphModel3;
        this.graphModelArrayList.add(graphModel3);
        new GraphUtils(this.pieChart, this.graphModelArrayList, getApplicationContext()).setupPieData();
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.present_value_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.present_value_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.present_value_calculator));
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
                intent.setData(Uri.fromParts("package", PresentValueCalculatorActivity.this.getPackageName(), (String) null));
                PresentValueCalculatorActivity.this.startActivityForResult(intent, 112);
                PresentValueCalculatorActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }
}
