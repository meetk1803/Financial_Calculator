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
import com.demo.financialcalculator.model.GraphModel;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.GraphUtils;
import com.demo.financialcalculator.util.ShareUtil;
import com.demo.financialcalculator.util.Utils;
import com.github.mikephil.charting.charts.PieChart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CarLeaseActivity extends AppCompatActivity {
    double MonthlyLeasePayment;
    double Terms;
    Button btnCalculate;
    Button btnShare;
    double downPayment;
    EditText etDownPayment;
    EditText etInterestRate;
    EditText etOwedTrade;
    EditText etResidualValue;
    EditText etSaleTaxes;
    EditText etTerms;
    EditText etTradeAmount;
    EditText etVehiclePrice;
    GraphModel graphModel;
    ArrayList<GraphModel> graphModelArrayList;
    LinearLayout graphlayot;
    double interestRate;
    CardView llResult;
    AlertDialog mMyDialog;
    double owedTrade;
    PieChart pieChart;
    double residualValue;
    ScrollView rootLayout;
    double saleTaxes;
    Spinner spTerm;
    int term;
    Toolbar toolBar;
    double tradeAmount;
    TextView txtMonthlyPayment;
    TextView txtOverPayments;
    double vehiclePrice;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_car_lease);
//        new AdAdmob(this).BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        init();
        setUpToolbar();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.car_lease_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CarLeaseActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.graphModelArrayList = new ArrayList<>();
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.etVehiclePrice = (EditText) findViewById(R.id.etVehiclePrice);
        this.etResidualValue = (EditText) findViewById(R.id.etResidualValue);
        this.etInterestRate = (EditText) findViewById(R.id.etInterestRate);
        this.etDownPayment = (EditText) findViewById(R.id.etDownPayment);
        this.etTradeAmount = (EditText) findViewById(R.id.etTradeAmount);
        this.etOwedTrade = (EditText) findViewById(R.id.etOwedTrade);
        this.etTerms = (EditText) findViewById(R.id.etTerms);
        this.etSaleTaxes = (EditText) findViewById(R.id.etSaleTaxes);
        this.txtMonthlyPayment = (TextView) findViewById(R.id.txtMonthlyPayment);
        this.txtOverPayments = (TextView) findViewById(R.id.txtOverPayments);
        this.graphlayot = (LinearLayout) findViewById(R.id.graphlayot);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.spTerm = (Spinner) findViewById(R.id.spTerm);
        this.pieChart = (PieChart) findViewById(R.id.piechart);
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
                        CarLeaseActivity.this.term = 1;
                        return;
                    case 1:
                        CarLeaseActivity.this.term = 12;
                        return;
                    default:
                        return;
                }
            }
        });
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CarLeaseActivity.this.setValue()) {
                    AppConstant.hideKeyboard(CarLeaseActivity.this);
                    AppConstant.visibleResult(CarLeaseActivity.this.llResult);
                    AppConstant.visibleGraph(CarLeaseActivity.this.graphlayot);
                }
                CarLeaseActivity carLeaseActivity = CarLeaseActivity.this;
                carLeaseActivity.txtMonthlyPayment.setText(String.format("$%s", new Object[]{Utils.decimalFormat.format(carLeaseActivity.MonthlyLeasePayment)}));
                CarLeaseActivity carLeaseActivity2 = CarLeaseActivity.this;
                TextView textView = carLeaseActivity2.txtOverPayments;
                DecimalFormat decimalFormat = Utils.decimalFormat;
                textView.setText(String.format("$%s", new Object[]{decimalFormat.format(Double.parseDouble(decimalFormat.format(carLeaseActivity2.MonthlyLeasePayment)) * CarLeaseActivity.this.Terms)}));
            }
        });
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CarLeaseActivity.this.checkPermission();
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
            if (!this.etTradeAmount.getText().toString().trim().isEmpty()) {
                this.tradeAmount = Double.valueOf(this.etTradeAmount.getText().toString()).doubleValue();
            } else {
                this.tradeAmount = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            if (!this.etOwedTrade.getText().toString().trim().isEmpty()) {
                this.owedTrade = Double.valueOf(this.etOwedTrade.getText().toString()).doubleValue();
            } else {
                this.owedTrade = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            if (!this.etSaleTaxes.getText().toString().trim().isEmpty()) {
                this.saleTaxes = Double.valueOf(this.etSaleTaxes.getText().toString()).doubleValue() / 100.0d;
            } else {
                this.saleTaxes = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            if (!this.etVehiclePrice.getText().toString().trim().isEmpty()) {
                double doubleValue = Double.valueOf(this.etVehiclePrice.getText().toString()).doubleValue();
                double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                if (doubleValue != d) {
                    this.vehiclePrice = ((Double.valueOf(this.etVehiclePrice.getText().toString()).doubleValue() - this.downPayment) - this.tradeAmount) + this.owedTrade;
                    if (!this.etResidualValue.getText().toString().trim().isEmpty()) {
                        this.residualValue = Double.valueOf(this.etResidualValue.getText().toString()).doubleValue();
                    } else {
                        this.residualValue = d;
                    }
                    if (this.etInterestRate.getText().toString().trim().isEmpty() || Double.valueOf(this.etInterestRate.getText().toString()).doubleValue() == d) {
                        this.etInterestRate.setError("Please fill out this field");
                        return false;
                    }
                    this.interestRate = Double.valueOf(this.etInterestRate.getText().toString()).doubleValue();
                    if (this.etTerms.getText().toString().trim().isEmpty() || Double.valueOf(this.etTerms.getText().toString()).doubleValue() == d) {
                        this.etTerms.setError("Please fill out this field");
                        return false;
                    }
                    double doubleValue2 = Double.valueOf(this.etTerms.getText().toString()).doubleValue();
                    double d2 = (double) this.term;
                    Double.isNaN(d2);
                    Double.isNaN(d2);
                    double d3 = doubleValue2 * d2;
                    this.Terms = d3;
                    double d4 = this.vehiclePrice;
                    double d5 = this.residualValue;
                    double d22 = (d4 - d5) / d3;
                    double d32 = this.interestRate / 2400.0d;
                    double d42 = ((d4 + d5) * d32) + d22;
                    double d52 = this.saleTaxes * d42;
                    this.MonthlyLeasePayment = d42 + d52;
                    setPieChart(d22, d52, (d4 + d5) * d32);
                    return true;
                }
            }
            this.etVehiclePrice.setError("Please fill out this field");
            return false;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid decimal value", 0).show();
            e.printStackTrace();
            return false;
        }
    }

    private void setPieChart(double d, double d2, double d3) {
        ArrayList<GraphModel> arrayList = this.graphModelArrayList;
        if (arrayList != null && arrayList.size() > 0) {
            this.graphModelArrayList.clear();
        }
        GraphModel graphModel2 = new GraphModel(getResources().getString(R.string.deprecation_charge) + "\n(" + Utils.decimalFormat.format(d) + ")", d, getResources().getColor(R.color.graphcolor1));
        this.graphModel = graphModel2;
        this.graphModelArrayList.add(graphModel2);
        GraphModel graphModel3 = new GraphModel(getResources().getString(R.string.finance_charge) + "\n(" + Utils.decimalFormat.format(d3) + ")", d3, getResources().getColor(R.color.graphcolor2));
        this.graphModel = graphModel3;
        this.graphModelArrayList.add(graphModel3);
        GraphModel graphModel4 = new GraphModel(getResources().getString(R.string.sales_tax) + "\n(" + Utils.decimalFormat.format(d2) + ")", d2, getResources().getColor(R.color.graphcolor3));
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
                ShareUtil.print(this, this.rootLayout, getString(R.string.car_lease_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.car_lease_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.car_lease_calculator));
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
                intent.setData(Uri.fromParts("package", CarLeaseActivity.this.getPackageName(), (String) null));
                CarLeaseActivity.this.startActivityForResult(intent, 112);
                CarLeaseActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
