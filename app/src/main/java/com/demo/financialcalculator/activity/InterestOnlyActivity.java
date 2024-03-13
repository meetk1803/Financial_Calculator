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
import com.demo.financialcalculator.model.MonthModel;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class InterestOnlyActivity extends AppCompatActivity {
    Button btnCalculate;
    Button btnShare;
    Button btnStatistics;
    Calendar calendar;
    CommonModel commonModel;
    TextView etDate;
    EditText etInterestPeriod;
    EditText etInterestRate;
    EditText etLoanAmount;
    EditText etTerms;
    double interestPeriod;
    double interestRate;
    CardView llResult;
    double loanAmount;
    int mDay;
    int mMonth;
    AlertDialog mMyDialog;
    int mYear;
    ArrayList<MonthModel> monthModels;
    double monthlyPayment;
    ScrollView rootLayout;
    Spinner spInterestPeriodTerm;
    Spinner spTerm;
    int term;
    int termInterestOnly;
    double terms;
    Toolbar toolBar;
    TextView txtInterest;
    TextView txtInterestPayment;
    TextView txtOverPayments;
    TextView txtPrincipal;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_interest_only);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.interest_only_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InterestOnlyActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.monthModels = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etLoanAmount = (EditText) findViewById(R.id.etLoanAmount);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etInterestPeriod = (EditText) findViewById(R.id.etInterestPeriod);
        this.etDate = (TextView) findViewById(R.id.etDate);
        this.txtInterestPayment = (TextView) findViewById(R.id.txtInterestPayment);
        this.txtPrincipal = (TextView) findViewById(R.id.txtPrincipal);
        this.txtOverPayments = (TextView) findViewById(R.id.txtOverPayments);
        this.txtInterest = (TextView) findViewById(R.id.txtInterest);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.spInterestPeriodTerm = (Spinner) findViewById(R.id.spInterestPeriodTerm);
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        this.etDate.setText(AppConstant.getFormattedDate(instance.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
        this.etDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InterestOnlyActivity.this.startDateDialog();
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
                        InterestOnlyActivity.this.term = 1;
                        return;
                    case 1:
                        InterestOnlyActivity.this.term = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.terms_array));
        arrayAdapter2.setDropDownViewResource(17367049);
        this.spInterestPeriodTerm.setAdapter(arrayAdapter2);
        this.spInterestPeriodTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        InterestOnlyActivity.this.termInterestOnly = 1;
                        return;
                    case 1:
                        InterestOnlyActivity.this.termInterestOnly = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (InterestOnlyActivity.this.setValue()) {
                    AppConstant.hideKeyboard(InterestOnlyActivity.this);
                    AppConstant.visibleResult(InterestOnlyActivity.this.llResult);
                }
                InterestOnlyActivity interestOnlyActivity = InterestOnlyActivity.this;
                interestOnlyActivity.txtInterestPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(Utils.getInterestOnly(interestOnlyActivity.interestRate, interestOnlyActivity.loanAmount))}));
                InterestOnlyActivity interestOnlyActivity2 = InterestOnlyActivity.this;
                interestOnlyActivity2.txtPrincipal.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(interestOnlyActivity2.monthlyPayment)}));
                InterestOnlyActivity interestOnlyActivity3 = InterestOnlyActivity.this;
                double interestOnly = Utils.getInterestOnly(interestOnlyActivity3.interestRate, interestOnlyActivity3.loanAmount);
                InterestOnlyActivity interestOnlyActivity4 = InterestOnlyActivity.this;
                double interestOnly2 = (interestOnly * interestOnlyActivity4.interestPeriod) + (interestOnlyActivity4.monthlyPayment * interestOnlyActivity4.terms);
                interestOnlyActivity4.txtOverPayments.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(interestOnly2)}));
                InterestOnlyActivity interestOnlyActivity5 = InterestOnlyActivity.this;
                interestOnlyActivity5.txtInterest.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(interestOnly2 - interestOnlyActivity5.loanAmount)}));
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (InterestOnlyActivity.this.setValue()) {
                    InterestOnlyActivity interestOnlyActivity = InterestOnlyActivity.this;
                    double d = interestOnlyActivity.interestPeriod;
                    if (d != com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                        interestOnlyActivity.terms += d;
                    }
                    interestOnlyActivity.commonModel.setPrincipalAmount(interestOnlyActivity.loanAmount);
                    InterestOnlyActivity interestOnlyActivity2 = InterestOnlyActivity.this;
                    interestOnlyActivity2.commonModel.setInterestRate(interestOnlyActivity2.interestRate);
                    InterestOnlyActivity interestOnlyActivity3 = InterestOnlyActivity.this;
                    interestOnlyActivity3.commonModel.setTerms(interestOnlyActivity3.terms);
                    InterestOnlyActivity interestOnlyActivity4 = InterestOnlyActivity.this;
                    interestOnlyActivity4.commonModel.setMonthlyPayment(interestOnlyActivity4.monthlyPayment);
                    InterestOnlyActivity interestOnlyActivity5 = InterestOnlyActivity.this;
                    interestOnlyActivity5.commonModel.setInterestPeriod(interestOnlyActivity5.interestPeriod);
                    InterestOnlyActivity interestOnlyActivity6 = InterestOnlyActivity.this;
                    interestOnlyActivity6.commonModel.setDate(interestOnlyActivity6.calendar.getTimeInMillis());
                    Intent intent = new Intent(InterestOnlyActivity.this, StatisticsActivity.class);
                    intent.putExtra("InterestOnly", InterestOnlyActivity.this.commonModel);
                    InterestOnlyActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InterestOnlyActivity.this.checkPermission();
            }
        });
    }

    public boolean setValue() {
        try {
            if (!this.etLoanAmount.getText().toString().trim().isEmpty()) {
                if (Double.valueOf(this.etLoanAmount.getText().toString()).doubleValue() != com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                    try {
                        this.loanAmount = Double.valueOf(this.etLoanAmount.getText().toString()).doubleValue();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (this.etInterestRate.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                        this.etInterestRate.setError("Please fill out this field");
                        return false;
                    }
                    try {
                        this.interestRate = Double.valueOf(this.etInterestRate.getText().toString()).doubleValue();
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    }
                    if (!this.etInterestPeriod.getText().toString().trim().isEmpty()) {
                        try {
                            double doubleValue = Double.valueOf(this.etInterestPeriod.getText().toString()).doubleValue();
                            double d = (double) this.termInterestOnly;
                            Double.isNaN(d);
                            Double.isNaN(d);
                            this.interestPeriod = doubleValue * d;
                        } catch (NumberFormatException e3) {
                            e3.printStackTrace();
                        }
                    } else {
                        this.interestPeriod = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                    }
                    if (!this.etTerms.getText().toString().trim().isEmpty()) {
                        double doubleValue2 = Double.valueOf(this.etTerms.getText().toString()).doubleValue();
                        double d2 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                        if (doubleValue2 != d2) {
                            if (this.interestPeriod != d2) {
                                double doubleValue22 = Double.valueOf(this.etTerms.getText().toString()).doubleValue();
                                double d22 = (double) this.term;
                                Double.isNaN(d22);
                                Double.isNaN(d22);
                                this.terms = (doubleValue22 * d22) - this.interestPeriod;
                            } else {
                                double doubleValue3 = Double.valueOf(this.etTerms.getText().toString()).doubleValue();
                                double d3 = (double) this.term;
                                Double.isNaN(d3);
                                Double.isNaN(d3);
                                this.terms = doubleValue3 * d3;
                            }
                            this.monthlyPayment = Utils.getMonthlyPayment(this.loanAmount, this.interestRate, this.terms);
                            return true;
                        }
                    }
                    this.etTerms.setError("Please fill out this field");
                    return false;
                }
            }
            this.etLoanAmount.setError("Please fill out this field");
            return false;
        } catch (NumberFormatException e4) {
            Toast.makeText(this, "Please enter valid decimal value", Toast.LENGTH_SHORT).show();
            e4.printStackTrace();
            return false;
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.interest_only_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.interest_only_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.interest_only_calculator));
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
                intent.setData(Uri.fromParts("package", InterestOnlyActivity.this.getPackageName(), (String) null));
                InterestOnlyActivity.this.startActivityForResult(intent, 112);
                InterestOnlyActivity.this.mMyDialog.dismiss();
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
                InterestOnlyActivity.this.calendar.set(5, i3);
                InterestOnlyActivity.this.calendar.set(2, i2);
                InterestOnlyActivity.this.calendar.set(1, i);
                InterestOnlyActivity interestOnlyActivity = InterestOnlyActivity.this;
                interestOnlyActivity.etDate.setText(AppConstant.getFormattedDate(interestOnlyActivity.calendar.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
            }
        }, this.mYear, this.mMonth, this.mDay).show();
    }
}
