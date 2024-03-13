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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.financialcalculator.Interface.RemoveEditText;
import com.demo.financialcalculator.R;
import com.demo.financialcalculator.adapter.RecyclerAdapter;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.ShareUtil;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class NPVCalculatorActivity extends AppCompatActivity implements RemoveEditText {
    RecyclerAdapter adapter;
    Button btnAddYear;
    Button btnCalculate;
    Button btnShare;
    ArrayList<String> doubleArrayList;
    EditText etDiscountRate;
    EditText etInitialInvestment;
    CardView llResult;
    AlertDialog mMyDialog;
    RecyclerView recyclerView;
    ScrollView rootLayout;
    Toolbar toolBar;
    TextView txtNpv;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_npv_calculator);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
        setRecycler();
    }

    private void setRecycler() {
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, this.doubleArrayList, this);
        this.adapter = recyclerAdapter;
        this.recyclerView.setAdapter(recyclerAdapter);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((int) R.string.net_present_value_calculator);
        setSupportActionBar(this.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    private void init() {
        ArrayList<String> arrayList = new ArrayList<>();
        this.doubleArrayList = arrayList;
        arrayList.add("");
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.txtNpv = (TextView) findViewById(R.id.txtNpv);
        this.etInitialInvestment = (EditText) findViewById(R.id.etInitialInvestment);
        this.etDiscountRate = (EditText) findViewById(R.id.etDiscountRate);
        this.btnAddYear = (Button) findViewById(R.id.btnAddYear);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.btnAddYear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NPVCalculatorActivity.this.doubleArrayList.add("");
                NPVCalculatorActivity.this.adapter.notifyDataSetChanged();
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NPVCalculatorActivity.this.calculate();
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NPVCalculatorActivity.this.checkPermission();
            }
        });
    }

    public void removePosition(int i) {
        this.doubleArrayList.remove(i);
        this.adapter.notifyDataSetChanged();
    }




    @SuppressLint({"SetTextI18n"})
    public void calculate() {
        double d;
        double d2;
        double d3;
        NPVCalculatorActivity nPVCalculatorActivity = this;
        double d4 = Utils.DOUBLE_EPSILON;
        try {
            d = Double.parseDouble(nPVCalculatorActivity.etInitialInvestment.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            d = Utils.DOUBLE_EPSILON;
        }

        try {
            d2 = Double.parseDouble(nPVCalculatorActivity.etDiscountRate.getText().toString());
        } catch (Exception e2) {
            e2.printStackTrace();
            d2 = Utils.DOUBLE_EPSILON;
        }

        double d52 = d2 / 100.0d;
        double d6 = 0.0d;
        double d42 = d4;
        int i = 0;

        while (true) {
            try {
                if (i >= nPVCalculatorActivity.doubleArrayList.size()) {
                    i = -1;
                    break;
                }

                try {
                    d3 = Double.valueOf(nPVCalculatorActivity.doubleArrayList.get(i)).doubleValue();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    d3 = d42;
                }

                if (d3 <= d42) {
                    break;
                }

                i++;
                double d7 = d;
                d6 += d3 / Math.pow(d52 + 1.0d, (double) i);
                d42 = Utils.DOUBLE_EPSILON;
                nPVCalculatorActivity = this;
                d = d7;
            } catch (Exception e4) {
                e4.printStackTrace();
                // Declare e4 here if you want to access it outside the catch block
                break;
            }
        }

        if (i == -1) {
            Double valueOf = Double.valueOf(d6 - d);
            nPVCalculatorActivity.txtNpv.setText("$" + com.demo.financialcalculator.util.Utils.decimalFormat.format(valueOf));
        } else {
            Toast.makeText(nPVCalculatorActivity, "Year  " + (i + 1) + "  value cannot be zero!", Toast.LENGTH_SHORT).show();
        }

        AppConstant.hideKeyboard(this);
        AppConstant.visibleResult(nPVCalculatorActivity.llResult);
    }

//    @SuppressLint({"SetTextI18n"})
//    public void calculate() {
//        double d;
//        double d2;
//        double d3;
//        NPVCalculatorActivity nPVCalculatorActivity = this;
//        double d4 = Utils.DOUBLE_EPSILON;
//        try {
//            d = Double.parseDouble(nPVCalculatorActivity.etInitialInvestment.getText().toString());
//        } catch (Exception e) {
//            try {
//                e.printStackTrace();
//                d = Utils.DOUBLE_EPSILON;
//            } catch (Exception e2) {
//                e4 = e2;
//                e4.printStackTrace();
//                double d5 = d4;
//                return;
//            }
//        }
//        try {
//            d2 = Double.parseDouble(nPVCalculatorActivity.etDiscountRate.getText().toString());
//        } catch (Exception e22) {
//            e22.printStackTrace();
//            d2 = Utils.DOUBLE_EPSILON;
//        }
//        double d52 = d2 / 100.0d;
//        double d6 = 0.0d;
//        double d42 = d4;
//        int i = 0;
//        while (true) {
//            try {
//                if (i >= nPVCalculatorActivity.doubleArrayList.size()) {
//                    i = -1;
//                    break;
//                }
//                try {
//                    d3 = Double.valueOf(nPVCalculatorActivity.doubleArrayList.get(i)).doubleValue();
//                } catch (Exception e3) {
//                    e3.printStackTrace();
//                    d3 = d42;
//                }
//                if (d3 <= d42) {
//                    break;
//                }
//                i++;
//                double d7 = d;
//                d6 += d3 / Math.pow(d52 + 1.0d, (double) i);
//                d42 = Utils.DOUBLE_EPSILON;
//                nPVCalculatorActivity = this;
//                d = d7;
//            } catch (Exception e4) {
//                e4 = e4;
//                d4 = d42;
//                e4.printStackTrace();
//                double d53 = d4;
//                return;
//            }
//        }
//        if (i == -1) {
//            Double valueOf = Double.valueOf(d6 - d);
//            nPVCalculatorActivity.txtNpv.setText("$" + com.demo.financialcalculator.util.Utils.decimalFormat.format(valueOf));
//        } else {
//            Toast.makeText(nPVCalculatorActivity, "Year  " + (i + 1) + "  value cannot be zero!", Toast.LENGTH_SHORT).show();
//        }
//        AppConstant.hideKeyboard(this);
//        AppConstant.visibleResult(nPVCalculatorActivity.llResult);
//    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!com.demo.financialcalculator.util.Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.net_present_value_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.net_present_value_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.net_present_value_calculator));
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
                intent.setData(Uri.fromParts("package", NPVCalculatorActivity.this.getPackageName(), (String) null));
                NPVCalculatorActivity.this.startActivityForResult(intent, 112);
                NPVCalculatorActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }
}
