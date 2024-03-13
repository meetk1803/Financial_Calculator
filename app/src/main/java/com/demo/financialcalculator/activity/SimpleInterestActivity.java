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
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.model.GraphModel;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.GraphUtils;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.Calendar;

public class SimpleInterestActivity extends AppCompatActivity {
    Button btnCalculate;
    Button btnShare;
    Button btnStatistics;
    Calendar calendar;
    CommonModel commonModel;
    TextView etDate;
    EditText etInterestRate;
    EditText etPrincipal;
    EditText etTerms;
    GraphModel graphModel;
    ArrayList<GraphModel> graphModelArrayList;
    LinearLayout graphlayot;
    double interestAmount;
    double interestRate;
    CardView llResult;
    int mDay;
    int mMonth;
    AlertDialog mMyDialog;
    int mYear;
    PieChart pieChart;
    double principal;
    double principalAmount;
    ScrollView rootLayout;
    Spinner spTerm;
    int term;
    double terms;
    Toolbar toolBar;
    TextView txtInterest;
    TextView txtPrincipal;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_simple_interest);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.simple_interest_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SimpleInterestActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.graphModelArrayList = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etPrincipal = (EditText) findViewById(R.id.etPrincipal);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etDate = (TextView) findViewById(R.id.etDate);
        this.graphlayot = (LinearLayout) findViewById(R.id.graphlayot);
        this.txtPrincipal = (TextView) findViewById(R.id.txtPrincipal);
        this.txtInterest = (TextView) findViewById(R.id.txtInterest);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        this.pieChart = (PieChart) findViewById(R.id.piechart);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.calendar = Calendar.getInstance();
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        this.etDate.setText(AppConstant.getFormattedDate(instance.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
        this.etDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SimpleInterestActivity.this.startDateDialog();
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
                        SimpleInterestActivity.this.term = 12;
                        return;
                    case 1:
                        SimpleInterestActivity.this.term = 1;
                        return;
                    default:
                        return;
                }
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SimpleInterestActivity.this.setValue()) {
                    AppConstant.hideKeyboard(SimpleInterestActivity.this);
                    AppConstant.visibleResult(SimpleInterestActivity.this.llResult);
                    AppConstant.visibleGraph(SimpleInterestActivity.this.graphlayot);
                }
                SimpleInterestActivity simpleInterestActivity = SimpleInterestActivity.this;
                simpleInterestActivity.txtPrincipal.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(simpleInterestActivity.principal)}));
                SimpleInterestActivity simpleInterestActivity2 = SimpleInterestActivity.this;
                simpleInterestActivity2.txtInterest.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(simpleInterestActivity2.interestAmount)}));
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SimpleInterestActivity.this.setValue()) {
                    SimpleInterestActivity simpleInterestActivity = SimpleInterestActivity.this;
                    simpleInterestActivity.commonModel.setPrincipalAmount(simpleInterestActivity.principal);
                    SimpleInterestActivity simpleInterestActivity2 = SimpleInterestActivity.this;
                    simpleInterestActivity2.commonModel.setTerms(simpleInterestActivity2.terms);
                    SimpleInterestActivity simpleInterestActivity3 = SimpleInterestActivity.this;
                    simpleInterestActivity3.commonModel.setInterestAmount(simpleInterestActivity3.principalAmount);
                    SimpleInterestActivity simpleInterestActivity4 = SimpleInterestActivity.this;
                    simpleInterestActivity4.commonModel.setYear(simpleInterestActivity4.calendar.get(1));
                    Intent intent = new Intent(SimpleInterestActivity.this, SimpleInterestStatisticsActivity.class);
                    intent.putExtra("SimpleInterest", SimpleInterestActivity.this.commonModel);
                    SimpleInterestActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SimpleInterestActivity.this.checkPermission();
            }
        });
    }

    public boolean setValue() {
        try {
            if (!this.etPrincipal.getText().toString().trim().isEmpty()) {
                double doubleValue = Double.valueOf(this.etPrincipal.getText().toString()).doubleValue();
                double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                if (doubleValue != d) {
                    this.principal = Double.valueOf(this.etPrincipal.getText().toString()).doubleValue();
                    if (this.etInterestRate.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == d) {
                        this.etInterestRate.setError("Please fill out this field");
                        return false;
                    }
                    this.interestRate = Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() / 100.0d;
                    if (this.etTerms.getText().toString().trim().isEmpty() || Double.valueOf(this.etTerms.getText().toString()).doubleValue() == d) {
                        this.etTerms.setError("Please fill out this field");
                        return false;
                    }
                    double doubleValue2 = Double.valueOf(this.etTerms.getText().toString()).doubleValue();
                    double d2 = (double) this.term;
                    Double.isNaN(d2);
                    Double.isNaN(d2);
                    double d3 = doubleValue2 / d2;
                    this.terms = d3;
                    double d4 = this.principal;
                    double d5 = this.interestRate * d4;
                    this.principalAmount = d5;
                    double d6 = d5 * d3;
                    this.interestAmount = d6;
                    setPieChart(d4, d6);
                    return true;
                }
            }
            this.etPrincipal.setError("Please fill out this field");
            return false;
        } catch (Exception e) {
            Toast.makeText(this, "Please enter valid decimal value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    private void setPieChart(double d, double d2) {
        ArrayList<GraphModel> arrayList = this.graphModelArrayList;
        if (arrayList != null && arrayList.size() > 0) {
            this.graphModelArrayList.clear();
        }
        GraphModel graphModel2 = new GraphModel(getResources().getString(R.string.principalamount) + "\n(" + Utils.decimalFormat.format(d) + ")", d, getResources().getColor(R.color.graphcolor1));
        this.graphModel = graphModel2;
        this.graphModelArrayList.add(graphModel2);
        GraphModel graphModel3 = new GraphModel(getResources().getString(R.string.interestamount) + "\n(" + Utils.decimalFormat.format(d2) + ")", d2, getResources().getColor(R.color.graphcolor2));
        this.graphModel = graphModel3;
        this.graphModelArrayList.add(graphModel3);
        new GraphUtils(this.pieChart, this.graphModelArrayList, getApplicationContext()).setupPieData();
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.simple_interest_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.simple_interest_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.simple_interest_calculator));
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
                intent.setData(Uri.fromParts("package", SimpleInterestActivity.this.getPackageName(), (String) null));
                SimpleInterestActivity.this.startActivityForResult(intent, 112);
                SimpleInterestActivity.this.mMyDialog.dismiss();
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
                SimpleInterestActivity.this.calendar.set(5, i3);
                SimpleInterestActivity.this.calendar.set(2, i2);
                SimpleInterestActivity.this.calendar.set(1, i);
                SimpleInterestActivity simpleInterestActivity = SimpleInterestActivity.this;
                simpleInterestActivity.etDate.setText(AppConstant.getFormattedDate(simpleInterestActivity.calendar.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
            }
        }, this.mYear, this.mMonth, this.mDay).show();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
