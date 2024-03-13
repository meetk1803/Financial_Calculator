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

public class AmortizationActivity extends AppCompatActivity {
    double amount;
    Button btnCalculate;
    Button btnShare;
    Button btnStatistics;
    Calendar calendar;
    CommonModel commonModel;
    EditText etAmount;
    TextView etDate;
    EditText etInterestRate;
    EditText etTerms;
    double interest;
    CardView llResult;
    int mDay;
    int mMonth;
    AlertDialog mMyDialog;
    int mYear;
    double result;
    ScrollView rootLayout;
    Spinner spTerm;
    int term;
    double terms;
    Toolbar toolBar;
    TextView txtMonthlyPayment;
    TextView txtTotalInterest;
    TextView txtTotalPayments;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_amortization);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.amortization_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AmortizationActivity.this.onBackPressed();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void init() {
        this.commonModel = new CommonModel();
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etAmount = (EditText) findViewById(R.id.etAmount);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etDate = (TextView) findViewById(R.id.etDate);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        this.txtMonthlyPayment = (TextView) findViewById(R.id.txtMonthlyPayment);
        this.txtTotalPayments = (TextView) findViewById(R.id.txtTotalPayments);
        this.txtTotalInterest = (TextView) findViewById(R.id.txtTotalInterest);
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        this.etDate.setText(AppConstant.getFormattedDate(instance.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
        this.etDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AmortizationActivity.this.startDateDialog();
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
                        AmortizationActivity.this.term = 1;
                        return;
                    case 1:
                        AmortizationActivity.this.term = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AmortizationActivity.this.calculate()) {
                    AppConstant.hideKeyboard(AmortizationActivity.this);
                    AppConstant.visibleResult(AmortizationActivity.this.llResult);
                    AmortizationActivity amortizationActivity = AmortizationActivity.this;
                    amortizationActivity.txtMonthlyPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(amortizationActivity.result)}));
                    AmortizationActivity amortizationActivity2 = AmortizationActivity.this;
                    amortizationActivity2.txtTotalPayments.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(amortizationActivity2.result * amortizationActivity2.terms)}));
                    AmortizationActivity amortizationActivity3 = AmortizationActivity.this;
                    amortizationActivity3.txtTotalInterest.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format((amortizationActivity3.result * amortizationActivity3.terms) - amortizationActivity3.amount)}));
                }
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AmortizationActivity.this.calculate()) {
                    AmortizationActivity amortizationActivity = AmortizationActivity.this;
                    amortizationActivity.commonModel.setPrincipalAmount(amortizationActivity.amount);
                    AmortizationActivity amortizationActivity2 = AmortizationActivity.this;
                    amortizationActivity2.commonModel.setTerms(amortizationActivity2.terms);
                    AmortizationActivity amortizationActivity3 = AmortizationActivity.this;
                    amortizationActivity3.commonModel.setInterestRate(amortizationActivity3.interest);
                    AmortizationActivity amortizationActivity4 = AmortizationActivity.this;
                    amortizationActivity4.commonModel.setMonthlyPayment(amortizationActivity4.result);
                    AmortizationActivity amortizationActivity5 = AmortizationActivity.this;
                    amortizationActivity5.commonModel.setDate(amortizationActivity5.calendar.getTimeInMillis());
                    Intent intent = new Intent(AmortizationActivity.this, StatisticsActivity.class);
                    intent.putExtra("AutoLoan", AmortizationActivity.this.commonModel);
                    AmortizationActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AmortizationActivity.this.checkPermission();
            }
        });
    }

    public boolean calculate() {
        try {
            if (this.etAmount.getText().toString().length() != 0) {
                double parseDouble = Double.parseDouble(this.etAmount.getText().toString());
                double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
//                double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                if (parseDouble != d) {
                    if (this.etInterestRate.getText().toString().length() == 0 || Double.parseDouble(this.etInterestRate.getText().toString()) == d) {
                        this.etInterestRate.setError("Please fill out this field");
                        return false;
                    } else if (this.etTerms.getText().toString().length() == 0 || Double.parseDouble(this.etTerms.getText().toString()) == d) {
                        this.etTerms.setError("Please fill out this field");
                        return false;
                    } else {
                        this.amount = Double.parseDouble(this.etAmount.getText().toString());
                        this.interest = Double.parseDouble(this.etInterestRate.getText().toString());
                        double parseDouble2 = Double.parseDouble(this.etTerms.getText().toString());
                        double d2 = (double) this.term;
                        Double.isNaN(d2);
                        Double.isNaN(d2);
                        double d3 = parseDouble2 * d2;
                        this.terms = d3;
                        double d4 = this.interest;
                        double d22 = (d4 / 100.0d) / 12.0d;
                        if (d4 == d) {
                            this.result = this.amount / d3;
                            return true;
                        }
                        double d5 = parseDouble2;
                        double d42 = d22 + 1.0d;
                        double d6 = d2;
                        this.result = (Math.pow(d42, d3) * (this.amount * d22)) / (Math.pow(d42, this.terms) - 1.0d);
                        return true;
                    }
                }
            }
            this.etAmount.setError("Please fill out this field");
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
                ShareUtil.print(this, this.rootLayout, getString(R.string.amortization_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.amortization_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.amortization_calculator));
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
                intent.setData(Uri.fromParts("package", AmortizationActivity.this.getPackageName(), (String) null));
                AmortizationActivity.this.startActivityForResult(intent, 112);
                AmortizationActivity.this.mMyDialog.dismiss();
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
                AmortizationActivity.this.calendar.set(5, i3);
                AmortizationActivity.this.calendar.set(2, i2);
                AmortizationActivity.this.calendar.set(1, i);
                AmortizationActivity amortizationActivity = AmortizationActivity.this;
                amortizationActivity.etDate.setText(AppConstant.getFormattedDate(amortizationActivity.calendar.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
            }
        }, this.mYear, this.mMonth, this.mDay).show();
    }
}
