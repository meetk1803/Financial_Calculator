package com.demo.financialcalculator.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.adapter.SimpleInterestAdapter;
import com.demo.financialcalculator.model.CommonModel;
import com.demo.financialcalculator.model.MonthModel;
import com.demo.financialcalculator.util.DialogUtils;
import com.demo.financialcalculator.util.GraphUtils;
import com.demo.financialcalculator.util.Utils;

import java.util.ArrayList;

public class SimpleInterestStatisticsActivity extends AppCompatActivity {
    CommonModel commonModel;
    DialogUtils dialogUtils;
    ArrayList<MonthModel> monthModels;
    RecyclerView rvList;
    ArrayList<MonthModel> sameModel;
    SimpleInterestAdapter simpleInterestAdapter;
    Toolbar toolBar;
    ArrayList<MonthModel> yearModels;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_simple_interest_statistics);
//        AdAdmob adAdmob = new AdAdmob(this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd(this);
        init();
        setUpToolbar();
        setValue();
    }

    private void init() {
        this.commonModel = new CommonModel();
        this.monthModels = new ArrayList<>();
        this.yearModels = new ArrayList<>();
        this.sameModel = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvList);
        this.rvList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.simple_interest_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SimpleInterestStatisticsActivity.this.onBackPressed();
            }
        });
    }

    private void setValue() {
        new CalculateAsync().execute(new Void[0]);
    }

    public class CalculateAsync extends AsyncTask<Void, Void, Void> {
        public CalculateAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            if (SimpleInterestStatisticsActivity.this.getIntent() != null && SimpleInterestStatisticsActivity.this.getIntent().getExtras() != null && SimpleInterestStatisticsActivity.this.getIntent().getExtras().get("SimpleInterest") != null) {
                SimpleInterestStatisticsActivity simpleInterestStatisticsActivity = SimpleInterestStatisticsActivity.this;
                simpleInterestStatisticsActivity.commonModel = (CommonModel) simpleInterestStatisticsActivity.getIntent().getSerializableExtra("SimpleInterest");
            }
        }

        public Void doInBackground(Void... voidArr) {
            if (SimpleInterestStatisticsActivity.this.commonModel == null) {
                return null;
            }
            SimpleInterestStatisticsActivity simpleInterestStatisticsActivity = SimpleInterestStatisticsActivity.this;
            simpleInterestStatisticsActivity.monthModels = Utils.getYearlySimpleInterest(simpleInterestStatisticsActivity.commonModel.getPrincipalAmount(), SimpleInterestStatisticsActivity.this.commonModel.getTerms(), SimpleInterestStatisticsActivity.this.commonModel.getInterestAmount(), SimpleInterestStatisticsActivity.this.commonModel.getYear());
            return null;
        }

        public void onPostExecute(Void r3) {
            super.onPostExecute(r3);
            SimpleInterestStatisticsActivity simpleInterestStatisticsActivity = SimpleInterestStatisticsActivity.this;
            simpleInterestStatisticsActivity.simpleInterestAdapter = new SimpleInterestAdapter(simpleInterestStatisticsActivity, simpleInterestStatisticsActivity.monthModels);
            SimpleInterestStatisticsActivity simpleInterestStatisticsActivity2 = SimpleInterestStatisticsActivity.this;
            simpleInterestStatisticsActivity2.rvList.setAdapter(simpleInterestStatisticsActivity2.simpleInterestAdapter);
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
        DialogUtils dialogUtils2 = new DialogUtils(this, this.monthModels, false, GraphUtils.SIMPLE_INTEREST_GRAPH);
        this.dialogUtils = dialogUtils2;
        dialogUtils2.setupDialog();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
