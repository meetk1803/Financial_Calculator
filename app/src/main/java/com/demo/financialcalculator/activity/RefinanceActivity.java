package com.demo.financialcalculator.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.core.app.ActivityCompat;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;

import java.util.Calendar;

public class RefinanceActivity extends AppCompatActivity {
    Button btnCalculate;
    Button btnShare;
    Button btnStatistics;
    TextView clFinanceCharge;
    TextView clMonthlyPayment;
    TextView clTotalPayment;
    CommonModel commonModel;
    double d1;
    double d2;
    EditText etFees;
    EditText etInterestRate;
    EditText etInterestRate1;
    EditText etLoanBalance;
    EditText etMonthlyPayment;
    EditText etTerms;
    double fees;
    double interestRate;
    double interestRate1;
    double loanBalance;
    /* access modifiers changed from: private */
    public int mMonth;
    AlertDialog mMyDialog;
    /* access modifiers changed from: private */
    public int mYear;
    double monthlyPayment;
    double result;
    TextView rlFinanceCharge;
    TextView rlMonthlyPayment;
    TextView rlTotalPayment;
    ScrollView rootLayout;
    Spinner spTerm;
    /* access modifiers changed from: private */
    public int term;
    double terms;
    Toolbar toolBar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_refinance);
        init();
        setUpToolbar();
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.refinance_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RefinanceActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etLoanBalance = (EditText) findViewById(R.id.etLoanBalance);
        this.etMonthlyPayment = (EditText) findViewById(R.id.etMonthlyPayment);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etInterestRate1 = (EditText) findViewById(R.id.etInterestRate1);
        this.etFees = (EditText) findViewById(R.id.etFees);
        this.clMonthlyPayment = (TextView) findViewById(R.id.clMonthlyPayment);
        this.clTotalPayment = (TextView) findViewById(R.id.clTotalPayment);
        this.clFinanceCharge = (TextView) findViewById(R.id.clFinanceCharge);
        this.rlMonthlyPayment = (TextView) findViewById(R.id.rlMonthlyPayment);
        this.rlTotalPayment = (TextView) findViewById(R.id.rlTotalPayment);
        this.rlFinanceCharge = (TextView) findViewById(R.id.rlFinanceCharge);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        Calendar calendar = Calendar.getInstance();
        this.mYear = calendar.get(1);
        this.mMonth = calendar.get(2);
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
                        int unused = RefinanceActivity.this.term = 1;
                        return;
                    case 1:
                        int unused2 = RefinanceActivity.this.term = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RefinanceActivity.this.calculate();
                RefinanceActivity refinanceActivity = RefinanceActivity.this;
                refinanceActivity.clMonthlyPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(refinanceActivity.monthlyPayment)}));
                RefinanceActivity refinanceActivity2 = RefinanceActivity.this;
                refinanceActivity2.rlMonthlyPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(refinanceActivity2.result)}));
                RefinanceActivity refinanceActivity3 = RefinanceActivity.this;
                refinanceActivity3.rlTotalPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(refinanceActivity3.result * refinanceActivity3.d2)}));
                RefinanceActivity refinanceActivity4 = RefinanceActivity.this;
                refinanceActivity4.rlFinanceCharge.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format((refinanceActivity4.result * refinanceActivity4.d2) - refinanceActivity4.loanBalance)}));
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (RefinanceActivity.this.calculate()) {
                    RefinanceActivity refinanceActivity = RefinanceActivity.this;
                    refinanceActivity.commonModel.setPrincipalAmount(refinanceActivity.loanBalance);
                    RefinanceActivity refinanceActivity2 = RefinanceActivity.this;
                    refinanceActivity2.commonModel.setInterestRate(refinanceActivity2.interestRate);
                    if (RefinanceActivity.this.term == 1) {
                        RefinanceActivity refinanceActivity3 = RefinanceActivity.this;
                        refinanceActivity3.commonModel.setTerms((refinanceActivity3.terms + 1.0d) / 12.0d);
                    } else {
                        RefinanceActivity refinanceActivity4 = RefinanceActivity.this;
                        refinanceActivity4.commonModel.setTerms(refinanceActivity4.terms + 1.0d);
                    }
                    RefinanceActivity refinanceActivity5 = RefinanceActivity.this;
                    refinanceActivity5.commonModel.setYear(refinanceActivity5.mYear);
                    RefinanceActivity refinanceActivity6 = RefinanceActivity.this;
                    refinanceActivity6.commonModel.setMonth(refinanceActivity6.mMonth + 1);
                    RefinanceActivity refinanceActivity7 = RefinanceActivity.this;
                    refinanceActivity7.commonModel.setMonthlyPayment(refinanceActivity7.monthlyPayment);
                    Intent intent = new Intent(RefinanceActivity.this, RefinanceStatisticsActivity.class);
                    intent.putExtra("Refinance", RefinanceActivity.this.commonModel);
                    RefinanceActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RefinanceActivity.this.checkPermission();
            }
        });
    }

    public boolean calculate() {
        try {
            if (this.etLoanBalance.getText().toString().length() != 0) {
                double doubleValue = Double.valueOf(this.etLoanBalance.getText().toString()).doubleValue();
                double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                if (doubleValue != d) {
                    if (this.etMonthlyPayment.getText().toString().length() == 0 || Double.valueOf(this.etMonthlyPayment.getText().toString()).doubleValue() == d) {
                        this.etMonthlyPayment.setError("Please fill out this field");
                        return false;
                    } else if (this.etInterestRate.getText().toString().length() == 0 || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == d) {
                        this.etInterestRate.setError("Please fill out this field");
                        return false;
                    } else if (this.etTerms.getText().toString().length() == 0 || Double.valueOf(this.etTerms.getText().toString()).doubleValue() == d) {
                        this.etTerms.setError("Please fill out this field");
                        return false;
                    } else if (this.etInterestRate1.getText().toString().length() == 0 || Double.valueOf(this.etInterestRate1.getText().toString()).doubleValue() == d) {
                        this.etInterestRate1.setError("Please fill out this field");
                        return false;
                    } else {
                        if (!this.etFees.getText().toString().trim().isEmpty()) {
                            this.fees = Double.parseDouble(this.etFees.getText().toString());
                        }
                        this.loanBalance = Double.parseDouble(this.etLoanBalance.getText().toString());
                        this.monthlyPayment = Double.parseDouble(this.etMonthlyPayment.getText().toString());
                        this.interestRate = Double.parseDouble(this.etInterestRate.getText().toString());
                        this.terms = Double.parseDouble(this.etTerms.getText().toString());
                        double parseDouble = Double.parseDouble(this.etInterestRate1.getText().toString());
                        this.interestRate1 = parseDouble;
                        this.d1 = (parseDouble / 100.0d) / 12.0d;
                        double d3 = this.terms;
                        double d22 = (double) this.term;
                        Double.isNaN(d22);
                        Double.isNaN(d22);
                        double d4 = d3 * d22;
                        this.d2 = d4;
                        double d5 = this.loanBalance;
                        double d6 = this.d1;
                        this.result = ((d5 * d6) * Math.pow(d6 + 1.0d, d4)) / (Math.pow(this.d1 + 1.0d, this.d2) - 1.0d);
                        return true;
                    }
                }
            }
            this.etLoanBalance.setError("Please fill out this field");
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
                ShareUtil.print(this, this.rootLayout, getString(R.string.refinance_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.refinance_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.refinance_calculator));
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
                intent.setData(Uri.fromParts("package", RefinanceActivity.this.getPackageName(), (String) null));
                RefinanceActivity.this.startActivityForResult(intent, 112);
                RefinanceActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
