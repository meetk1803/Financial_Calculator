package com.demo.financialcalculator.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.adapter.CompoundAdapter;
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.model.MonthModel;
import com.demo.financialcalculator.util.DialogUtils;
import com.demo.financialcalculator.util.GraphUtils;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CompoundStatisticsActivity extends AppCompatActivity {
    int POS = 0;
    CommonModel commonModel;
    CompoundAdapter compoundAdapter;
    DialogUtils dialogUtils;
    Date end_date_Year;
    boolean isMonthly = true;
    ArrayList<MonthModel> monthModels;
    ProgressBar progressBar;
    RadioButton rbMonth;
    RadioButton rbYear;
    RecyclerView rvList;
    ArrayList<MonthModel> sameModel;
    Toolbar toolBar;
    TextView txtInterest;
    TextView txtMonthLabel;
    TextView txtPaid;
    TextView txtPrincipal;
    ArrayList<MonthModel> yearModels;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_compound_statistics);
//        AdAdmob adAdmob = new AdAdmob(this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd(this);
        init();
        setUpToolbar();
    }

    private void setValue() {
        new CalculateAsync().execute(new Void[0]);
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.monthModels = new ArrayList<>();
        this.yearModels = new ArrayList<>();
        this.sameModel = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.rvList = (RecyclerView) findViewById(R.id.rvList);
        this.txtPrincipal = (TextView) findViewById(R.id.txtPrincipal);
        this.txtInterest = (TextView) findViewById(R.id.txtInterest);
        this.txtPaid = (TextView) findViewById(R.id.txtPaid);
        this.txtMonthLabel = (TextView) findViewById(R.id.txtMonthLabel);
        this.rbMonth = (RadioButton) findViewById(R.id.rbMonth);
        this.rbYear = (RadioButton) findViewById(R.id.rbYear);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.rbYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    CompoundStatisticsActivity compoundStatisticsActivity = CompoundStatisticsActivity.this;
                    if (compoundStatisticsActivity.POS != 1) {
                        CompoundStatisticsActivity compoundStatisticsActivity2 = CompoundStatisticsActivity.this;
                        compoundStatisticsActivity2.POS = 1;
                        compoundStatisticsActivity2.txtMonthLabel.setText(compoundStatisticsActivity.getString(R.string.year));
                        CompoundStatisticsActivity compoundStatisticsActivity22 = CompoundStatisticsActivity.this;
                        compoundStatisticsActivity22.isMonthly = false;
                        compoundStatisticsActivity22.setData(compoundStatisticsActivity22.yearModels);
                    }
                }
            }
        });
        this.rbMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    CompoundStatisticsActivity compoundStatisticsActivity = CompoundStatisticsActivity.this;
                    if (compoundStatisticsActivity.POS != 0) {
                        CompoundStatisticsActivity compoundStatisticsActivity2 = CompoundStatisticsActivity.this;
                        compoundStatisticsActivity2.POS = 0;
                        compoundStatisticsActivity2.txtMonthLabel.setText(compoundStatisticsActivity.getString(R.string.month));
                        CompoundStatisticsActivity compoundStatisticsActivity22 = CompoundStatisticsActivity.this;
                        compoundStatisticsActivity22.isMonthly = true;
                        compoundStatisticsActivity22.setData(compoundStatisticsActivity22.monthModels);
                    }
                }
            }
        });
        setValue();
        this.rvList.setLayoutManager(new LinearLayoutManager(this));
        CompoundAdapter compoundAdapter2 = new CompoundAdapter(this, this.monthModels, this.isMonthly);
        this.compoundAdapter = compoundAdapter2;
        this.rvList.setAdapter(compoundAdapter2);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.statistics));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CompoundStatisticsActivity.this.onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        double d = Utils.DOUBLE_EPSILON;
        com.demo.financialcalculator.util.Utils.Principal = d;
        com.demo.financialcalculator.util.Utils.Interest = d;
        com.demo.financialcalculator.util.Utils.Paid = d;
    }

    public void setData(ArrayList<MonthModel> arrayList) {
        this.compoundAdapter.setList(arrayList, this.isMonthly);
    }

    public class CalculateAsync extends AsyncTask<Void, ArrayList<MonthModel>, ArrayList<MonthModel>> {
        public CalculateAsync() {
        }

        public ArrayList<MonthModel> doInBackground(Void... voidArr) {
            if (!(CompoundStatisticsActivity.this.getIntent() == null || CompoundStatisticsActivity.this.getIntent().getExtras() == null || CompoundStatisticsActivity.this.getIntent().getExtras().get("CompoundInterest") == null)) {
                CompoundStatisticsActivity compoundStatisticsActivity = CompoundStatisticsActivity.this;
                compoundStatisticsActivity.commonModel = (CommonModel) compoundStatisticsActivity.getIntent().getSerializableExtra("CompoundInterest");
                if (CompoundStatisticsActivity.this.commonModel != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(CompoundStatisticsActivity.this.commonModel.getDate());
                    CompoundStatisticsActivity compoundStatisticsActivity2 = CompoundStatisticsActivity.this;
                    compoundStatisticsActivity2.monthModels = com.demo.financialcalculator.util.Utils.getMonthlyCompound(compoundStatisticsActivity2.commonModel.getPrincipalAmount(), CompoundStatisticsActivity.this.commonModel.getTerms(), CompoundStatisticsActivity.this.commonModel.getInterestRate(), CompoundStatisticsActivity.this.commonModel.getMonthlyPayment(), calendar.getTime());
                    CompoundStatisticsActivity.this.end_date_Year = new Date();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTimeInMillis(CompoundStatisticsActivity.this.commonModel.getDate());
                    calendar2.add(2, Integer.parseInt(com.demo.financialcalculator.util.Utils.decimalFormat.format(CompoundStatisticsActivity.this.commonModel.getTerms())));
                    CompoundStatisticsActivity.this.end_date_Year = calendar2.getTime();
                    CompoundStatisticsActivity compoundStatisticsActivity3 = CompoundStatisticsActivity.this;
                    compoundStatisticsActivity3.yearModels = com.demo.financialcalculator.util.Utils.getYearlyCompound(compoundStatisticsActivity3.monthModels, calendar2.getTime(), CompoundStatisticsActivity.this.end_date_Year);
                }
            }
            return CompoundStatisticsActivity.this.monthModels;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            CompoundStatisticsActivity.this.progressBar.setVisibility(View.VISIBLE);
        }

        public void onPostExecute(ArrayList<MonthModel> arrayList) {
            super.onPostExecute(arrayList);
            CompoundStatisticsActivity.this.progressBar.setVisibility(View.GONE);
            CompoundStatisticsActivity.this.txtPrincipal.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(Math.round(com.demo.financialcalculator.util.Utils.Principal)));
            CompoundStatisticsActivity.this.txtInterest.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(Math.round(com.demo.financialcalculator.util.Utils.Interest)));
            CompoundStatisticsActivity.this.txtPaid.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(Math.round(com.demo.financialcalculator.util.Utils.Paid)));
            CompoundStatisticsActivity.this.setData(arrayList);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statasic_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.statistics) {
            return super.onOptionsItemSelected(menuItem);
        }
        DialogUtils dialogUtils2 = new DialogUtils(this, this.compoundAdapter.getMonthModels(), this.isMonthly, GraphUtils.SIMPLE_INTEREST_GRAPH);
        this.dialogUtils = dialogUtils2;
        dialogUtils2.setupDialog();
        return true;
    }
}
