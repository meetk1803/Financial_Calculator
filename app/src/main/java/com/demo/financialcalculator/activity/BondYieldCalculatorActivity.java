package com.demo.financialcalculator.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.github.mikephil.charting.utils.Utils;

public class BondYieldCalculatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    double annualCouponPayment;
    Button btnCalculate;
    Button btnShare;
    double couponRate;
    double couponValue;
    double currentPrice;
    EditText etCouponRate;
    EditText etCurrentPrice;
    EditText etParValue;
    EditText etYearsTo;
    String item;
    LinearLayout linearCouponrate;
    CardView llResult;
    AlertDialog mMyDialog;
    double parValue;
    double result;
    ScrollView rootLayout;
    Spinner spinner;
    Toolbar toolBar;
    TextView txtCurrentYield;
    TextView txtYieldToMaturity;
    double yearsToMaturity;
    double yield;

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_bond_yield_calculator);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
        setSpinner();
    }

    @SuppressLint("ResourceType")
    private void setSpinner() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.payment_array));
        arrayAdapter.setDropDownViewResource(17367049);
        this.spinner.setAdapter(arrayAdapter);
        this.spinner.setOnItemSelectedListener(this);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((int) R.string.bond_yield_calculator);
        setSupportActionBar(this.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    private void init() {
        this.etCurrentPrice = (EditText) findViewById(R.id.etCurrentPrice);
        this.etParValue = (EditText) findViewById(R.id.etParValue);
        this.etCouponRate = (EditText) findViewById(R.id.etCouponRate);
        EditText editText = (EditText) findViewById(R.id.etYearsTo);
        this.etYearsTo = editText;
        editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});
        this.txtCurrentYield = (TextView) findViewById(R.id.txtCurrentYield);
        this.txtYieldToMaturity = (TextView) findViewById(R.id.txtYieldToMaturity);
        this.linearCouponrate = (LinearLayout) findViewById(R.id.linearCouponrate);
        this.spinner = (Spinner) findViewById(R.id.spinner);
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BondYieldCalculatorActivity.this.calculate();
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BondYieldCalculatorActivity.this.checkPermission();
            }
        });
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.item = adapterView.getItemAtPosition(i).toString();
        checkVisibility();
    }

    @SuppressLint({"SetTextI18n", "UseValueOf"})
    public void calculate() {
        if (validationDetails()) {
            try {
                this.currentPrice = Double.parseDouble(this.etCurrentPrice.getText().toString());
                this.parValue = Double.parseDouble(this.etParValue.getText().toString());
                this.yearsToMaturity = Double.parseDouble(this.etYearsTo.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                double d = Utils.DOUBLE_EPSILON;
                this.yearsToMaturity = d;
                this.currentPrice = d;
                this.parValue = d;
            }
            if (!this.item.equalsIgnoreCase("None")) {
                this.couponRate = Double.parseDouble(this.etCouponRate.getText().toString());
            }
            if (!this.item.equalsIgnoreCase("None")) {
                double d2 = this.couponRate * this.parValue;
                this.annualCouponPayment = d2;
                this.yield = d2 / this.currentPrice;
            } else {
                this.yield = Utils.DOUBLE_EPSILON;
            }
            if (!this.item.equalsIgnoreCase("Annually") && !this.item.equalsIgnoreCase("Quarterly") && !this.item.equalsIgnoreCase("Semi-Annually") && !this.item.equalsIgnoreCase("Monthly")) {
                double d3 = this.yearsToMaturity;
                if (d3 >= 1.0d) {
                    double d22 = this.couponRate;
                    double d32 = this.parValue;
                    double d4 = d22 * d32;
                    this.annualCouponPayment = d4;
                    this.couponValue = d4 / 100.0d;
                    double d42 = this.couponValue;
                    double d5 = this.currentPrice;
                    this.result = (((d32 - d5) / d3) + d42) / ((d32 + d5) / 2.0d);
                } else {
                    Toast.makeText(this, "You must put value greater than or equal to 1!", Toast.LENGTH_SHORT).show();
                }
            }
            TextView textView = this.txtCurrentYield;
            textView.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(this.yield) + "%");
            TextView textView2 = this.txtYieldToMaturity;
            textView2.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(this.result) + "%");
            AppConstant.hideKeyboard(this);
            AppConstant.visibleResult(this.llResult);
        }
    }

    private void checkVisibility() {
        if (this.item.equalsIgnoreCase("None")) {
            this.linearCouponrate.setVisibility(View.GONE);
        } else {
            this.linearCouponrate.setVisibility(View.VISIBLE);
        }
    }

    private boolean validationDetails() {
        try {
            if (!this.etCurrentPrice.getText().toString().isEmpty()) {
                double parseDouble = Double.parseDouble(this.etCurrentPrice.getText().toString());
                double d = Utils.DOUBLE_EPSILON;
                if (parseDouble != d) {
                    if (this.etParValue.getText().toString().isEmpty() || Double.parseDouble(this.etParValue.getText().toString()) == d) {
                        this.etParValue.setError("Please fill out this field");
                        return false;
                    } else if (!this.item.equalsIgnoreCase("None") && (this.etCouponRate.getText().toString().isEmpty() || Double.parseDouble(this.etCouponRate.getText().toString()) == d)) {
                        this.etCouponRate.setError("Please fill out this field");
                        return false;
                    } else if (!this.etYearsTo.getText().toString().isEmpty() && Double.parseDouble(this.etYearsTo.getText().toString()) != d) {
                        return true;
                    } else {
                        this.etYearsTo.setError("Please fill out this field");
                        return false;
                    }
                }
            }
            this.etCurrentPrice.setError("Please fill out this field");
            return false;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid decimal value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    public class InputFilterMinMax implements InputFilter {
        private int max;
        private int min;

        private boolean isInRange(int i, int i2, int i3) {
            if (i2 > i) {
                if (i3 < i || i3 > i2) {
                    return false;
                }
                return true;
            } else if (i3 < i2 || i3 > i) {
                return false;
            } else {
                return true;
            }
        }

        public InputFilterMinMax(int i, int i2) {
            this.min = i;
            this.max = i2;
        }

        InputFilterMinMax(String str, String str2) {
            this.min = Integer.parseInt(str);
            this.max = Integer.parseInt(str2);
        }

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            try {
                int i5 = this.min;
                int i6 = this.max;
                if (isInRange(i5, i6, Integer.parseInt(spanned.toString() + charSequence.toString()))) {
                    return null;
                }
                return "";
            } catch (NumberFormatException e) {
                return "";
            }
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!com.demo.financialcalculator.util.Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.bond_yield_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.bond_yield_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.bond_yield_calculator));
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
                intent.setData(Uri.fromParts("package", BondYieldCalculatorActivity.this.getPackageName(), (String) null));
                BondYieldCalculatorActivity.this.startActivityForResult(intent, 112);
                BondYieldCalculatorActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }
}
