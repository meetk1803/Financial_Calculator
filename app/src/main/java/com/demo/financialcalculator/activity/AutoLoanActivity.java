package com.demo.financialcalculator.activity;

import android.annotation.SuppressLint;
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

public class AutoLoanActivity extends AppCompatActivity {
    Button btnCalculate;
    Button btnShare;
    Button btnStatistics;
    Calendar calendar;
    CommonModel commonModel;
    double downPayment;
    TextView etDate;
    EditText etDownPayment;
    EditText etInterestRate;
    EditText etOwed;
    EditText etSalesTax;
    EditText etTerms;
    EditText etTrade;
    EditText etVehiclePrice;
    double interest;
    CardView llResult;
    int mDay;
    int mMonth;
    AlertDialog mMyDialog;
    int mYear;
    double monthlyPayment;
    double owedTrade;
    double price;
    ScrollView rootLayout;
    double salesTax;
    Spinner spTerm;
    int term;
    double terms;
    Toolbar toolBar;
    TextView txtInterest;
    TextView txtLoan;
    TextView txtMonthlyPayment;
    TextView txtOverPayments;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_auto_loan);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.auto_loan_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AutoLoanActivity.this.onBackPressed();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void init() {
        this.commonModel = new CommonModel();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etVehiclePrice = (EditText) findViewById(R.id.etVehiclePrice);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etSalesTax = (EditText) findViewById(R.id.etSalesTax);
        this.etDownPayment = (EditText) findViewById(R.id.etDownPayment);
        this.etTrade = (EditText) findViewById(R.id.etTradeAmount);
        this.etOwed = (EditText) findViewById(R.id.etOwedTrade);
        this.etDate = (TextView) findViewById(R.id.etDate);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.txtMonthlyPayment = (TextView) findViewById(R.id.txtMonthlyPayment);
        this.txtLoan = (TextView) findViewById(R.id.txtLoan);
        this.txtOverPayments = (TextView) findViewById(R.id.txtOverPayments);
        this.txtInterest = (TextView) findViewById(R.id.txtInterest);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        this.etDate.setText(AppConstant.getFormattedDate(instance.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
        this.etDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AutoLoanActivity.this.startDateDialog();
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
                        AutoLoanActivity.this.term = 1;
                        return;
                    case 1:
                        AutoLoanActivity.this.term = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        click();
    }

    private void click() {
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AutoLoanActivity.this.setValue()) {
                    AppConstant.hideKeyboard(AutoLoanActivity.this);
                    AppConstant.visibleResult(AutoLoanActivity.this.llResult);
                }
                AutoLoanActivity autoLoanActivity = AutoLoanActivity.this;
                autoLoanActivity.txtMonthlyPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(autoLoanActivity.monthlyPayment)}));
                AutoLoanActivity autoLoanActivity2 = AutoLoanActivity.this;
                autoLoanActivity2.txtLoan.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(autoLoanActivity2.price)}));
                AutoLoanActivity autoLoanActivity3 = AutoLoanActivity.this;
                autoLoanActivity3.txtOverPayments.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(autoLoanActivity3.monthlyPayment * autoLoanActivity3.terms)}));
                AutoLoanActivity autoLoanActivity4 = AutoLoanActivity.this;
                autoLoanActivity4.txtInterest.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(Utils.getTotalInterest(autoLoanActivity4.monthlyPayment, autoLoanActivity4.terms, autoLoanActivity4.price))}));
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AutoLoanActivity.this.setValue()) {
                    AutoLoanActivity autoLoanActivity = AutoLoanActivity.this;
                    autoLoanActivity.commonModel.setPrincipalAmount(autoLoanActivity.price);
                    AutoLoanActivity autoLoanActivity2 = AutoLoanActivity.this;
                    autoLoanActivity2.commonModel.setTerms(autoLoanActivity2.terms);
                    AutoLoanActivity autoLoanActivity3 = AutoLoanActivity.this;
                    autoLoanActivity3.commonModel.setInterestRate(autoLoanActivity3.interest);
                    AutoLoanActivity autoLoanActivity4 = AutoLoanActivity.this;
                    autoLoanActivity4.commonModel.setMonthlyPayment(autoLoanActivity4.monthlyPayment);
                    AutoLoanActivity autoLoanActivity5 = AutoLoanActivity.this;
                    autoLoanActivity5.commonModel.setDate(autoLoanActivity5.calendar.getTimeInMillis());
                    Intent intent = new Intent(AutoLoanActivity.this, StatisticsActivity.class);
                    intent.putExtra("AutoLoan", AutoLoanActivity.this.commonModel);
                    AutoLoanActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AutoLoanActivity.this.checkPermission();
            }
        });
    }

    public boolean setValue() {
        try {
            if (!this.etVehiclePrice.getText().toString().trim().isEmpty()) {
                double doubleValue = Double.valueOf(this.etVehiclePrice.getText().toString()).doubleValue();
                double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                if (doubleValue != d) {
                    double doubleValue2 = Double.valueOf(this.etVehiclePrice.getText().toString()).doubleValue();
                    if (!this.etTrade.getText().toString().trim().isEmpty()) {
                        this.price = doubleValue2 - Double.valueOf(this.etTrade.getText().toString()).doubleValue();
                    } else {
                        this.price = doubleValue2;
                    }
                    if (!this.etDownPayment.getText().toString().trim().isEmpty()) {
                        this.downPayment = Double.valueOf(this.etDownPayment.getText().toString()).doubleValue();
                    } else {
                        this.downPayment = d;
                    }
                    if (!this.etSalesTax.getText().toString().trim().isEmpty()) {
                        this.salesTax = (this.price * Double.valueOf(this.etSalesTax.getText().toString()).doubleValue()) / 100.0d;
                    } else {
                        this.salesTax = d;
                    }
                    if (!this.etOwed.getText().toString().trim().isEmpty()) {
                        this.owedTrade = Double.valueOf(this.etOwed.getText().toString()).doubleValue();
                    } else {
                        this.owedTrade = d;
                    }
                    this.price = ((this.price + this.salesTax) - this.downPayment) + this.owedTrade;
                    if (this.etInterestRate.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == d) {
                        this.etInterestRate.setError("Please fill out this field");
                        return false;
                    }
                    this.interest = Double.valueOf(this.etInterestRate.getText().toString()).doubleValue();
                    if (this.etTerms.getText().toString().trim().isEmpty() || Double.valueOf(this.etTerms.getText().toString()).doubleValue() == d) {
                        this.etTerms.setError("Please fill out this field");
                        return false;
                    }
                    double doubleValue22 = Double.valueOf(this.etTerms.getText().toString()).doubleValue();
                    double d2 = (double) this.term;
                    Double.isNaN(d2);
                    Double.isNaN(d2);
                    double d3 = doubleValue22 * d2;
                    this.terms = d3;
                    this.monthlyPayment = Utils.getMonthlyPayment(this.price, this.interest, d3);
                    return true;
                }
            }
            this.etVehiclePrice.setError("Please fill out this field");
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
                ShareUtil.print(this, this.rootLayout, getString(R.string.auto_loan_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.auto_loan_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.auto_loan_calculator));
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
                intent.setData(Uri.fromParts("package", AutoLoanActivity.this.getPackageName(), (String) null));
                AutoLoanActivity.this.startActivityForResult(intent, 112);
                AutoLoanActivity.this.mMyDialog.dismiss();
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
                AutoLoanActivity.this.calendar.set(5, i3);
                AutoLoanActivity.this.calendar.set(2, i2);
                AutoLoanActivity.this.calendar.set(1, i);
                AutoLoanActivity autoLoanActivity = AutoLoanActivity.this;
                autoLoanActivity.etDate.setText(AppConstant.getFormattedDate(autoLoanActivity.calendar.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
            }
        }, this.mYear, this.mMonth, this.mDay).show();
    }
}
