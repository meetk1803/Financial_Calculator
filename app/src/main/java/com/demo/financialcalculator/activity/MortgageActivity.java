package com.demo.financialcalculator.activity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.model.MonthModel;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class MortgageActivity extends AppCompatActivity {
    double PMI;
    double TaxIns;
    Button btnCalculate;
    Button btnShare;
    Button btnStatistics;
    Calendar calendar;
    CommonModel commonModel;
    double downPayment;
    TextView etDate;
    EditText etDownPayment;
    EditText etInterestRate;
    EditText etPMI;
    EditText etPropertyInsurance;
    EditText etPropertyTax;
    EditText etPurchasePrice;
    EditText etTerms;
    double interestRate;
    CardView llResult;
    int mDay;
    int mMonth;
    AlertDialog mMyDialog;
    int mYear;
    ArrayList<MonthModel> monthModels;
    double monthlyPayment;
    double propertyInsurance;
    double propertyTax;
    double purchasePrice;
    ScrollView rootLayout;
    Spinner spTerm;
    int term;
    double terms;
    Toolbar toolBar;
    TextView txtLoanAmount;
    TextView txtMonthlyPayment;
    TextView txtOverPayments;
    TextView txtTotalInterest;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_mortgage);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.mortgage_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MortgageActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.monthModels = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etPurchasePrice = (EditText) findViewById(R.id.etPurchasePrice);
        this.etDownPayment = (EditText) findViewById(R.id.etDownPayment);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etPropertyTax = (EditText) findViewById(R.id.etPropertyTax);
        this.etPMI = (EditText) findViewById(R.id.etPMI);
        this.etPropertyInsurance = (EditText) findViewById(R.id.etPropertyInsurance);
        this.etDate = (TextView) findViewById(R.id.etDate);
        this.txtMonthlyPayment = (TextView) findViewById(R.id.txtMonthlyPayment);
        this.txtLoanAmount = (TextView) findViewById(R.id.txtLoanAmount);
        this.txtOverPayments = (TextView) findViewById(R.id.txtOverPayments);
        this.txtTotalInterest = (TextView) findViewById(R.id.txtTotalInterest);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        this.etDate.setText(AppConstant.getFormattedDate(instance.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
        this.etDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MortgageActivity.this.startDateDialog();
            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.terms_array));
        arrayAdapter.setDropDownViewResource(17367049);
        this.spTerm.setAdapter(arrayAdapter);
        this.spTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                int c;
                String obj = adapterView.getItemAtPosition(i).toString();
                Log.e("obj", "" + obj);
                if (obj.equals("Yr")) {
                    c = 1;
                } else {
                    obj.equals("Mo");
                    c = 0;
                }
                switch (c) {
                    case 0:
                        MortgageActivity.this.term = 1;
                        return;
                    case 1:
                        MortgageActivity.this.term = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MortgageActivity.this.setValue()) {
                    AppConstant.hideKeyboard(MortgageActivity.this);
                    AppConstant.visibleResult(MortgageActivity.this.llResult);
                }
                MortgageActivity mortgageActivity = MortgageActivity.this;
                mortgageActivity.txtMonthlyPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(mortgageActivity.monthlyPayment + mortgageActivity.TaxIns)}));
                MortgageActivity.this.txtLoanAmount.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(Utils.Principal)}));
                MortgageActivity.this.txtOverPayments.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(Utils.Paid)}));
                MortgageActivity.this.txtTotalInterest.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(Utils.Interest)}));
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MortgageActivity.this.setValue()) {
                    MortgageActivity mortgageActivity = MortgageActivity.this;
                    mortgageActivity.commonModel.setPrincipalAmount(mortgageActivity.purchasePrice);
                    MortgageActivity mortgageActivity2 = MortgageActivity.this;
                    mortgageActivity2.commonModel.setTerms(mortgageActivity2.terms);
                    MortgageActivity mortgageActivity3 = MortgageActivity.this;
                    mortgageActivity3.commonModel.setInterestRate(mortgageActivity3.interestRate);
                    MortgageActivity mortgageActivity4 = MortgageActivity.this;
                    mortgageActivity4.commonModel.setMonthlyPayment(mortgageActivity4.monthlyPayment);
                    MortgageActivity mortgageActivity5 = MortgageActivity.this;
                    mortgageActivity5.commonModel.setTaxIns(mortgageActivity5.TaxIns);
                    MortgageActivity mortgageActivity6 = MortgageActivity.this;
                    mortgageActivity6.commonModel.setPMI(mortgageActivity6.PMI);
                    MortgageActivity mortgageActivity7 = MortgageActivity.this;
                    mortgageActivity7.commonModel.setDate(mortgageActivity7.calendar.getTimeInMillis());
                    MortgageActivity mortgageActivity8 = MortgageActivity.this;
                    mortgageActivity8.commonModel.setDownPayment(mortgageActivity8.downPayment);
                    Intent intent = new Intent(MortgageActivity.this, MortgageStatisticsActivity.class);
                    intent.putExtra("Mortgage", MortgageActivity.this.commonModel);
                    MortgageActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MortgageActivity.this.checkPermission();
            }
        });
    }

    public boolean setValue() {
        try {
            if (!this.etDownPayment.getText().toString().trim().isEmpty()) {
                this.downPayment = Double.valueOf(this.etDownPayment.getText().toString()).doubleValue();
            } else {
                this.downPayment = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            if (!this.etPropertyTax.getText().toString().trim().isEmpty()) {
                this.propertyTax = Double.valueOf(this.etPropertyTax.getText().toString()).doubleValue() / 12.0d;
            } else {
                this.propertyTax = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            if (!this.etPropertyInsurance.getText().toString().trim().isEmpty()) {
                this.propertyInsurance = Double.valueOf(this.etPropertyInsurance.getText().toString()).doubleValue() / 12.0d;
            } else {
                this.propertyInsurance = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            if (!this.etPurchasePrice.getText().toString().trim().isEmpty()) {
                double doubleValue = Double.valueOf(this.etPurchasePrice.getText().toString()).doubleValue();
                double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                if (doubleValue != d) {
                    this.purchasePrice = Double.valueOf(this.etPurchasePrice.getText().toString()).doubleValue();
                    if (this.etTerms.getText().toString().trim().isEmpty() || Double.valueOf(this.etTerms.getText().toString()).doubleValue() == d) {
                        this.etTerms.setError("Please fill out this field");
                        return false;
                    }
                    double doubleValue2 = Double.valueOf(this.etTerms.getText().toString()).doubleValue();
                    double d2 = (double) this.term;
                    Double.isNaN(d2);
                    Double.isNaN(d2);
                    this.terms = doubleValue2 * d2;
                    if (this.etInterestRate.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == d) {
                        double d3 = d2;
                        this.etInterestRate.setError("Please fill out this field");
                        return false;
                    }
                    this.interestRate = Double.valueOf(this.etInterestRate.getText().toString()).doubleValue();
                    if (!this.etPMI.getText().toString().trim().isEmpty()) {
                        this.PMI = (Double.valueOf(this.etPMI.getText().toString()).doubleValue() * (this.purchasePrice - this.downPayment)) / 1200.0d;
                    } else {
                        this.PMI = d;
                    }
                    this.TaxIns = this.propertyTax + this.propertyInsurance;
                    this.monthlyPayment = Utils.getMonthlyPayment(this.purchasePrice - this.downPayment, this.interestRate, this.terms);
                    StringBuilder sb = new StringBuilder();
                    sb.append(">>>");
                    StringBuilder sb2 = sb;
                    sb2.append(Utils.getMonthlyPayment(this.purchasePrice, this.interestRate, this.terms));
                    Log.d("Month", sb2.toString());
                    if (!((Utils.Principal == d || Utils.Interest == d || Utils.Paid == d) && Utils.mTaxInsPMI == d)) {
                        Utils.Principal = d;
                        Utils.Interest = d;
                        Utils.Paid = d;
                        Utils.mTaxInsPMI = d;
                    }
                    if (this.monthModels.size() > 0) {
                        this.monthModels.clear();
                    }
                    double d4 = this.purchasePrice;
                    double d5 = this.terms;
                    double d6 = this.interestRate;
                    double d7 = this.monthlyPayment;
                    double d8 = this.TaxIns;
                    double d9 = doubleValue2;
                    double d10 = d2;
                    double d11 = d4;
                    double d12 = d6;
                    double d13 = d7;
                    this.monthModels = Utils.getMonthlyMortgage(d11, d5, d12, d13, d8, this.PMI, Utils.CALDATE(this.etDate.getText().toString()), this.downPayment);
                    return true;
                }
            }
            this.etPurchasePrice.setError("Please fill out this field");
            return false;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid decimal value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.mortgage_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.mortgage_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.mortgage_calculator));
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
                intent.setData(Uri.fromParts("package", MortgageActivity.this.getPackageName(), (String) null));
                MortgageActivity.this.startActivityForResult(intent, 112);
                MortgageActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: package-private */
    public void startDateDialog() {
        this.mYear = this.calendar.get(1);
        this.mMonth = this.calendar.get(2);
        this.mDay = this.calendar.get(5);
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                MortgageActivity.this.calendar.set(5, i3);
                MortgageActivity.this.calendar.set(2, i2);
                MortgageActivity.this.calendar.set(1, i);
                MortgageActivity mortgageActivity = MortgageActivity.this;
                mortgageActivity.etDate.setText(AppConstant.getFormattedDate(mortgageActivity.calendar.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
            }
        }, this.mYear, this.mMonth, this.mDay).show();
    }
}
