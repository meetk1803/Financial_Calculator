package com.demo.financialcalculator.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.demo.financialcalculator.adapter.MonthAdapter;
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.model.MonthModel;
import com.demo.financialcalculator.util.DialogUtils;
import com.demo.financialcalculator.util.GraphUtils;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatisticsActivity extends AppCompatActivity {
    int POS = 0;
    CommonModel commonModel;
    ArrayList<CommonModel> commonModelList;
    DialogUtils dialogUtils;
    Date end_date_Year;
    CommonModel interestOnly;
    boolean isMonthly = true;
    MonthAdapter monthAdapter;
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
        setContentView((int) R.layout.activity_statistics);
//        AdAdmob adAdmob = new AdAdmob(this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd(this);
        this.commonModel = new CommonModel();
        this.interestOnly = new CommonModel();
        this.commonModelList = new ArrayList<>();
        this.monthModels = new ArrayList<>();
        this.yearModels = new ArrayList<>();
        this.sameModel = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        setUpToolbar();
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
                    StatisticsActivity statisticsActivity = StatisticsActivity.this;
                    if (statisticsActivity.POS != 1) {
                        StatisticsActivity statisticsActivity2 = StatisticsActivity.this;
                        statisticsActivity2.POS = 1;
                        statisticsActivity2.txtMonthLabel.setText(statisticsActivity.getString(R.string.year));
                        StatisticsActivity statisticsActivity22 = StatisticsActivity.this;
                        statisticsActivity22.isMonthly = false;
                        statisticsActivity22.setData(statisticsActivity22.yearModels);
                    }
                }
            }
        });
        this.rbMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    StatisticsActivity statisticsActivity = StatisticsActivity.this;
                    if (statisticsActivity.POS != 0) {
                        StatisticsActivity statisticsActivity2 = StatisticsActivity.this;
                        statisticsActivity2.POS = 0;
                        statisticsActivity2.txtMonthLabel.setText(statisticsActivity.getString(R.string.month));
                        StatisticsActivity statisticsActivity22 = StatisticsActivity.this;
                        statisticsActivity22.isMonthly = true;
                        statisticsActivity22.setData(statisticsActivity22.monthModels);
                    }
                }
            }
        });
        setValue();
        this.rvList.setLayoutManager(new LinearLayoutManager(this));
        MonthAdapter monthAdapter2 = new MonthAdapter(this, this.monthModels, this.isMonthly);
        this.monthAdapter = monthAdapter2;
        this.rvList.setAdapter(monthAdapter2);
    }

    private void setValue() {
        new CalculateAsync().execute(new Void[0]);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.statistics));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StatisticsActivity.this.onBackPressed();
            }
        });
    }

    public void setData(ArrayList<MonthModel> arrayList) {
        this.monthAdapter.setList(arrayList, this.isMonthly);
    }

    public void onBackPressed() {
        super.onBackPressed();
        double d = Utils.DOUBLE_EPSILON;
        com.demo.financialcalculator.util.Utils.Principal = d;
        com.demo.financialcalculator.util.Utils.Interest = d;
        com.demo.financialcalculator.util.Utils.Paid = d;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statasic_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.statistics) {
            return super.onOptionsItemSelected(menuItem);
        }
        DialogUtils dialogUtils2 = new DialogUtils(this, this.monthAdapter.getMonthModels(), this.isMonthly, GraphUtils.COMON_GRAPH);
        this.dialogUtils = dialogUtils2;
        dialogUtils2.setupDialog();
        return true;
    }

    public class CalculateAsync extends AsyncTask<Void, ArrayList<MonthModel>, ArrayList<MonthModel>> {
        public CalculateAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            StatisticsActivity.this.progressBar.setVisibility(View.VISIBLE);
        }

        public ArrayList<MonthModel> doInBackground(Void... voidArr) {
            if (StatisticsActivity.this.getIntent() != null && StatisticsActivity.this.getIntent().getExtras() != null && StatisticsActivity.this.getIntent().getExtras().get("AutoLoan") != null) {
                StatisticsActivity statisticsActivity = StatisticsActivity.this;
                statisticsActivity.commonModel = (CommonModel) statisticsActivity.getIntent().getSerializableExtra("AutoLoan");
                if (StatisticsActivity.this.commonModel != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(StatisticsActivity.this.commonModel.getDate());
                    calendar.add(2, 1);
                    StatisticsActivity statisticsActivity2 = StatisticsActivity.this;
                    statisticsActivity2.monthModels = com.demo.financialcalculator.util.Utils.getMonthlyAmount(statisticsActivity2.commonModel.getPrincipalAmount(), StatisticsActivity.this.commonModel.getTerms(), StatisticsActivity.this.commonModel.getInterestRate(), StatisticsActivity.this.commonModel.getMonthlyPayment(), calendar.getTime());
                    StatisticsActivity.this.end_date_Year = new Date();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTimeInMillis(calendar.getTimeInMillis());
                    calendar2.add(2, Integer.parseInt(com.demo.financialcalculator.util.Utils.decimalFormat.format(StatisticsActivity.this.commonModel.getTerms())));
                    Log.d("TIMEINMILLE", "" + calendar2.getTimeInMillis());
                    StatisticsActivity.this.end_date_Year = calendar2.getTime();
                    StatisticsActivity statisticsActivity3 = StatisticsActivity.this;
                    statisticsActivity3.yearModels = com.demo.financialcalculator.util.Utils.getYearlyAmount(statisticsActivity3.monthModels, calendar.getTime(), StatisticsActivity.this.end_date_Year);
                }
            } else if (!(StatisticsActivity.this.getIntent() == null || StatisticsActivity.this.getIntent().getExtras() == null || StatisticsActivity.this.getIntent().getExtras().get("InterestOnly") == null)) {
                StatisticsActivity statisticsActivity4 = StatisticsActivity.this;
                statisticsActivity4.interestOnly = (CommonModel) statisticsActivity4.getIntent().getSerializableExtra("InterestOnly");
                if (StatisticsActivity.this.interestOnly != null) {
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTimeInMillis(StatisticsActivity.this.interestOnly.getDate());
                    calendar3.add(2, 1);
                    StatisticsActivity statisticsActivity5 = StatisticsActivity.this;
                    statisticsActivity5.monthModels = com.demo.financialcalculator.util.Utils.getMonthlyInterest(statisticsActivity5.interestOnly.getPrincipalAmount(), StatisticsActivity.this.interestOnly.getTerms(), StatisticsActivity.this.interestOnly.getInterestRate(), StatisticsActivity.this.interestOnly.getMonthlyPayment(), StatisticsActivity.this.interestOnly.getInterestPeriod(), calendar3.getTime());
                    StatisticsActivity.this.end_date_Year = new Date();
                    Calendar calendar4 = Calendar.getInstance();
                    calendar4.setTimeInMillis(calendar3.getTimeInMillis());
                    calendar4.add(2, Integer.parseInt(com.demo.financialcalculator.util.Utils.decimalFormat.format(StatisticsActivity.this.interestOnly.getTerms())));
                    StatisticsActivity.this.end_date_Year = calendar4.getTime();
                    StatisticsActivity statisticsActivity6 = StatisticsActivity.this;
                    statisticsActivity6.yearModels = com.demo.financialcalculator.util.Utils.getYearlyAmount(statisticsActivity6.monthModels, calendar3.getTime(), StatisticsActivity.this.end_date_Year);
                }
            }
            return StatisticsActivity.this.monthModels;
        }

        public void onPostExecute(ArrayList<MonthModel> arrayList) {
            super.onPostExecute(arrayList);
            StatisticsActivity.this.progressBar.setVisibility(View.GONE);
            StatisticsActivity.this.txtPrincipal.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(com.demo.financialcalculator.util.Utils.Principal));
            StatisticsActivity.this.txtInterest.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(com.demo.financialcalculator.util.Utils.Interest));
            StatisticsActivity.this.txtPaid.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(com.demo.financialcalculator.util.Utils.Paid));
            StatisticsActivity.this.setData(arrayList);
        }
    }
}
