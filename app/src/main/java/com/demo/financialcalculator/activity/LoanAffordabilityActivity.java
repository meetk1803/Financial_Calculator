package com.demo.financialcalculator.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;

import java.util.Calendar;

public class LoanAffordabilityActivity extends AppCompatActivity {
    Button btnCalculate;
    Button btnShare;
    Button btnStatistics;
    Calendar calendar;
    CommonModel commonModel;
    TextView etDate;
    EditText etInterestRate;
    EditText etMaxMonthly;
    EditText etTerms;
    double interestRate;
    CardView llResult;
    int mDay;
    int mMonth;
    AlertDialog mMyDialog;
    int mYear;
    double maxPayment;
    double result;
    ScrollView rootLayout;
    Spinner spTerm;
    int term;
    double terms;
    Toolbar toolBar;
    TextView txtLoan;
    TextView txtMonthlyPayment;
    TextView txtTotalInterest;
    TextView txtTotalPayments;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_loan_affordability);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.loan_affordability_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoanAffordabilityActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.etMaxMonthly = (EditText) findViewById(R.id.etMaxMonthly);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etDate = (TextView) findViewById(R.id.etDate);
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.txtLoan = (TextView) findViewById(R.id.txtLoan);
        this.txtMonthlyPayment = (TextView) findViewById(R.id.txtMonthlyPayment);
        this.txtTotalPayments = (TextView) findViewById(R.id.txtTotalPayments);
        this.txtTotalInterest = (TextView) findViewById(R.id.txtTotalInterest);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        this.etDate.setText(AppConstant.getFormattedDate(instance.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
        this.etDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoanAffordabilityActivity.this.startDateDialog();
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
                if (obj.equals("Yr")) {
                    c = 1;
                } else {
                    obj.equals("Mo");
                    c = 0;
                }
                switch (c) {
                    case 0:
                        LoanAffordabilityActivity.this.term = 1;
                        return;
                    case 1:
                        LoanAffordabilityActivity.this.term = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (LoanAffordabilityActivity.this.calculate()) {
                    AppConstant.hideKeyboard(LoanAffordabilityActivity.this);
                    AppConstant.visibleResult(LoanAffordabilityActivity.this.llResult);
                }
                LoanAffordabilityActivity loanAffordabilityActivity = LoanAffordabilityActivity.this;
                loanAffordabilityActivity.txtMonthlyPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(loanAffordabilityActivity.maxPayment)}));
                LoanAffordabilityActivity loanAffordabilityActivity2 = LoanAffordabilityActivity.this;
                loanAffordabilityActivity2.txtLoan.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(loanAffordabilityActivity2.result)}));
                LoanAffordabilityActivity loanAffordabilityActivity3 = LoanAffordabilityActivity.this;
                loanAffordabilityActivity3.txtTotalPayments.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(loanAffordabilityActivity3.maxPayment * loanAffordabilityActivity3.terms)}));
                LoanAffordabilityActivity loanAffordabilityActivity4 = LoanAffordabilityActivity.this;
                loanAffordabilityActivity4.txtTotalInterest.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(Utils.getTotalInterest(loanAffordabilityActivity4.maxPayment, loanAffordabilityActivity4.terms, loanAffordabilityActivity4.result))}));
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (LoanAffordabilityActivity.this.calculate()) {
                    LoanAffordabilityActivity loanAffordabilityActivity = LoanAffordabilityActivity.this;
                    loanAffordabilityActivity.commonModel.setPrincipalAmount(loanAffordabilityActivity.result);
                    LoanAffordabilityActivity loanAffordabilityActivity2 = LoanAffordabilityActivity.this;
                    loanAffordabilityActivity2.commonModel.setTerms(loanAffordabilityActivity2.terms);
                    LoanAffordabilityActivity loanAffordabilityActivity3 = LoanAffordabilityActivity.this;
                    loanAffordabilityActivity3.commonModel.setInterestRate(loanAffordabilityActivity3.interestRate);
                    LoanAffordabilityActivity loanAffordabilityActivity4 = LoanAffordabilityActivity.this;
                    loanAffordabilityActivity4.commonModel.setMonthlyPayment(loanAffordabilityActivity4.maxPayment);
                    LoanAffordabilityActivity loanAffordabilityActivity5 = LoanAffordabilityActivity.this;
                    loanAffordabilityActivity5.commonModel.setDate(loanAffordabilityActivity5.calendar.getTimeInMillis());
                    Intent intent = new Intent(LoanAffordabilityActivity.this, StatisticsActivity.class);
                    intent.putExtra("AutoLoan", LoanAffordabilityActivity.this.commonModel);
                    LoanAffordabilityActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoanAffordabilityActivity.this.checkPermission();
            }
        });
    }

    public boolean calculate() {
        double d;
        try {
            if (this.etMaxMonthly.getText().toString().length() != 0) {
                double doubleValue = Double.valueOf(this.etMaxMonthly.getText().toString()).doubleValue();
                double d2 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                if (doubleValue != d2) {
                    if (this.etInterestRate.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == d2) {
                        this.etMaxMonthly.setError("Please fill out this field");
                        return false;
                    } else if (this.etTerms.getText().toString().length() == 0 || Double.valueOf(this.etTerms.getText().toString()).doubleValue() == d2) {
                        this.etTerms.setError("Please fill out this field");
                        return false;
                    } else {
                        this.maxPayment = Double.parseDouble(this.etMaxMonthly.getText().toString());
                        this.interestRate = Double.parseDouble(this.etInterestRate.getText().toString());
                        double parseDouble = Double.parseDouble(this.etTerms.getText().toString());
                        double d3 = (double) this.term;
                        Double.isNaN(d3);
                        Double.isNaN(d3);
                        double d4 = parseDouble * d3;
                        this.terms = d4;
                        double d5 = this.interestRate;
                        double d22 = (d5 / 100.0d) / 12.0d;
                        if (d5 == d2) {
                            this.result = this.maxPayment * d4;
                            double d6 = parseDouble;
                            d = d2;
                        } else {
                            double d7 = parseDouble;
                            double d32 = d22 + 1.0d;
                            d = d2;
                            this.result = (this.maxPayment * (Math.pow(d32, d4) - 1.0d)) / (Math.pow(d32, this.terms) * d22);
                        }
                        if (this.terms != d) {
                            return true;
                        }
                        this.maxPayment = d;
                        return true;
                    }
                }
            }
            this.etMaxMonthly.setError("Please fill out this field");
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
                ShareUtil.print(this, this.rootLayout, getString(R.string.loan_affordability_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.loan_affordability_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.loan_affordability_calculator));
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
                intent.setData(Uri.fromParts("package", LoanAffordabilityActivity.this.getPackageName(), (String) null));
                LoanAffordabilityActivity.this.startActivityForResult(intent, 112);
                LoanAffordabilityActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }

    /* access modifiers changed from: package-private */
    public void startDateDialog() {
        this.mYear = this.calendar.get(1);
        this.mMonth = this.calendar.get(2);
        this.mDay = this.calendar.get(5);
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                LoanAffordabilityActivity.this.calendar.set(5, i3);
                LoanAffordabilityActivity.this.calendar.set(2, i2);
                LoanAffordabilityActivity.this.calendar.set(1, i);
                LoanAffordabilityActivity loanAffordabilityActivity = LoanAffordabilityActivity.this;
                loanAffordabilityActivity.etDate.setText(AppConstant.getFormattedDate(loanAffordabilityActivity.calendar.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
            }
        }, this.mYear, this.mMonth, this.mDay).show();
    }
}
