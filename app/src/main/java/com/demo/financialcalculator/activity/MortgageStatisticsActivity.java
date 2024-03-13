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
import com.demo.financialcalculator.adapter.MortgageAdapter;
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.model.MonthModel;
import com.demo.financialcalculator.util.DialogUtils;
import com.demo.financialcalculator.util.GraphUtils;
import com.demo.financialcalculator.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MortgageStatisticsActivity extends AppCompatActivity {
    int POS = 0;
    CommonModel commonModel;
    DialogUtils dialogUtils;
    Date end_date_Year;
    boolean isMonthly = true;
    ArrayList<MonthModel> monthModels;
    MortgageAdapter mortgageAdapter;
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
    TextView txtTaxInsPMI;
    ArrayList<MonthModel> yearModels;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_mortgage_statistics);
//        AdAdmob adAdmob = new AdAdmob(this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd(this);
        this.commonModel = new CommonModel();
        this.monthModels = new ArrayList<>();
        this.yearModels = new ArrayList<>();
        this.sameModel = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        setUpToolbar();
        this.rvList = (RecyclerView) findViewById(R.id.rvList);
        this.txtPrincipal = (TextView) findViewById(R.id.txtPrincipal);
        this.txtInterest = (TextView) findViewById(R.id.txtInterest);
        this.txtTaxInsPMI = (TextView) findViewById(R.id.txtTaxInsPMI);
        this.txtPaid = (TextView) findViewById(R.id.txtPaid);
        this.txtMonthLabel = (TextView) findViewById(R.id.txtMonthLabel);
        this.rbMonth = (RadioButton) findViewById(R.id.rbMonth);
        this.rbYear = (RadioButton) findViewById(R.id.rbYear);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.rbYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    MortgageStatisticsActivity mortgageStatisticsActivity = MortgageStatisticsActivity.this;
                    if (mortgageStatisticsActivity.POS != 1) {
                        MortgageStatisticsActivity mortgageStatisticsActivity2 = MortgageStatisticsActivity.this;
                        mortgageStatisticsActivity2.POS = 1;
                        mortgageStatisticsActivity2.txtMonthLabel.setText(mortgageStatisticsActivity.getString(R.string.year));
                        MortgageStatisticsActivity mortgageStatisticsActivity22 = MortgageStatisticsActivity.this;
                        mortgageStatisticsActivity22.isMonthly = false;
                        mortgageStatisticsActivity22.setData(mortgageStatisticsActivity22.yearModels);
                    }
                }
            }
        });
        this.rbMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    MortgageStatisticsActivity mortgageStatisticsActivity = MortgageStatisticsActivity.this;
                    if (mortgageStatisticsActivity.POS != 0) {
                        MortgageStatisticsActivity mortgageStatisticsActivity2 = MortgageStatisticsActivity.this;
                        mortgageStatisticsActivity2.POS = 0;
                        mortgageStatisticsActivity2.txtMonthLabel.setText(mortgageStatisticsActivity.getString(R.string.month));
                        MortgageStatisticsActivity mortgageStatisticsActivity22 = MortgageStatisticsActivity.this;
                        mortgageStatisticsActivity22.isMonthly = true;
                        mortgageStatisticsActivity22.setData(mortgageStatisticsActivity22.monthModels);
                    }
                }
            }
        });
        this.txtPrincipal.setText(Utils.decimalFormat.format(Utils.Principal));
        this.txtInterest.setText(Utils.decimalFormat.format(Utils.Interest));
        this.txtTaxInsPMI.setText(Utils.decimalFormat.format(Utils.mTaxInsPMI));
        this.txtPaid.setText(Utils.decimalFormat.format(Utils.Paid));
        setValue();
        this.rvList.setLayoutManager(new LinearLayoutManager(this));
        MortgageAdapter mortgageAdapter2 = new MortgageAdapter(this, this.monthModels, this.isMonthly);
        this.mortgageAdapter = mortgageAdapter2;
        this.rvList.setAdapter(mortgageAdapter2);
    }

    private void setValue() {
        new CalculateAsync().execute(new Void[0]);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.statistics));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MortgageStatisticsActivity.this.onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        Utils.Principal = d;
        Utils.Interest = d;
        Utils.mTaxInsPMI = d;
        Utils.Paid = d;
    }

    public void setData(ArrayList<MonthModel> arrayList) {
        this.mortgageAdapter.setList(arrayList, this.isMonthly);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statasic_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.statistics) {
            return super.onOptionsItemSelected(menuItem);
        }
        DialogUtils dialogUtils2 = new DialogUtils(this, this.mortgageAdapter.getMonthModels(), this.isMonthly, GraphUtils.MORTGAGE_GRAPH);
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
            MortgageStatisticsActivity.this.progressBar.setVisibility(View.VISIBLE);
        }

        public ArrayList<MonthModel> doInBackground(Void... voidArr) {
            if (!(MortgageStatisticsActivity.this.getIntent() == null || MortgageStatisticsActivity.this.getIntent().getExtras() == null)) {
                MortgageStatisticsActivity mortgageStatisticsActivity = MortgageStatisticsActivity.this;
                mortgageStatisticsActivity.commonModel = (CommonModel) mortgageStatisticsActivity.getIntent().getSerializableExtra("Mortgage");
                if (MortgageStatisticsActivity.this.commonModel != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(MortgageStatisticsActivity.this.commonModel.getDate());
                    calendar.add(2, 1);
                    MortgageStatisticsActivity mortgageStatisticsActivity2 = MortgageStatisticsActivity.this;
                    mortgageStatisticsActivity2.monthModels = Utils.getMonthlyMortgage(mortgageStatisticsActivity2.commonModel.getPrincipalAmount(), MortgageStatisticsActivity.this.commonModel.getTerms(), MortgageStatisticsActivity.this.commonModel.getInterestRate(), MortgageStatisticsActivity.this.commonModel.getMonthlyPayment(), MortgageStatisticsActivity.this.commonModel.getTaxIns(), MortgageStatisticsActivity.this.commonModel.getPMI(), calendar.getTime(), MortgageStatisticsActivity.this.commonModel.getDownPayment());
                    MortgageStatisticsActivity.this.end_date_Year = new Date();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(calendar.getTime());
                    calendar2.add(2, Integer.parseInt(Utils.decimalFormat.format(MortgageStatisticsActivity.this.commonModel.getTerms())));
                    MortgageStatisticsActivity.this.end_date_Year = calendar2.getTime();
                    MortgageStatisticsActivity mortgageStatisticsActivity3 = MortgageStatisticsActivity.this;
                    mortgageStatisticsActivity3.yearModels = Utils.getYearlyMortgage(mortgageStatisticsActivity3.monthModels, calendar.getTime(), MortgageStatisticsActivity.this.end_date_Year);
                }
            }
            return MortgageStatisticsActivity.this.monthModels;
        }

        public void onPostExecute(ArrayList<MonthModel> arrayList) {
            super.onPostExecute(arrayList);
            MortgageStatisticsActivity.this.progressBar.setVisibility(View.GONE);
            MortgageStatisticsActivity.this.txtPrincipal.setText(Utils.decimalFormat.format(Utils.Principal));
            MortgageStatisticsActivity.this.txtInterest.setText(Utils.decimalFormat.format(Utils.Interest));
            MortgageStatisticsActivity.this.txtPaid.setText(Utils.decimalFormat.format(Utils.Paid));
            MortgageStatisticsActivity.this.setData(arrayList);
        }
    }
}
