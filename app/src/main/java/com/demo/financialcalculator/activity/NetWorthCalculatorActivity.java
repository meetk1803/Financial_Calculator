package com.demo.financialcalculator.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class NetWorthCalculatorActivity extends AppCompatActivity {
    double art;
    double autoLoan;
    double boats;
    double bonds;
    Button btnCalculate;
    Button btnShare;
    double businessInterest;
    double cars;
    double cash;
    double checkingAccount;
    double collectibles;
    double creditCard;
    ImageView drop;
    ImageView drop1;
    ImageView drop2;
    ImageView drop3;
    ImageView drop4;
    ImageView drop5;
    EditText etArt;
    EditText etAutoLoan;
    EditText etBoats;
    EditText etBonds;
    EditText etBusiness;
    EditText etCars;
    EditText etCash;
    EditText etCheckingAccount;
    EditText etCollectibles;
    EditText etCreditCard;
    EditText etDeposit;
    EditText etHomeFurnishing;
    EditText etInvestmentLoan;
    EditText etInvestmentOther;
    EditText etIras;
    EditText etJewelry;
    EditText etKeogh;
    EditText etKs;
    EditText etLifeInsurance;
    EditText etLiquidOther;
    EditText etLongTermOther;
    EditText etMortgages;
    EditText etMutualFunds;
    EditText etOutstandingTaxes;
    EditText etPension;
    EditText etPersonalLoan;
    EditText etPersonalOthers;
    EditText etPrimaryResidence;
    EditText etRealEstate;
    EditText etRetirementOther;
    EditText etSavingsAccount;
    EditText etShortTermLoan;
    EditText etShortTermOther;
    EditText etStocks;
    EditText etStudentLoan;
    EditText etVacationProperties;
    GraphModel graphModel;
    ArrayList<GraphModel> graphModelArrayList;
    LinearLayout graphlayot;
    double homeFurnishing;
    LinearLayout investmentAssests;
    double investmentLoan;
    double iras;
    double jewelry;
    double keogh;
    double ks;
    TextView lTotal;
    double lifeInsurance;
    LinearLayout linearInvestment;
    LinearLayout linearLiquid;
    LinearLayout linearLongTerm;
    LinearLayout linearPersonal;
    LinearLayout linearRetirement;
    LinearLayout linearShortTerm;
    LinearLayout liquidAssests;
    CardView llResult;
    LinearLayout longTerm;
    AlertDialog mMyDialog;
    double mortgages;
    double mutualFund;
    double otherInvestmenmt;
    double otherLiquid;
    double otherLongTerm;
    double otherPersonal;
    double otherRetirement;
    double otherShortTerm;
    double outstandingTaxes;
    double pension;
    LinearLayout personalAssests;
    double personalLoan;
    PieChart piechartAssets;
    PieChart piechartLiabilities;
    double primaryResidence;
    double realEstate;
    LinearLayout retirementAssests;
    ScrollView rootLayout;
    double savingsAccount;
    LinearLayout shortTerm;
    double shortTermLoan;
    double stocks;
    double studentLoan;
    double termDeposit;
    Toolbar toolBar;
    double totalCash;
    TextView totalInvest;
    double totalInvestment;
    double totalLongTerm;
    TextView totalLongTermLiabilities;
    double totalPersonal;
    TextView totalPersonalAssest;
    TextView totalRetire;
    double totalRetirement;
    double totalShortTerm;
    TextView totalShortTermLiabilities;
    TextView txtNetWorth;
    TextView txtTotalAssest;
    TextView txtTotalLiabilities;
    double vacationProperties;

    public NetWorthCalculatorActivity() {
        double d = Utils.DOUBLE_EPSILON;
        this.cash = d;
        this.savingsAccount = d;
        this.checkingAccount = d;
        this.lifeInsurance = d;
        this.otherLiquid = d;
        this.realEstate = d;
        this.termDeposit = d;
        this.stocks = d;
        this.bonds = d;
        this.mutualFund = d;
        this.businessInterest = d;
        this.otherInvestmenmt = d;
        this.iras = d;
        this.ks = d;
        this.keogh = d;
        this.pension = d;
        this.otherRetirement = d;
        this.primaryResidence = d;
        this.vacationProperties = d;
        this.boats = d;
        this.cars = d;
        this.homeFurnishing = d;
        this.art = d;
        this.jewelry = d;
        this.collectibles = d;
        this.otherPersonal = d;
        this.creditCard = d;
        this.shortTermLoan = d;
        this.outstandingTaxes = d;
        this.otherShortTerm = d;
        this.mortgages = d;
        this.investmentLoan = d;
        this.personalLoan = d;
        this.studentLoan = d;
        this.autoLoan = d;
        this.otherLongTerm = d;
        this.totalCash = d;
        this.totalInvestment = d;
        this.totalRetirement = d;
        this.totalPersonal = d;
        this.totalShortTerm = d;
        this.totalLongTerm = d;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_net_worth_calculator);
//        new AdAdmob(this).FullscreenAd(this);
        init();
        setUpToolbar();
    }

    private void init() {
        this.graphModelArrayList = new ArrayList<>();
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.btnCalculate = (Button) findViewById(R.id.btnCalculate);
        this.btnShare = (Button) findViewById(R.id.btnShare);
        this.rootLayout = (ScrollView) findViewById(R.id.rootLayout);
        this.liquidAssests = (LinearLayout) findViewById(R.id.liquidAssests);
        this.investmentAssests = (LinearLayout) findViewById(R.id.investmentAssests);
        this.llResult = (CardView) findViewById(R.id.llResult);
        this.retirementAssests = (LinearLayout) findViewById(R.id.retirementAssests);
        this.personalAssests = (LinearLayout) findViewById(R.id.personalAssests);
        this.shortTerm = (LinearLayout) findViewById(R.id.shortTerm);
        this.graphlayot = (LinearLayout) findViewById(R.id.graphlayot);
        this.longTerm = (LinearLayout) findViewById(R.id.longTerm);
        this.linearLiquid = (LinearLayout) findViewById(R.id.linearLiquid);
        this.linearInvestment = (LinearLayout) findViewById(R.id.linearInvestment);
        this.linearRetirement = (LinearLayout) findViewById(R.id.linearRetirement);
        this.linearPersonal = (LinearLayout) findViewById(R.id.linearPersonal);
        this.linearShortTerm = (LinearLayout) findViewById(R.id.linearShortTerm);
        this.linearLongTerm = (LinearLayout) findViewById(R.id.linearLongTerm);
        this.etCash = (EditText) findViewById(R.id.etCash);
        this.etSavingsAccount = (EditText) findViewById(R.id.etSavingsAccount);
        this.etCheckingAccount = (EditText) findViewById(R.id.etCheckingAccount);
        this.etLifeInsurance = (EditText) findViewById(R.id.etLifeInsurance);
        this.etLiquidOther = (EditText) findViewById(R.id.etLiquidOther);
        this.etRealEstate = (EditText) findViewById(R.id.etRealEstate);
        this.etDeposit = (EditText) findViewById(R.id.etDeposit);
        this.etStocks = (EditText) findViewById(R.id.etStocks);
        this.etBonds = (EditText) findViewById(R.id.etBonds);
        this.etMutualFunds = (EditText) findViewById(R.id.etMutualFunds);
        this.etBusiness = (EditText) findViewById(R.id.etBusiness);
        this.etInvestmentOther = (EditText) findViewById(R.id.etInvestmentOther);
        this.etIras = (EditText) findViewById(R.id.etIras);
        this.etKs = (EditText) findViewById(R.id.etKs);
        this.etKeogh = (EditText) findViewById(R.id.etKeogh);
        this.etPension = (EditText) findViewById(R.id.etPension);
        this.etRetirementOther = (EditText) findViewById(R.id.etRetirementOther);
        this.etPrimaryResidence = (EditText) findViewById(R.id.etPrimaryResidence);
        this.etVacationProperties = (EditText) findViewById(R.id.etVacationProperties);
        this.etCars = (EditText) findViewById(R.id.etCars);
        this.etBoats = (EditText) findViewById(R.id.etBoats);
        this.etHomeFurnishing = (EditText) findViewById(R.id.etHomeFurnishing);
        this.etArt = (EditText) findViewById(R.id.etArt);
        this.etJewelry = (EditText) findViewById(R.id.etJewelry);
        this.etCollectibles = (EditText) findViewById(R.id.etCollectibles);
        this.etPersonalOthers = (EditText) findViewById(R.id.etPersonalOthers);
        this.etCreditCard = (EditText) findViewById(R.id.etCreditCard);
        this.etShortTermLoan = (EditText) findViewById(R.id.etShortTermLoan);
        this.etOutstandingTaxes = (EditText) findViewById(R.id.etOutstandingTaxes);
        this.etShortTermOther = (EditText) findViewById(R.id.etShortTermOther);
        this.etMortgages = (EditText) findViewById(R.id.etMortgages);
        this.etInvestmentLoan = (EditText) findViewById(R.id.etInvestmentLoan);
        this.etPersonalLoan = (EditText) findViewById(R.id.etPersonalLoan);
        this.etStudentLoan = (EditText) findViewById(R.id.etStudentLoan);
        this.etAutoLoan = (EditText) findViewById(R.id.etAutoLoan);
        this.etLongTermOther = (EditText) findViewById(R.id.etLongTermOther);
        this.lTotal = (TextView) findViewById(R.id.lTotal);
        this.totalInvest = (TextView) findViewById(R.id.totalInvest);
        this.totalRetire = (TextView) findViewById(R.id.totalRetire);
        this.totalPersonalAssest = (TextView) findViewById(R.id.totalPersonalAssest);
        this.totalShortTermLiabilities = (TextView) findViewById(R.id.totalShortTermLiabilities);
        this.totalLongTermLiabilities = (TextView) findViewById(R.id.totalLongTermLiabilities);
        this.txtTotalAssest = (TextView) findViewById(R.id.txtTotalAssest);
        this.txtTotalLiabilities = (TextView) findViewById(R.id.txtTotalLiabilities);
        this.txtNetWorth = (TextView) findViewById(R.id.txtNetWorth);
        this.drop = (ImageView) findViewById(R.id.drops);
        this.drop1 = (ImageView) findViewById(R.id.drop1);
        this.drop2 = (ImageView) findViewById(R.id.drop2);
        this.drop3 = (ImageView) findViewById(R.id.drop3);
        this.drop4 = (ImageView) findViewById(R.id.drop4);
        this.drop5 = (ImageView) findViewById(R.id.drop5);
        this.piechartAssets = (PieChart) findViewById(R.id.piechartAssets);
        this.piechartLiabilities = (PieChart) findViewById(R.id.piechartLiabilities);
        this.btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    NetWorthCalculatorActivity.this.calculate();
                    AppConstant.hideKeyboard(NetWorthCalculatorActivity.this);
                    AppConstant.visibleResult(NetWorthCalculatorActivity.this.llResult);
                    AppConstant.visibleGraph(NetWorthCalculatorActivity.this.graphlayot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        checkVisibility();
        setTextChangeListener();
        this.btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NetWorthCalculatorActivity.this.checkPermission();
            }
        });
    }

    @SuppressLint({"SetTextI18n"})
    public void calculate() {
        Double valueOf = Double.valueOf(this.totalCash + this.totalInvestment + this.totalRetirement + this.totalPersonal);
        Double valueOf2 = Double.valueOf(this.totalShortTerm + this.totalLongTerm);
        Double valueOf3 = Double.valueOf(valueOf.doubleValue() - valueOf2.doubleValue());
        TextView textView = this.txtTotalAssest;
        textView.setText("$" + com.demo.financialcalculator.util.Utils.decimalFormat.format(valueOf));
        TextView textView2 = this.txtTotalLiabilities;
        textView2.setText("$" + com.demo.financialcalculator.util.Utils.decimalFormat.format(valueOf2));
        TextView textView3 = this.txtNetWorth;
        textView3.setText("$" + com.demo.financialcalculator.util.Utils.decimalFormat.format(valueOf3));
        setPieChart(this.totalCash, this.totalInvestment, this.totalRetirement, this.totalPersonal);
        setPieChart(this.totalShortTerm, this.totalLongTerm);
    }

    private void setTextChangeListener() {
        setTextChangeListenerLiquid();
        setTextChangeListenerInvestment();
        setTextChangeListenerRetirement();
        setTextChangeListenerPersonal();
        setTextChangeListenerShortTerm();
        setTextChangeListenerLongTerm();
    }

    private void setTextChangeListenerLongTerm() {
        this.etMortgages.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.mortgages = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.mortgages = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLongTerm();
            }
        });
        this.etInvestmentLoan.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.investmentLoan = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.investmentLoan = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLongTerm();
            }
        });
        this.etPersonalLoan.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.personalLoan = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.personalLoan = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLongTerm();
            }
        });
        this.etStudentLoan.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.studentLoan = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.studentLoan = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLongTerm();
            }
        });
        this.etAutoLoan.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.autoLoan = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.autoLoan = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLongTerm();
            }
        });
        this.etLongTermOther.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.otherLongTerm = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.otherLongTerm = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLongTerm();
            }
        });
    }

    public void calculateLongTerm() {
        double d = this.mortgages + this.investmentLoan + this.personalLoan + this.studentLoan + this.autoLoan + this.otherLongTerm;
        this.totalLongTerm = d;
        this.totalLongTermLiabilities.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(d));
    }

    private void setTextChangeListenerShortTerm() {
        this.etCreditCard.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.creditCard = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.creditCard = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateShortTerm();
            }
        });
        this.etShortTermLoan.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.shortTermLoan = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.shortTermLoan = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateShortTerm();
            }
        });
        this.etOutstandingTaxes.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.outstandingTaxes = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.outstandingTaxes = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateShortTerm();
            }
        });
        this.etShortTermOther.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.otherShortTerm = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.otherShortTerm = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateShortTerm();
            }
        });
    }

    public void calculateShortTerm() {
        double d = this.creditCard + this.shortTermLoan + this.outstandingTaxes + this.otherShortTerm;
        this.totalShortTerm = d;
        this.totalShortTermLiabilities.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(d));
    }

    private void setTextChangeListenerPersonal() {
        this.etPrimaryResidence.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.primaryResidence = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.primaryResidence = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
        this.etVacationProperties.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.vacationProperties = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.vacationProperties = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
        this.etCars.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.cars = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.cars = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
        this.etBoats.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.boats = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.boats = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
        this.etHomeFurnishing.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.homeFurnishing = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.homeFurnishing = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
        this.etArt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.art = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.art = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
        this.etJewelry.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.jewelry = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.jewelry = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
        this.etCollectibles.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.collectibles = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.collectibles = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
        this.etPersonalOthers.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.otherPersonal = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.otherPersonal = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculatePersonal();
            }
        });
    }

    public void calculatePersonal() {
        double d = this.primaryResidence + this.vacationProperties + this.cars + this.boats + this.homeFurnishing + this.art + this.jewelry + this.collectibles + this.otherPersonal;
        this.totalPersonal = d;
        this.totalPersonalAssest.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(d));
    }

    private void setTextChangeListenerRetirement() {
        this.etIras.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.iras = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.iras = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateRetirement();
            }
        });
        this.etKs.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.ks = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.ks = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateRetirement();
            }
        });
        this.etKeogh.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.keogh = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.keogh = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateRetirement();
            }
        });
        this.etPension.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.pension = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.pension = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateRetirement();
            }
        });
        this.etRetirementOther.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.otherRetirement = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.otherRetirement = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateRetirement();
            }
        });
    }

    public void calculateRetirement() {
        double d = this.iras + this.ks + this.keogh + this.pension + this.otherRetirement;
        this.totalRetirement = d;
        this.totalRetire.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(d));
    }

    private void setTextChangeListenerInvestment() {
        this.etRealEstate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.realEstate = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.realEstate = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateInvestment();
            }
        });
        this.etDeposit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.termDeposit = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.termDeposit = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateInvestment();
            }
        });
        this.etStocks.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.stocks = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.stocks = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateInvestment();
            }
        });
        this.etBonds.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.bonds = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.bonds = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateInvestment();
            }
        });
        this.etBusiness.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.businessInterest = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.businessInterest = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateInvestment();
            }
        });
        this.etInvestmentOther.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.otherInvestmenmt = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.otherInvestmenmt = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateInvestment();
            }
        });
        this.etMutualFunds.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.mutualFund = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.mutualFund = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateInvestment();
            }
        });
    }

    public void calculateInvestment() {
        double d = this.realEstate + this.termDeposit + this.stocks + this.bonds + this.mutualFund + this.businessInterest + this.otherInvestmenmt;
        this.totalInvestment = d;
        this.totalInvest.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(d));
    }

    private void setTextChangeListenerLiquid() {
        this.etCash.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.cash = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.cash = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLiQuid();
            }
        });
        this.etSavingsAccount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.savingsAccount = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.savingsAccount = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLiQuid();
            }
        });
        this.etCheckingAccount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.checkingAccount = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.checkingAccount = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLiQuid();
            }
        });
        this.etLifeInsurance.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.lifeInsurance = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.lifeInsurance = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLiQuid();
            }
        });
        this.etLiquidOther.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    NetWorthCalculatorActivity.this.otherLiquid = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NetWorthCalculatorActivity.this.otherLiquid = Utils.DOUBLE_EPSILON;
                }
                NetWorthCalculatorActivity.this.calculateLiQuid();
            }
        });
    }

    public void calculateLiQuid() {
        double d = this.cash + this.savingsAccount + this.checkingAccount + this.lifeInsurance + this.otherLiquid;
        this.totalCash = d;
        this.lTotal.setText(com.demo.financialcalculator.util.Utils.decimalFormat.format(d));
    }

    private void checkVisibility() {
        this.liquidAssests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NetWorthCalculatorActivity.this.linearLiquid.getVisibility() == View.GONE) {
                    NetWorthCalculatorActivity.this.linearLiquid.setVisibility(View.VISIBLE);
                    NetWorthCalculatorActivity netWorthCalculatorActivity = NetWorthCalculatorActivity.this;
                    netWorthCalculatorActivity.drop.setImageDrawable(netWorthCalculatorActivity.getResources().getDrawable(R.drawable.ic_up_black));
                    return;
                }
                NetWorthCalculatorActivity.this.linearLiquid.setVisibility(View.GONE);
                NetWorthCalculatorActivity netWorthCalculatorActivity2 = NetWorthCalculatorActivity.this;
                netWorthCalculatorActivity2.drop.setImageDrawable(netWorthCalculatorActivity2.getResources().getDrawable(R.drawable.ic_down_black));
            }
        });
        this.investmentAssests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NetWorthCalculatorActivity.this.linearInvestment.getVisibility() == View.GONE) {
                    NetWorthCalculatorActivity.this.linearInvestment.setVisibility(View.VISIBLE);
                    NetWorthCalculatorActivity netWorthCalculatorActivity = NetWorthCalculatorActivity.this;
                    netWorthCalculatorActivity.drop1.setImageDrawable(netWorthCalculatorActivity.getResources().getDrawable(R.drawable.ic_up_black));
                    return;
                }
                NetWorthCalculatorActivity.this.linearInvestment.setVisibility(View.GONE);
                NetWorthCalculatorActivity netWorthCalculatorActivity2 = NetWorthCalculatorActivity.this;
                netWorthCalculatorActivity2.drop1.setImageDrawable(netWorthCalculatorActivity2.getResources().getDrawable(R.drawable.ic_down_black));
            }
        });
        this.retirementAssests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NetWorthCalculatorActivity.this.linearRetirement.getVisibility() == View.GONE) {
                    NetWorthCalculatorActivity.this.linearRetirement.setVisibility(View.VISIBLE);
                    NetWorthCalculatorActivity netWorthCalculatorActivity = NetWorthCalculatorActivity.this;
                    netWorthCalculatorActivity.drop2.setImageDrawable(netWorthCalculatorActivity.getResources().getDrawable(R.drawable.ic_up_black));
                    return;
                }
                NetWorthCalculatorActivity.this.linearRetirement.setVisibility(View.GONE);
                NetWorthCalculatorActivity netWorthCalculatorActivity2 = NetWorthCalculatorActivity.this;
                netWorthCalculatorActivity2.drop2.setImageDrawable(netWorthCalculatorActivity2.getResources().getDrawable(R.drawable.ic_down_black));
            }
        });
        this.personalAssests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NetWorthCalculatorActivity.this.linearPersonal.getVisibility() == View.GONE) {
                    NetWorthCalculatorActivity.this.linearPersonal.setVisibility(View.VISIBLE);
                    NetWorthCalculatorActivity netWorthCalculatorActivity = NetWorthCalculatorActivity.this;
                    netWorthCalculatorActivity.drop3.setImageDrawable(netWorthCalculatorActivity.getResources().getDrawable(R.drawable.ic_up_black));
                    return;
                }
                NetWorthCalculatorActivity.this.linearPersonal.setVisibility(View.GONE);
                NetWorthCalculatorActivity netWorthCalculatorActivity2 = NetWorthCalculatorActivity.this;
                netWorthCalculatorActivity2.drop3.setImageDrawable(netWorthCalculatorActivity2.getResources().getDrawable(R.drawable.ic_down_black));
            }
        });
        this.shortTerm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NetWorthCalculatorActivity.this.linearShortTerm.getVisibility() == View.GONE) {
                    NetWorthCalculatorActivity.this.linearShortTerm.setVisibility(View.VISIBLE);
                    NetWorthCalculatorActivity netWorthCalculatorActivity = NetWorthCalculatorActivity.this;
                    netWorthCalculatorActivity.drop4.setImageDrawable(netWorthCalculatorActivity.getResources().getDrawable(R.drawable.ic_up_black));
                    return;
                }
                NetWorthCalculatorActivity.this.linearShortTerm.setVisibility(View.GONE);
                NetWorthCalculatorActivity netWorthCalculatorActivity2 = NetWorthCalculatorActivity.this;
                netWorthCalculatorActivity2.drop4.setImageDrawable(netWorthCalculatorActivity2.getResources().getDrawable(R.drawable.ic_down_black));
            }
        });
        this.longTerm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NetWorthCalculatorActivity.this.linearLongTerm.getVisibility() == View.GONE) {
                    NetWorthCalculatorActivity.this.linearLongTerm.setVisibility(View.VISIBLE);
                    NetWorthCalculatorActivity netWorthCalculatorActivity = NetWorthCalculatorActivity.this;
                    netWorthCalculatorActivity.drop5.setImageDrawable(netWorthCalculatorActivity.getResources().getDrawable(R.drawable.ic_up_black));
                    return;
                }
                NetWorthCalculatorActivity.this.linearLongTerm.setVisibility(View.GONE);
                NetWorthCalculatorActivity netWorthCalculatorActivity2 = NetWorthCalculatorActivity.this;
                netWorthCalculatorActivity2.drop5.setImageDrawable(netWorthCalculatorActivity2.getResources().getDrawable(R.drawable.ic_down_black));
            }
        });
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((int) R.string.net_worth_calculator);
        setSupportActionBar(this.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setPieChart(double d, double d2, double d3, double d4) {
        ArrayList<GraphModel> arrayList = this.graphModelArrayList;
        if (arrayList != null && arrayList.size() > 0) {
            this.graphModelArrayList.clear();
        }
        GraphModel graphModel2 = new GraphModel(getResources().getString(R.string.liquid) + "\n(" + com.demo.financialcalculator.util.Utils.decimalFormat.format(d) + ")", d, getResources().getColor(R.color.graphcolor1));
        this.graphModel = graphModel2;
        this.graphModelArrayList.add(graphModel2);
        GraphModel graphModel3 = new GraphModel(getResources().getString(R.string.invest) + "\n(" + com.demo.financialcalculator.util.Utils.decimalFormat.format(d2) + ")", d2, getResources().getColor(R.color.graphcolor2));
        this.graphModel = graphModel3;
        this.graphModelArrayList.add(graphModel3);
        GraphModel graphModel4 = new GraphModel(getResources().getString(R.string.retire) + "\n(" + com.demo.financialcalculator.util.Utils.decimalFormat.format(d3) + ")", d3, getResources().getColor(R.color.graphcolor3));
        this.graphModel = graphModel4;
        this.graphModelArrayList.add(graphModel4);
        GraphModel graphModel5 = new GraphModel(getResources().getString(R.string.personal) + "\n(" + com.demo.financialcalculator.util.Utils.decimalFormat.format(d4) + ")", d4, getResources().getColor(R.color.graphcolor4));
        this.graphModel = graphModel5;
        this.graphModelArrayList.add(graphModel5);
        new GraphUtils(this.piechartAssets, this.graphModelArrayList, getApplicationContext()).setupPieData();
    }

    private void setPieChart(double d, double d2) {
        ArrayList<GraphModel> arrayList = this.graphModelArrayList;
        if (arrayList != null && arrayList.size() > 0) {
            this.graphModelArrayList.clear();
        }
        GraphModel graphModel2 = new GraphModel(getResources().getString(R.string.shortterm) + "\n(" + com.demo.financialcalculator.util.Utils.decimalFormat.format(d) + ")", d, getResources().getColor(R.color.graphcolor1));
        this.graphModel = graphModel2;
        this.graphModelArrayList.add(graphModel2);
        GraphModel graphModel3 = new GraphModel(getResources().getString(R.string.longterm) + "\n(" + com.demo.financialcalculator.util.Utils.decimalFormat.format(d2) + ")", d2, getResources().getColor(R.color.graphcolor2));
        this.graphModel = graphModel3;
        this.graphModelArrayList.add(graphModel3);
        new GraphUtils(this.piechartLiabilities, this.graphModelArrayList, getApplicationContext()).setupPieData();
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!com.demo.financialcalculator.util.Utils.hasPermissions(this, strArr)) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            } else {
                ShareUtil.print(this, this.rootLayout, getString(R.string.net_worth_calculator));
            }
        } else {
            ShareUtil.print(this, this.rootLayout, getString(R.string.net_worth_calculator));
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ShareUtil.print(this, this.rootLayout, getString(R.string.net_worth_calculator));
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
                intent.setData(Uri.fromParts("package", NetWorthCalculatorActivity.this.getPackageName(), (String) null));
                NetWorthCalculatorActivity.this.startActivityForResult(intent, 112);
                NetWorthCalculatorActivity.this.mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
