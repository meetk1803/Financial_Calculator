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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;

import java.text.DecimalFormat;

public class LoanComparisonActivity extends AppCompatActivity {
    Button btnCalculate;
    Button btnReset;
    Button btnShare;
    Button btnStatistics;
    CommonModel commonModel;
    EditText etInterestRate1;
    EditText etInterestRate2;
    EditText etLoanAmount1;
    EditText etLoanAmount2;
    EditText etProcessingFee1;
    EditText etProcessingFee2;
    EditText etTerm1;
    EditText etTerm2;
    double interestRate1;
    double interestRate2;
    CardView llResult;
    double loanAmount1;
    double loanAmount2;
    int mMonth;
    AlertDialog mMyDialog;
    int mYear;
    double monthlyPayment1;
    double monthlyPayment2;
    double processingFee1;
    double processingFee2;
    ScrollView rootLayout;
    Spinner spTerm1;
    Spinner spTerm2;
    int term1;
    int term2;
    double terms1;
    double terms2;
    Toolbar toolBar;
    TextView txtInterest1;
    TextView txtInterest2;
    TextView txtMonthlyPayment1;
    TextView txtMonthlyPayment2;
    TextView txtProcessingFee1;
    TextView txtProcessingFee2;
    TextView txtTotalPayments1;
    TextView txtTotalPayments2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_loan_comparison);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.loan_comparison_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoanComparisonActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etLoanAmount1 = (EditText) findViewById(R.id.etLoanAmount1);
        this.etLoanAmount2 = (EditText) findViewById(R.id.etLoanAmount2);
        this.etInterestRate1 = (EditText) findViewById(R.id.etInterestRate1);
        this.etInterestRate2 = (EditText) findViewById(R.id.etInterestRate2);
        this.etTerm1 = (EditText) findViewById(R.id.etTerm1);
        this.etTerm2 = (EditText) findViewById(R.id.etTerm2);
        this.etProcessingFee1 = (EditText) findViewById(R.id.etProcessingFee1);
        this.etProcessingFee2 = (EditText) findViewById(R.id.etProcessingFee2);
        this.txtMonthlyPayment1 = (TextView) findViewById(R.id.txtMonthlyPayment1);
        this.txtMonthlyPayment2 = (TextView) findViewById(R.id.txtMonthlyPayment2);
        this.txtTotalPayments1 = (TextView) findViewById(R.id.txtTotalPayments1);
        this.txtTotalPayments2 = (TextView) findViewById(R.id.txtTotalPayments2);
        this.txtInterest1 = (TextView) findViewById(R.id.txtInterest1);
        this.txtInterest2 = (TextView) findViewById(R.id.txtInterest2);
        this.txtProcessingFee1 = (TextView) findViewById(R.id.txtProcessingFee1);
        this.txtProcessingFee2 = (TextView) findViewById(R.id.txtProcessingFee2);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.btnReset = (Button) findViewById(R.id.btnReset);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.spTerm1 = (Spinner) findViewById(R.id.spTerm1);
        this.spTerm2 = (Spinner) findViewById(R.id.spTerm2);
        this.llResult = (CardView) findViewById(R.id.llResult);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.terms_array));
        arrayAdapter.setDropDownViewResource(17367049);
        this.spTerm1.setAdapter(arrayAdapter);
        this.spTerm1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        LoanComparisonActivity.this.term1 = 1;
                        return;
                    case 1:
                        LoanComparisonActivity.this.term1 = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.terms_array));
        arrayAdapter2.setDropDownViewResource(17367049);
        this.spTerm2.setAdapter(arrayAdapter2);
        this.spTerm2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        LoanComparisonActivity.this.term2 = 1;
                        return;
                    case 1:
                        LoanComparisonActivity.this.term2 = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        click();
    }

    private void click() {
        this.btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!LoanComparisonActivity.this.etLoanAmount1.getText().toString().trim().isEmpty()) {
                    LoanComparisonActivity.this.etLoanAmount1.setText("");
                }
                if (!LoanComparisonActivity.this.etLoanAmount2.getText().toString().trim().isEmpty()) {
                    LoanComparisonActivity.this.etLoanAmount2.setText("");
                }
                if (!LoanComparisonActivity.this.etInterestRate1.getText().toString().trim().isEmpty()) {
                    LoanComparisonActivity.this.etInterestRate1.setText("");
                }
                if (!LoanComparisonActivity.this.etInterestRate2.getText().toString().trim().isEmpty()) {
                    LoanComparisonActivity.this.etInterestRate2.setText("");
                }
                if (!LoanComparisonActivity.this.etTerm1.getText().toString().trim().isEmpty()) {
                    LoanComparisonActivity.this.etTerm1.setText("");
                }
                if (!LoanComparisonActivity.this.etTerm2.getText().toString().trim().isEmpty()) {
                    LoanComparisonActivity.this.etTerm2.setText("");
                }
                if (!LoanComparisonActivity.this.etProcessingFee1.getText().toString().trim().isEmpty()) {
                    LoanComparisonActivity.this.etProcessingFee1.setText("");
                }
                if (!LoanComparisonActivity.this.etProcessingFee2.getText().toString().trim().isEmpty()) {
                    LoanComparisonActivity.this.etProcessingFee2.setText("");
                }
                LoanComparisonActivity.this.txtMonthlyPayment1.setText("");
                LoanComparisonActivity.this.txtMonthlyPayment2.setText("");
                LoanComparisonActivity.this.txtTotalPayments1.setText("");
                LoanComparisonActivity.this.txtTotalPayments2.setText("");
                LoanComparisonActivity.this.txtInterest1.setText("");
                LoanComparisonActivity.this.txtInterest2.setText("");
                LoanComparisonActivity.this.txtProcessingFee1.setText("");
                LoanComparisonActivity.this.txtProcessingFee2.setText("");
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (LoanComparisonActivity.this.setValue()) {
                    AppConstant.hideKeyboard(LoanComparisonActivity.this);
                    AppConstant.visibleResult(LoanComparisonActivity.this.llResult);
                }
                LoanComparisonActivity loanComparisonActivity = LoanComparisonActivity.this;
                loanComparisonActivity.txtMonthlyPayment1.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(loanComparisonActivity.monthlyPayment1)}));
                LoanComparisonActivity loanComparisonActivity2 = LoanComparisonActivity.this;
                loanComparisonActivity2.txtMonthlyPayment2.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(loanComparisonActivity2.monthlyPayment2)}));
                LoanComparisonActivity loanComparisonActivity3 = LoanComparisonActivity.this;
                TextView textView = loanComparisonActivity3.txtTotalPayments1;
                DecimalFormat decimalFormat = Utils.decimalFormat;
                double d = loanComparisonActivity3.monthlyPayment1;
                double d2 = loanComparisonActivity3.terms1;
                double d3 = (double) loanComparisonActivity3.term1;
                Double.isNaN(d3);
                Double.isNaN(d3);
                textView.setText(String.format("$%s", new Object[]{decimalFormat.format(d * d2 * d3)}));
                LoanComparisonActivity loanComparisonActivity4 = LoanComparisonActivity.this;
                TextView textView2 = loanComparisonActivity4.txtTotalPayments2;
                DecimalFormat decimalFormat2 = Utils.decimalFormat;
                DecimalFormat decimalFormat3 = decimalFormat;
                TextView textView3 = textView;
                double d4 = loanComparisonActivity4.monthlyPayment2 * loanComparisonActivity4.terms2;
                double d5 = (double) loanComparisonActivity4.term2;
                Double.isNaN(d5);
                Double.isNaN(d5);
                double d6 = d;
                textView2.setText(String.format("$%s", new Object[]{decimalFormat2.format(d4 * d5)}));
                LoanComparisonActivity loanComparisonActivity5 = LoanComparisonActivity.this;
                TextView textView32 = loanComparisonActivity5.txtInterest1;
                DecimalFormat decimalFormat32 = Utils.decimalFormat;
                double d7 = d4;
                double d62 = loanComparisonActivity5.monthlyPayment1;
                double d8 = d5;
                double d72 = loanComparisonActivity5.terms1;
                double d82 = (double) loanComparisonActivity5.term1;
                Double.isNaN(d82);
                double d9 = d2;
                Double.isNaN(d82);
                double d10 = d72;
                textView32.setText(String.format("$%s", new Object[]{decimalFormat32.format(Utils.getTotalInterest(d62, d72 * d82, LoanComparisonActivity.this.loanAmount1))}));
                LoanComparisonActivity loanComparisonActivity6 = LoanComparisonActivity.this;
                TextView textView4 = loanComparisonActivity6.txtInterest2;
                DecimalFormat decimalFormat4 = Utils.decimalFormat;
                double d11 = d62;
                double d63 = loanComparisonActivity6.monthlyPayment2;
                double d12 = d82;
                double d102 = loanComparisonActivity6.terms2;
                DecimalFormat decimalFormat5 = decimalFormat32;
                double d112 = (double) loanComparisonActivity6.term2;
                Double.isNaN(d112);
                TextView textView5 = textView32;
                Double.isNaN(d112);
                double d13 = d112;
                textView4.setText(String.format("$%s", new Object[]{decimalFormat4.format(Utils.getTotalInterest(d63, d102 * d112, LoanComparisonActivity.this.loanAmount2))}));
                LoanComparisonActivity loanComparisonActivity7 = LoanComparisonActivity.this;
                loanComparisonActivity7.txtProcessingFee1.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(loanComparisonActivity7.processingFee1)}));
                LoanComparisonActivity loanComparisonActivity8 = LoanComparisonActivity.this;
                double d14 = d102;
                loanComparisonActivity8.txtProcessingFee2.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(loanComparisonActivity8.processingFee2)}));
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (LoanComparisonActivity.this.setValue()) {
                    LoanComparisonActivity loanComparisonActivity = LoanComparisonActivity.this;
                    loanComparisonActivity.commonModel.setPrincipalAmount(loanComparisonActivity.loanAmount1);
                    LoanComparisonActivity loanComparisonActivity2 = LoanComparisonActivity.this;
                    loanComparisonActivity2.commonModel.setInterestRate(loanComparisonActivity2.interestRate1);
                    LoanComparisonActivity loanComparisonActivity3 = LoanComparisonActivity.this;
                    if (loanComparisonActivity3.term1 == 1) {
                        loanComparisonActivity3.commonModel.setTerms(loanComparisonActivity3.terms1 / 12.0d);
                    } else {
                        loanComparisonActivity3.commonModel.setTerms(loanComparisonActivity3.terms1);
                    }
                    LoanComparisonActivity loanComparisonActivity4 = LoanComparisonActivity.this;
                    loanComparisonActivity4.commonModel.setMonthlyPayment(loanComparisonActivity4.monthlyPayment1);
                    LoanComparisonActivity loanComparisonActivity5 = LoanComparisonActivity.this;
                    loanComparisonActivity5.commonModel.setPrincipalAmount2(loanComparisonActivity5.loanAmount2);
                    LoanComparisonActivity loanComparisonActivity6 = LoanComparisonActivity.this;
                    loanComparisonActivity6.commonModel.setInterestRate2(loanComparisonActivity6.interestRate2);
                    LoanComparisonActivity loanComparisonActivity7 = LoanComparisonActivity.this;
                    if (loanComparisonActivity7.term2 == 1) {
                        loanComparisonActivity7.commonModel.setTerms2(loanComparisonActivity7.terms2 / 12.0d);
                    } else {
                        loanComparisonActivity7.commonModel.setTerms2(loanComparisonActivity7.terms2);
                    }
                    LoanComparisonActivity loanComparisonActivity8 = LoanComparisonActivity.this;
                    loanComparisonActivity8.commonModel.setMonthlyPayment2(loanComparisonActivity8.monthlyPayment2);
                    LoanComparisonActivity loanComparisonActivity9 = LoanComparisonActivity.this;
                    loanComparisonActivity9.commonModel.setMonth(loanComparisonActivity9.mMonth);
                    LoanComparisonActivity loanComparisonActivity10 = LoanComparisonActivity.this;
                    loanComparisonActivity10.commonModel.setYear(loanComparisonActivity10.mYear);
                    LoanComparisonActivity.this.commonModel.setDate(System.currentTimeMillis());
                    Intent intent = new Intent(LoanComparisonActivity.this, RefinanceStatisticsActivity.class);
                    intent.putExtra("LoanComparison", LoanComparisonActivity.this.commonModel);
                    LoanComparisonActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoanComparisonActivity.this.checkPermission();
            }
        });
    }

    public boolean setValue() {
        try {
            if (!this.etLoanAmount1.getText().toString().trim().isEmpty()) {
                double doubleValue = Double.valueOf(this.etLoanAmount1.getText().toString()).doubleValue();
                double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                if (doubleValue != d) {
                    this.loanAmount1 = Double.valueOf(this.etLoanAmount1.getText().toString()).doubleValue();
                    if (this.etLoanAmount2.getText().toString().trim().isEmpty() || Double.valueOf(this.etLoanAmount2.getText().toString()).doubleValue() == d) {
                        this.etLoanAmount2.setError("Please fill out this field");
                        return false;
                    }
                    this.loanAmount2 = Double.valueOf(this.etLoanAmount2.getText().toString()).doubleValue();
                    if (this.etInterestRate1.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate1.getText().toString()).doubleValue() == d) {
                        this.etInterestRate1.setError("Please fill out this field");
                        return false;
                    }
                    this.interestRate1 = Double.valueOf(this.etInterestRate1.getText().toString()).doubleValue();
                    if (this.etInterestRate2.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate2.getText().toString()).doubleValue() == d) {
                        this.etInterestRate2.setError("Please fill out this field");
                        return false;
                    }
                    this.interestRate2 = Double.valueOf(this.etInterestRate2.getText().toString()).doubleValue();
                    if (this.etTerm1.getText().toString().trim().isEmpty() || Double.valueOf(this.etTerm1.getText().toString()).doubleValue() == d) {
                        this.etTerm1.setError("Please fill out this field");
                        return false;
                    }
                    this.terms1 = Double.valueOf(this.etTerm1.getText().toString()).doubleValue();
                    if (this.etTerm2.getText().toString().trim().isEmpty() || Double.valueOf(this.etTerm2.getText().toString()).doubleValue() == d) {
                        this.etTerm2.setError("Please fill out this field");
                        return false;
                    }
                    this.terms2 = Double.valueOf(this.etTerm2.getText().toString()).doubleValue();
                    if (!this.etProcessingFee1.getText().toString().trim().isEmpty()) {
                        this.processingFee1 = (this.loanAmount1 * Double.valueOf(this.etProcessingFee1.getText().toString()).doubleValue()) / 100.0d;
                    }
                    if (!this.etProcessingFee2.getText().toString().trim().isEmpty()) {
                        this.processingFee2 = (this.loanAmount2 * Double.valueOf(this.etProcessingFee2.getText().toString()).doubleValue()) / 100.0d;
                    }
                    double d2 = this.loanAmount1;
                    double d22 = this.interestRate1;
                    double d3 = this.terms1;
                    double d4 = (double) this.term1;
                    Double.isNaN(d4);
                    Double.isNaN(d4);
                    this.monthlyPayment1 = Utils.getMonthlyPayment(d2, d22, d3 * d4);
                    double d5 = this.loanAmount2;
                    double d6 = this.interestRate2;
                    double d7 = this.terms2;
                    double d8 = d3;
                    double d82 = (double) this.term2;
                    Double.isNaN(d82);
                    Double.isNaN(d82);
                    double d9 = d82;
                    this.monthlyPayment2 = Utils.getMonthlyPayment(d5, d6, d82 * d7);
                    return true;
                }
            }
            this.etLoanAmount1.setError("Please fill out this field");
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
                ShareUtil.print(this, this.rootLayout, getString(R.string.loan_comparison_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.loan_comparison_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.loan_comparison_calculator));
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
                intent.setData(Uri.fromParts("package", LoanComparisonActivity.this.getPackageName(), (String) null));
                LoanComparisonActivity.this.startActivityForResult(intent, 112);
                LoanComparisonActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }
}
