package com.demo.financialcalculator.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.adapter.RefinanceAdapter;
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.model.MonthModel;
import com.demo.financialcalculator.util.GraphUtils;
import com.demo.financialcalculator.util.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class RefinanceStatisticsActivity extends AppCompatActivity {
    int POS = 0;
    CommonModel commonModel;
    boolean isLoan1 = true;
    boolean isLoanComparision = false;
    ArrayList<MonthModel> monthModels;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    RadioButton rbLoan1;
    RadioButton rbLoan2;
    RefinanceAdapter refinanceAdapter;
    RecyclerView rvList;
    ArrayList<MonthModel> sameModel;
    Toolbar toolBar;
    ArrayList<MonthModel> yearModels;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_refinance_statistics);
        init();
        setUpToolbar();
//        AdAdmob adAdmob = new AdAdmob(this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd(this);
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.monthModels = new ArrayList<>();
        this.yearModels = new ArrayList<>();
        this.sameModel = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.rvList = (RecyclerView) findViewById(R.id.rvList);
        this.radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        this.rbLoan1 = (RadioButton) findViewById(R.id.rbLoan1);
        this.rbLoan2 = (RadioButton) findViewById(R.id.rbLoan2);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (getIntent() == null || getIntent().getExtras() == null || getIntent().getExtras().get("Refinance") == null) {
            this.rbLoan1.setText(getString(R.string.loan_1));
            this.rbLoan2.setText(getString(R.string.loan_2));
            this.isLoanComparision = true;
            setValue();
        } else {
            this.rbLoan1.setText(getString(R.string.current_loan));
            this.rbLoan2.setText(getString(R.string.refinanced_loan));
            this.isLoanComparision = false;
            setValue();
        }
        this.rbLoan1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z && RefinanceStatisticsActivity.this.POS != 0) {
                    RefinanceStatisticsActivity refinanceStatisticsActivity = RefinanceStatisticsActivity.this;
                    refinanceStatisticsActivity.POS = 0;
                    refinanceStatisticsActivity.isLoan1 = true;
                    refinanceStatisticsActivity.setData(refinanceStatisticsActivity.monthModels);
                }
            }
        });
        this.rbLoan2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z && RefinanceStatisticsActivity.this.POS != 1) {
                    RefinanceStatisticsActivity refinanceStatisticsActivity = RefinanceStatisticsActivity.this;
                    refinanceStatisticsActivity.POS = 1;
                    refinanceStatisticsActivity.isLoan1 = false;
                    refinanceStatisticsActivity.setData(refinanceStatisticsActivity.yearModels);
                }
            }
        });
        setValue();
        this.rvList.setLayoutManager(new LinearLayoutManager(this));
        RefinanceAdapter refinanceAdapter2 = new RefinanceAdapter(this, this.monthModels);
        this.refinanceAdapter = refinanceAdapter2;
        this.rvList.setAdapter(refinanceAdapter2);
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.statistics));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RefinanceStatisticsActivity.this.onBackPressed();
            }
        });
    }

    private void setValue() {
        new CalculateAsync().execute(new Void[0]);
    }

    public void setData(ArrayList<MonthModel> arrayList) {
        this.refinanceAdapter.setList(arrayList);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statasic_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.statistics) {
            return super.onOptionsItemSelected(menuItem);
        }
        setupDialog(this.refinanceAdapter.getMonthModels(), false, this.isLoan1, GraphUtils.COMON_GRAPH);
        return true;
    }

    public void setupDialog(ArrayList<MonthModel> arrayList, boolean z, boolean z2, int i) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme2);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_chart, (ViewGroup) null);
        bottomSheetDialog.setContentView(inflate);
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        BarChart barChart = (BarChart) inflate.findViewById(R.id.barchart);
        TextView textView = (TextView) inflate.findViewById(R.id.txtLabel);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.ivClose);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.llTaxINSPMI);
        if (i == GraphUtils.MORTGAGE_GRAPH) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        new GraphUtils(barChart, arrayList, this, z, i).setupBarChartData();
        if (this.isLoanComparision) {
            if (z2) {
                textView.setText(getResources().getString(R.string.loan_1));
            } else {
                textView.setText(getResources().getString(R.string.loan_2));
            }
        } else if (z2) {
            textView.setText(getResources().getString(R.string.current_loan));
        } else {
            textView.setText(getResources().getString(R.string.refinanced_loan));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    public class CalculateAsync extends AsyncTask<Void, ArrayList<MonthModel>, ArrayList<MonthModel>> {
        public CalculateAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            RefinanceStatisticsActivity.this.progressBar.setVisibility(View.VISIBLE);
        }

        public ArrayList<MonthModel> doInBackground(Void... voidArr) {
            if (RefinanceStatisticsActivity.this.getIntent() != null && RefinanceStatisticsActivity.this.getIntent().getExtras() != null && RefinanceStatisticsActivity.this.getIntent().getExtras().get("Refinance") != null) {
                RefinanceStatisticsActivity refinanceStatisticsActivity = RefinanceStatisticsActivity.this;
                refinanceStatisticsActivity.commonModel = (CommonModel) refinanceStatisticsActivity.getIntent().getSerializableExtra("Refinance");
                if (RefinanceStatisticsActivity.this.commonModel != null) {
                    RefinanceStatisticsActivity refinanceStatisticsActivity2 = RefinanceStatisticsActivity.this;
                    refinanceStatisticsActivity2.monthModels = Utils.getYearlyRefinanceAmount(refinanceStatisticsActivity2.commonModel.getPrincipalAmount(), RefinanceStatisticsActivity.this.commonModel.getTerms(), RefinanceStatisticsActivity.this.commonModel.getInterestRate(), RefinanceStatisticsActivity.this.commonModel.getMonthlyPayment(), RefinanceStatisticsActivity.this.commonModel.getYear());
                }
            } else if (!(RefinanceStatisticsActivity.this.getIntent() == null || RefinanceStatisticsActivity.this.getIntent().getExtras() == null || RefinanceStatisticsActivity.this.getIntent().getExtras().get("LoanComparison") == null)) {
                RefinanceStatisticsActivity refinanceStatisticsActivity3 = RefinanceStatisticsActivity.this;
                refinanceStatisticsActivity3.commonModel = (CommonModel) refinanceStatisticsActivity3.getIntent().getSerializableExtra("LoanComparison");
                if (RefinanceStatisticsActivity.this.commonModel != null) {
                    RefinanceStatisticsActivity refinanceStatisticsActivity4 = RefinanceStatisticsActivity.this;
                    refinanceStatisticsActivity4.monthModels = Utils.getYearlyLoanCompare(refinanceStatisticsActivity4.commonModel.getPrincipalAmount(), RefinanceStatisticsActivity.this.commonModel.getTerms(), RefinanceStatisticsActivity.this.commonModel.getInterestRate(), RefinanceStatisticsActivity.this.commonModel.getMonthlyPayment());
                    RefinanceStatisticsActivity refinanceStatisticsActivity5 = RefinanceStatisticsActivity.this;
                    refinanceStatisticsActivity5.yearModels = Utils.getYearlyLoanCompare(refinanceStatisticsActivity5.commonModel.getPrincipalAmount2(), RefinanceStatisticsActivity.this.commonModel.getTerms2(), RefinanceStatisticsActivity.this.commonModel.getInterestRate2(), RefinanceStatisticsActivity.this.commonModel.getMonthlyPayment2());
                }
            }
            return RefinanceStatisticsActivity.this.monthModels;
        }

        public void onPostExecute(ArrayList<MonthModel> arrayList) {
            super.onPostExecute(arrayList);
            RefinanceStatisticsActivity.this.progressBar.setVisibility(View.GONE);
            RefinanceStatisticsActivity.this.setData(arrayList);
        }
    }
}
