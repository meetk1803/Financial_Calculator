package com.demo.financialcalculator.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class RentalYieldCalculatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnCalculate;
    Button btnShare;
    EditText etExpense;
    EditText etPropertyPrice;
    EditText etRent;
    EditText etVacancyRate;
    String item;
    CardView llResult;
    AlertDialog mMyDialog;
    ScrollView rootLayout;
    Spinner spinner;
    Toolbar toolBar;
    TextView txtGrossYield;
    TextView txtNetYield;

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_rental_yield_calculator);
        init();
        setUpToolbar();
        setSpinner();
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
    }

    @SuppressLint("ResourceType")
    private void setSpinner() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.rent_array));
        arrayAdapter.setDropDownViewResource(17367049);
        this.spinner.setAdapter(arrayAdapter);
        this.spinner.setOnItemSelectedListener(this);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((int) R.string.rental_yield_calculator);
        setSupportActionBar(this.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    private void init() {
        this.etPropertyPrice = (EditText) findViewById(R.id.etPropertyPrice);
        this.etRent = (EditText) findViewById(R.id.etRent);
        this.etVacancyRate = (EditText) findViewById(R.id.etVacancyRate);
        this.etExpense = (EditText) findViewById(R.id.etExpense);
        this.txtGrossYield = (TextView) findViewById(R.id.txtGrossYield);
        this.txtNetYield = (TextView) findViewById(R.id.txtNetYield);
        this.spinner = (Spinner) findViewById(R.id.spinner);
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RentalYieldCalculatorActivity.this.calculate();
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RentalYieldCalculatorActivity.this.checkPermission();
            }
        });
    }

    @SuppressLint({"SetTextI18n", "UseValueOf"})
    public void calculate() {
        int c;
        Double valueOf;
        if (validationDetails()) {
            Double valueOf2 = Double.valueOf(Double.parseDouble(this.etPropertyPrice.getText().toString()));
            Double valueOf3 = Double.valueOf(Double.parseDouble(this.etRent.getText().toString()));
            Double valueOf4 = Double.valueOf(Double.parseDouble(this.etVacancyRate.getText().toString()));
            Double valueOf5 = Double.valueOf(Double.parseDouble(this.etExpense.getText().toString()));
            String str = this.item;
            if (valueOf2.doubleValue() > Utils.DOUBLE_EPSILON) {
                Log.e("str", "= " + str);
                if (str.equals("Weekly")) {
                    c = 2;
                } else if (str.equals("Annually")) {
                    c = 1;
                } else {
                    str.equals("Monthly");
                    c = 0;
                }
                Log.e("c", "= " + c);
                Double d22 = null;
                switch (c) {
                    case 0:
                        Double valueOf6 = Double.valueOf(valueOf3.doubleValue() * 12.0d);
                        d22 = Double.valueOf((valueOf6.doubleValue() / valueOf2.doubleValue()) * 100.0d);
                        valueOf = Double.valueOf(((Double.valueOf(valueOf6.doubleValue() - Double.valueOf(valueOf6.doubleValue() * (valueOf4.doubleValue() / 100.0d)).doubleValue()).doubleValue() - valueOf5.doubleValue()) / valueOf2.doubleValue()) * 100.0d);
                        break;
                    case 1:
                        d22 = Double.valueOf((valueOf3.doubleValue() / valueOf2.doubleValue()) * 100.0d);
                        valueOf = Double.valueOf(((Double.valueOf(valueOf3.doubleValue() - Double.valueOf(valueOf3.doubleValue() * (valueOf4.doubleValue() / 100.0d)).doubleValue()).doubleValue() - valueOf5.doubleValue()) / valueOf2.doubleValue()) * 100.0d);
                        break;
                    case 2:
                        Double valueOf7 = Double.valueOf(valueOf3.doubleValue() * 52.0d);
                        d22 = Double.valueOf((valueOf7.doubleValue() / valueOf2.doubleValue()) * 100.0d);
                        valueOf = Double.valueOf(((Double.valueOf(valueOf7.doubleValue() - Double.valueOf(valueOf7.doubleValue() * (valueOf4.doubleValue() / 100.0d)).doubleValue()).doubleValue() - valueOf5.doubleValue()) / valueOf2.doubleValue()) * 100.0d);
                        break;
                    default:
                        valueOf = null;
                        break;
                }
                TextView textView = this.txtGrossYield;
                textView.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(d22) + "%");
                TextView textView2 = this.txtNetYield;
                textView2.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(valueOf) + "%");
            } else {
                Toast.makeText(this, "Property Price Value must be greater than or equal to 1", Toast.LENGTH_SHORT).show();
            }
            AppConstant.hideKeyboard(this);
            AppConstant.visibleResult(this.llResult);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.item = adapterView.getItemAtPosition(i).toString();
    }

    private boolean validationDetails() {
        try {
            if (!this.etPropertyPrice.getText().toString().isEmpty()) {
                double doubleValue = Double.valueOf(this.etPropertyPrice.getText().toString()).doubleValue();
                double d = Utils.DOUBLE_EPSILON;
                if (doubleValue != d) {
                    if (this.etRent.getText().toString().isEmpty() || Double.valueOf(this.etRent.getText().toString()).doubleValue() == d) {
                        this.etRent.setError("Please fill out this field");
                        return false;
                    } else if (this.etVacancyRate.getText().toString().isEmpty() || Double.valueOf(this.etVacancyRate.getText().toString()).doubleValue() == d) {
                        this.etVacancyRate.setError("Please fill out this field");
                        return false;
                    } else if (!this.etExpense.getText().toString().isEmpty() && Double.valueOf(this.etExpense.getText().toString()).doubleValue() != d) {
                        return true;
                    } else {
                        this.etExpense.setError("Please fill out this field");
                        return false;
                    }
                }
            }
            this.etPropertyPrice.setError("Please fill out this field");
            return false;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid decimal value", 0).show();
            e.printStackTrace();
            return false;
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!com.demo.financialcalculator.util.Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.rental_yield_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.rental_yield_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.rental_yield_calculator));
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
                intent.setData(Uri.fromParts("package", RentalYieldCalculatorActivity.this.getPackageName(), (String) null));
                RentalYieldCalculatorActivity.this.startActivityForResult(intent, 112);
                RentalYieldCalculatorActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }
}
