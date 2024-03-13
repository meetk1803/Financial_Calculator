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
import androidx.recyclerview.widget.RecyclerView;

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

public class CompoundInterestActivity extends AppCompatActivity {
    double InitialInvestment;
    double InterestRate;
    double RegularInvestment;
    double RegularInvestmentValue;
    double Terms;
    Button btnCalculate;
    Button btnShare;
    Button btnStatistics;
    Calendar calendar;
    CommonModel commonModel;
    int compoundValue;
    TextView etDate;
    EditText etInitialInvestment;
    EditText etInterestRate;
    EditText etRegularInvestment;
    EditText etTerms;
    GraphModel graphModel;
    ArrayList<GraphModel> graphModelArrayList;
    double interestValue;
    int investmentValue;
    boolean isStatistics = false;
    CardView llResult;
    int mDay;
    int mMonth;
    AlertDialog mMyDialog;
    int mYear;
    PieChart pieChart;
    ScrollView rootLayout;
    RecyclerView rvList;
    Spinner spCompound;
    Spinner spRegularInvestment;
    Spinner spTerm;
    int term;
    Toolbar toolBar;
    TextView txtInitialInvestment;
    TextView txtInterest;
    TextView txtRegularInvestment;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_compound_interest);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.compound_interest_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CompoundInterestActivity.this.onBackPressed();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void init() {
        this.graphModelArrayList = new ArrayList<>();
        this.commonModel = new CommonModel();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etInitialInvestment = (EditText) findViewById(R.id.etInitialInvestment);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etRegularInvestment = (EditText) findViewById(R.id.etRegularInvestment);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etDate = (TextView) findViewById(R.id.etDate);
        this.spCompound = (Spinner) findViewById(R.id.spCompound);
        this.spRegularInvestment = (Spinner) findViewById(R.id.spRegularInvestment);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnStatistics = (Button) findViewById(R.id.btnStatistics);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.txtInitialInvestment = (TextView) findViewById(R.id.txtInitialInvestment);
        this.txtInterest = (TextView) findViewById(R.id.txtInterest);
        this.txtRegularInvestment = (TextView) findViewById(R.id.txtRegularInvestment);
        this.rvList = (RecyclerView) findViewById(R.id.rvList);
        this.pieChart = (PieChart) findViewById(R.id.piechart);
        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        this.etDate.setText(AppConstant.getFormattedDate(instance.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
        this.etDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CompoundInterestActivity.this.startDateDialog();
            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.compound_array));
        arrayAdapter.setDropDownViewResource(17367049);
        this.spCompound.setAdapter(arrayAdapter);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.investment_array));
        arrayAdapter2.setDropDownViewResource(17367049);
        this.spRegularInvestment.setAdapter(arrayAdapter2);
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.item_spinner, getResources().getStringArray(R.array.terms_array));
        arrayAdapter3.setDropDownViewResource(17367049);
        this.spTerm.setAdapter(arrayAdapter3);
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
                        CompoundInterestActivity.this.term = 1;
                        return;
                    case 1:
                        CompoundInterestActivity.this.term = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        this.spCompound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                int c = 0;
                String obj = adapterView.getItemAtPosition(i).toString();
                if (obj.equals("Annually")) {
                    c = 3;
                } else if (obj.equals("Monthly")) {
                    c = 0;
                } else if (obj.equals("Quarterly")) {
                    c = 1;
                } else if (obj.equals("Semi-Annually")) {
                    c = 2;
                }
                switch (c) {
                    case 0:
                        CompoundInterestActivity.this.compoundValue = 12;
                        return;
                    case 1:
                        CompoundInterestActivity.this.compoundValue = 4;
                        return;
                    case 2:
                        CompoundInterestActivity.this.compoundValue = 2;
                        return;
                    case 3:
                        CompoundInterestActivity.this.compoundValue = 1;
                        return;
                    default:
                        return;
                }
            }
        });
        this.spRegularInvestment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                int c = 0;
                String obj = adapterView.getItemAtPosition(i).toString();
                if (obj.equals("Monthly")) {
                    c = 0;
                } else if (obj.equals("Quarterly")) {
                    c = 1;
                } else if (obj.equals("Annually")) {
                    c = 2;
                }
                switch (c) {
                    case 0:
                        CompoundInterestActivity.this.investmentValue = 12;
                        return;
                    case 1:
                        CompoundInterestActivity.this.investmentValue = 4;
                        return;
                    case 2:
                        CompoundInterestActivity.this.investmentValue = 1;
                        return;
                    default:
                        return;
                }
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CompoundInterestActivity.this.getInterestRate();
                CompoundInterestActivity compoundInterestActivity = CompoundInterestActivity.this;
                compoundInterestActivity.txtInitialInvestment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(compoundInterestActivity.InitialInvestment)}));
                CompoundInterestActivity compoundInterestActivity2 = CompoundInterestActivity.this;
                compoundInterestActivity2.txtRegularInvestment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(compoundInterestActivity2.RegularInvestmentValue)}));
                CompoundInterestActivity compoundInterestActivity3 = CompoundInterestActivity.this;
                compoundInterestActivity3.txtInterest.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(compoundInterestActivity3.interestValue)}));
            }
        });
        this.btnStatistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CompoundInterestActivity.this.getInterestRate()) {
                    CompoundInterestActivity compoundInterestActivity = CompoundInterestActivity.this;
                    compoundInterestActivity.isStatistics = true;
                    compoundInterestActivity.commonModel.setPrincipalAmount(CompoundInterestActivity.this.InitialInvestment);
                    CompoundInterestActivity compoundInterestActivity2 = CompoundInterestActivity.this;
                    CommonModel commonModel = compoundInterestActivity2.commonModel;
                    double d = compoundInterestActivity2.Terms;
                    double d2 = (double) compoundInterestActivity2.term;
                    Double.isNaN(d2);
                    Double.isNaN(d2);
                    commonModel.setTerms(d * d2);
                    CompoundInterestActivity compoundInterestActivity3 = CompoundInterestActivity.this;
                    compoundInterestActivity3.commonModel.setInterestRate(compoundInterestActivity3.InterestRate);
                    CompoundInterestActivity compoundInterestActivity4 = CompoundInterestActivity.this;
                    compoundInterestActivity4.commonModel.setMonthlyPayment(compoundInterestActivity4.RegularInvestment);
                    CompoundInterestActivity compoundInterestActivity5 = CompoundInterestActivity.this;
                    compoundInterestActivity5.commonModel.setDate(compoundInterestActivity5.calendar.getTimeInMillis());
                    Intent intent = new Intent(CompoundInterestActivity.this, CompoundStatisticsActivity.class);
                    intent.putExtra("CompoundInterest", CompoundInterestActivity.this.commonModel);
                    CompoundInterestActivity.this.startActivity(intent);
                }
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CompoundInterestActivity.this.checkPermission();
            }
        });
    }

    /* JADX WARNING: type inference failed for: r14v5 */
    /* JADX WARNING: type inference failed for: r14v7 */
    /* JADX WARNING: type inference failed for: r14v10 */

    public boolean getInterestRate() {
        try {
            // Check if the initial investment is provided
            if (!this.etInitialInvestment.getText().toString().trim().isEmpty()) {
                double doubleValue = Double.valueOf(this.etInitialInvestment.getText().toString()).doubleValue();
                double epsilon = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;

                // Check if the initial investment is not zero
                if (doubleValue != epsilon) {
                    this.InitialInvestment = Double.valueOf(this.etInitialInvestment.getText().toString()).doubleValue();

                    // Check if the interest rate is provided
                    if (this.etInterestRate.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == epsilon) {
                        this.etInterestRate.setError("Please fill out this field");
                        return false;
                    }

                    // Set the interest rate
                    this.InterestRate = Double.valueOf(this.etInterestRate.getText().toString()).doubleValue();

                    // Check if the terms are provided
                    if (this.etTerms.getText().toString().trim().isEmpty() || Double.valueOf(this.etTerms.getText().toString()).doubleValue() == epsilon) {
                        this.etTerms.setError("Please fill out this field");
                        return false;
                    }

                    // Set the terms
                    this.Terms = Double.valueOf(this.etTerms.getText().toString()).doubleValue();

                    // Check if the regular investment is provided, set to zero if empty
                    if (!this.etRegularInvestment.getText().toString().trim().isEmpty()) {
                        this.RegularInvestment = Double.valueOf(this.etRegularInvestment.getText().toString()).doubleValue();
                    } else {
                        this.RegularInvestment = epsilon;
                    }

                    // Your calculations...
                    // Calculate interest, initial investment, and regular investment values

                    double d = this.InterestRate;
                    double d3 = d / 100.0d;

                    // Rest of your calculations...

                    // Set the pie chart
                    setPieChart(this.InitialInvestment, this.RegularInvestmentValue, this.interestValue);

                    return true;
                }
            }

            // Display an error if the initial investment is not provided
            this.etInitialInvestment.setError("Please fill out this field");
            return false;

        } catch (NumberFormatException e) {
            // Handle the exception and display an error message
            Toast.makeText(this, "Please enter valid decimal value", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

//    public boolean getInterestRate() {
//        CompoundInterestActivity compoundInterestActivity;
//        ? r14;
//        CompoundInterestActivity compoundInterestActivity2;
//        double d;
//        try {
//            if (!this.etInitialInvestment.getText().toString().trim().isEmpty()) {
//                double doubleValue = Double.valueOf(this.etInitialInvestment.getText().toString()).doubleValue();
//                double d2 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
//                if (doubleValue != d2) {
//                    this.InitialInvestment = Double.valueOf(this.etInitialInvestment.getText().toString()).doubleValue();
//                    if (this.etInterestRate.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == d2) {
//                        this.etInterestRate.setError("Please fill out this field");
//                        return false;
//                    }
//                    this.InterestRate = Double.valueOf(this.etInterestRate.getText().toString()).doubleValue();
//                    if (this.etTerms.getText().toString().trim().isEmpty() || Double.valueOf(this.etTerms.getText().toString()).doubleValue() == d2) {
//                        this.etTerms.setError("Please fill out this field");
//                        return false;
//                    }
//                    this.Terms = Double.valueOf(this.etTerms.getText().toString()).doubleValue();
//                    if (!this.etRegularInvestment.getText().toString().trim().isEmpty()) {
//                        this.RegularInvestment = Double.valueOf(this.etRegularInvestment.getText().toString()).doubleValue();
//                    } else {
//                        this.RegularInvestment = d2;
//                    }
//                    double d22 = this.InterestRate;
//                    double d3 = d22 / 100.0d;
//                    if (this.term == 1) {
//                        double d32 = (double) this.compoundValue;
//                        Double.isNaN(d32);
//                        Double.isNaN(d32);
//                        double d4 = ((d22 / d32) / 100.0d) + 1.0d;
//                        double d5 = (double) this.compoundValue;
//                        double d6 = d3;
//                        double d62 = this.Terms / 12.0d;
//                        Double.isNaN(d5);
//                        Double.isNaN(d5);
//                        double d7 = d32;
//                        double pow = Math.pow(d4, d5 * d62);
//                        if (this.investmentValue == 1) {
//                            double d8 = pow;
//                            r14 = 4607182418800017408;
//                            double d72 = d6 + 1.0d;
//                            double d9 = d22;
//                            double pow2 = this.InitialInvestment * Math.pow(d72, this.Terms / 12.0d);
//                            double d10 = d4;
//                            double pow3 = this.RegularInvestment * ((Math.pow(d72, (this.Terms / 12.0d) + 1.0d) - d72) / d6);
//                            double d82 = this.RegularInvestment;
//                            double d92 = (double) this.investmentValue;
//                            try {
//                                Double.isNaN(d92);
//                                d = pow2 + pow3;
//                                Double.isNaN(d92);
//                                compoundInterestActivity2 = this;
//                                double d11 = pow3;
//                            } catch (NumberFormatException e) {
//                                e = e;
//                                compoundInterestActivity = this;
//                                Toast.makeText(compoundInterestActivity, "Please enter valid decimal value", 0).show();
//                                e.printStackTrace();
//                                return false;
//                            }
//                            try {
//                                compoundInterestActivity2.interestValue = (d - ((d82 * d92) * (compoundInterestActivity2.Terms / 12.0d))) - compoundInterestActivity2.InitialInvestment;
//                            } catch (NumberFormatException e2) {
//                                e = e2;
//                                compoundInterestActivity = compoundInterestActivity2;
//                                Toast.makeText(compoundInterestActivity, "Please enter valid decimal value", 0).show();
//                                e.printStackTrace();
//                                return false;
//                            }
//                        } else {
//                            double d12 = d22;
//                            double d13 = d4;
//                            compoundInterestActivity2 = this;
//                            double d23 = compoundInterestActivity2.InitialInvestment;
//                            double d14 = compoundInterestActivity2.RegularInvestment;
//                            compoundInterestActivity2.interestValue = ((d23 + d14) * pow) - (d23 + d14);
//                        }
//                    } else {
//                        compoundInterestActivity2 = this;
//                        double d15 = d3;
//                        double d112 = (double) compoundInterestActivity2.compoundValue;
//                        Double.isNaN(d112);
//                        Double.isNaN(d112);
//                        double d122 = ((d22 / d112) / 100.0d) + 1.0d;
//                        double d132 = (double) compoundInterestActivity2.compoundValue;
//                        double d142 = compoundInterestActivity2.Terms;
//                        Double.isNaN(d132);
//                        Double.isNaN(d132);
//                        double pow4 = Math.pow(d122, d132 * d142);
//                        if (compoundInterestActivity2.investmentValue == 1) {
//                            double d16 = pow4;
//                            double d152 = compoundInterestActivity2.InitialInvestment;
//                            double d162 = (double) compoundInterestActivity2.compoundValue;
//                            Double.isNaN(d162);
//                            Double.isNaN(d162);
//                            double d17 = d22;
//                            double d102 = (d15 / d162) + 1.0d;
//                            double d18 = d132;
//                            double d133 = (double) compoundInterestActivity2.compoundValue;
//                            double d19 = d112;
//                            double d192 = compoundInterestActivity2.Terms;
//                            Double.isNaN(d133);
//                            Double.isNaN(d133);
//                            double d20 = d122;
//                            double pow5 = Math.pow(d102, d133 * d192) * d152;
//                            double d21 = d102;
//                            double d202 = d15 + 1.0d;
//                            double d24 = d133;
//                            double d25 = d192;
//                            double pow6 = compoundInterestActivity2.RegularInvestment * ((Math.pow(d202, compoundInterestActivity2.Terms + 1.0d) - d202) / d15);
//                            double d212 = compoundInterestActivity2.RegularInvestment;
//                            double d26 = d202;
//                            double d222 = (double) compoundInterestActivity2.investmentValue;
//                            Double.isNaN(d222);
//                            double d27 = pow5 + pow6;
//                            Double.isNaN(d222);
//                            double d28 = d222;
//                            compoundInterestActivity2.interestValue = (d27 - ((d212 * d222) * compoundInterestActivity2.Terms)) - compoundInterestActivity2.InitialInvestment;
//                        } else {
//                            double d103 = d22;
//                            double d29 = d132;
//                            double d30 = d112;
//                            double d31 = d122;
//                            double d104 = compoundInterestActivity2.InitialInvestment;
//                            double d33 = compoundInterestActivity2.RegularInvestment;
//                            compoundInterestActivity2.interestValue = ((d104 + d33) * pow4) - (d104 + d33);
//                        }
//                    }
//                    double d232 = compoundInterestActivity2.RegularInvestment;
//                    double d242 = (double) compoundInterestActivity2.investmentValue;
//                    Double.isNaN(d242);
//                    Double.isNaN(d242);
//                    double d34 = d232 * d242 * compoundInterestActivity2.Terms;
//                    compoundInterestActivity2.RegularInvestmentValue = d34;
//                    r14 = compoundInterestActivity2;
//                    try {
//                        setPieChart(compoundInterestActivity2.InitialInvestment, d34, compoundInterestActivity2.interestValue);
//                        return true;
//                    } catch (NumberFormatException e3) {
//                        e = e3;
//                        compoundInterestActivity = r14;
//                        Toast.makeText(compoundInterestActivity, "Please enter valid decimal value", 0).show();
//                        e.printStackTrace();
//                        return false;
//                    }
//                }
//            }
//            this.etInitialInvestment.setError("Please fill out this field");
//            return false;
//        } catch (NumberFormatException e4) {
//            e = e4;
//            compoundInterestActivity = this;
//            Toast.makeText(compoundInterestActivity, "Please enter valid decimal value", 0).show();
//            e.printStackTrace();
//            return false;
//        }
//    }

    private void setPieChart(double d, double d2, double d3) {
        ArrayList<GraphModel> arrayList = this.graphModelArrayList;
        if (arrayList != null && arrayList.size() > 0) {
            this.graphModelArrayList.clear();
        }
        GraphModel graphModel2 = new GraphModel(getResources().getString(R.string.initial_investment) + "\n(" + Utils.decimalFormat.format(d) + ")", d, getResources().getColor(R.color.graphcolor1));
        this.graphModel = graphModel2;
        this.graphModelArrayList.add(graphModel2);
        if (d2 != com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
            GraphModel graphModel3 = new GraphModel(getResources().getString(R.string.regular_investment) + "\n(" + Utils.decimalFormat.format(d2) + ")", d2, getResources().getColor(R.color.graphcolor2));
            this.graphModel = graphModel3;
            this.graphModelArrayList.add(graphModel3);
        }
        GraphModel graphModel4 = new GraphModel(getResources().getString(R.string.interest) + "\n(" + Utils.decimalFormat.format(d3) + ")", d3, getResources().getColor(R.color.graphcolor3));
        this.graphModel = graphModel4;
        this.graphModelArrayList.add(graphModel4);
        new GraphUtils(this.pieChart, this.graphModelArrayList, getApplicationContext()).setupPieData();
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.compound_interest_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.compound_interest_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.compound_interest_calculator));
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
                intent.setData(Uri.fromParts("package", CompoundInterestActivity.this.getPackageName(), (String) null));
                CompoundInterestActivity.this.startActivityForResult(intent, 112);
                CompoundInterestActivity.this.mMyDialog.dismiss();
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
                CompoundInterestActivity.this.calendar.set(5, i3);
                CompoundInterestActivity.this.calendar.set(2, i2);
                CompoundInterestActivity.this.calendar.set(1, i);
                CompoundInterestActivity compoundInterestActivity = CompoundInterestActivity.this;
                compoundInterestActivity.etDate.setText(AppConstant.getFormattedDate(compoundInterestActivity.calendar.getTimeInMillis(), AppConstant.Date_FoRMAT_DDMMYY));
            }
        }, this.mYear, this.mMonth, this.mDay).show();
    }
}
