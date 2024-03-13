package com.demo.financialcalculator.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.util.AppConstant;
import com.demo.financialcalculator.util.AppPref;

public class MainActivity extends AppCompatActivity {
    LinearLayout invest_Lin;
    ImageView investmentdrop;
    ImageView leaseDrop;
    LinearLayout lease_Lin;
    LinearLayout linAmortization;
    LinearLayout linAutoLoan;
    LinearLayout linBondYield;
    LinearLayout linCarLease;
    LinearLayout linCompound;
    LinearLayout linInterestOnly;
    LinearLayout linInvestment;
    LinearLayout linLease;
    LinearLayout linLoanAffordability;
    LinearLayout linLoanComparison;
    LinearLayout linLoans;
    LinearLayout linMortgage;
    LinearLayout linNetWorth;
    LinearLayout linNpv;
    LinearLayout linPresentValue;
    LinearLayout linRefinance;
    LinearLayout linRentalYield;
    LinearLayout linRoi;
    LinearLayout linSimple;
    ImageView loanDrop;
    LinearLayout loan_Lin;
    String playStoreUrl = "";
    ScrollView scrollView;
    String title = "How was your experience with us?";
    Toolbar toolBar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
//        AdAdmob adAdmob = new AdAdmob(this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd(this);
        init();
        setUpToolbar();
        this.playStoreUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((int) R.string.app_name);
        setSupportActionBar(this.toolBar);
    }

    private void init() {
        this.scrollView = (ScrollView) findViewById(R.id.scrollview);
        this.linLoans = (LinearLayout) findViewById(R.id.linLoans);
        this.linLease = (LinearLayout) findViewById(R.id.linLease);
        this.linInvestment = (LinearLayout) findViewById(R.id.linInvestment);
        this.loan_Lin = (LinearLayout) findViewById(R.id.loan_Lin);
        this.lease_Lin = (LinearLayout) findViewById(R.id.lease_Lin);
        this.invest_Lin = (LinearLayout) findViewById(R.id.invest_Lin);
        this.loanDrop = (ImageView) findViewById(R.id.loanDrop);
        this.leaseDrop = (ImageView) findViewById(R.id.leaseDrop);
        this.investmentdrop = (ImageView) findViewById(R.id.investmentDrop);
        this.linRentalYield = (LinearLayout) findViewById(R.id.linRentalYield);
        this.linBondYield = (LinearLayout) findViewById(R.id.linBondYield);
        this.linRoi = (LinearLayout) findViewById(R.id.linRoi);
        this.linNpv = (LinearLayout) findViewById(R.id.linNpv);
        this.linPresentValue = (LinearLayout) findViewById(R.id.linPresentValue);
        this.linNetWorth = (LinearLayout) findViewById(R.id.linNetWorth);
        this.linMortgage = (LinearLayout) findViewById(R.id.linMortgage);
        this.linAutoLoan = (LinearLayout) findViewById(R.id.linAutoLoan);
        this.linAmortization = (LinearLayout) findViewById(R.id.linAmortization);
        this.linLoanComparison = (LinearLayout) findViewById(R.id.linLoanComparison);
        this.linRefinance = (LinearLayout) findViewById(R.id.linRefinance);
        this.linInterestOnly = (LinearLayout) findViewById(R.id.linInterestOnly);
        this.linLoanAffordability = (LinearLayout) findViewById(R.id.linLoanAffordability);
        this.linCarLease = (LinearLayout) findViewById(R.id.linCarLease);
        this.linCompound = (LinearLayout) findViewById(R.id.linCompound);
        this.linSimple = (LinearLayout) findViewById(R.id.linSimple);
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        checkVisibility();
        onClick();
    }

    private void checkVisibility() {
        this.linLoans.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.this.loan_Lin.getVisibility() == View.GONE) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.loanDrop.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_up_white));
                    MainActivity.this.loan_Lin.setVisibility(View.VISIBLE);
                    return;
                }
                MainActivity mainActivity2 = MainActivity.this;
                mainActivity2.loanDrop.setImageDrawable(mainActivity2.getResources().getDrawable(R.drawable.ic_down_white));
                MainActivity.this.loan_Lin.setVisibility(View.GONE);
            }
        });
        this.linLease.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.this.lease_Lin.getVisibility() == View.GONE) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.leaseDrop.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_up_white));
                    MainActivity.this.lease_Lin.setVisibility(View.VISIBLE);
                    return;
                }
                MainActivity mainActivity2 = MainActivity.this;
                mainActivity2.leaseDrop.setImageDrawable(mainActivity2.getResources().getDrawable(R.drawable.ic_down_white));
                MainActivity.this.lease_Lin.setVisibility(View.GONE);
            }
        });
        this.linInvestment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.this.invest_Lin.getVisibility() == View.GONE) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.investmentdrop.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_up_white));
                    MainActivity.this.invest_Lin.setVisibility(View.VISIBLE);
                    if (MainActivity.this.lease_Lin.getVisibility() == View.VISIBLE && MainActivity.this.loan_Lin.getVisibility() == View.VISIBLE) {
                        MainActivity.this.scrollView.postDelayed(new Runnable() {
                            public void run() {
                                MainActivity.this.scrollView.fullScroll(130);
                            }
                        }, 100);
                        return;
                    }
                    return;
                }
                MainActivity mainActivity2 = MainActivity.this;
                mainActivity2.investmentdrop.setImageDrawable(mainActivity2.getResources().getDrawable(R.drawable.ic_down_white));
                MainActivity.this.invest_Lin.setVisibility(8);
            }
        });
    }

    private void onClick() {
        this.linRentalYield.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, RentalYieldCalculatorActivity.class));
            }
        });
        this.linBondYield.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, BondYieldCalculatorActivity.class));
            }
        });
        this.linRoi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ROIActivity.class));
            }
        });
        this.linNpv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, NPVCalculatorActivity.class));
            }
        });
        this.linPresentValue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, PresentValueCalculatorActivity.class));
            }
        });
        this.linNetWorth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, NetWorthCalculatorActivity.class));
            }
        });
        this.linMortgage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, MortgageActivity.class));
            }
        });
        this.linAutoLoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, AutoLoanActivity.class));
            }
        });
        this.linAmortization.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, AmortizationActivity.class));
            }
        });
        this.linLoanComparison.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, LoanComparisonActivity.class));
            }
        });
        this.linRefinance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, RefinanceActivity.class));
            }
        });
        this.linInterestOnly.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, InterestOnlyActivity.class));
            }
        });
        this.linLoanAffordability.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, LoanAffordabilityActivity.class));
            }
        });
        this.linCarLease.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, CarLeaseActivity.class));
            }
        });
        this.linCompound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, CompoundInterestActivity.class));
            }
        });
        this.linSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SimpleInterestActivity.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.privarcypolicy) {
            uriparse(AppConstant.PRIVACY_POLICY_URL);
            return true;
        } else if (itemId == R.id.rateus) {
//            showDialog();
            return true;
        } else if (itemId == R.id.share) {
            shareapp();
            return true;
        } else if (itemId != R.id.termsofService) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            uriparse(AppConstant.TERMS_OF_SERVICE_URL);
            return true;
        }
    }

    @SuppressLint({"WrongConstant"})
    private void uriparse(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addFlags(1208483840);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AppConstant.PRIVACY_POLICY_URL)));
        }
    }

    public void shareapp() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "Financial Calculator & Investment Calculator");
            intent.putExtra("android.intent.extra.TEXT", "Financial Calculator & Investment CalculatorAll in one financial Calculator works as great Financial Planner & useful loans\n- Mortgage Calculator, Loan Comparison Calculator\n- Mortgage Statistics (Monthly, Yearly) Calculation\n- Graphical view of mortgage chart\n- Simple Interest Calculator, Return of Investment Calculator\n\nhttps://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            Log.d("", e.toString());
        }
    }

//    private void showDialog() {
//        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getPackageName(), 0);
//        RatingDialog build = new RatingDialog.Builder(this).session(1).title(this.title).threshold(4.0f).icon(getResources().getDrawable(R.drawable.icon200)).titleTextColor(R.color.colorPrimary).negativeButtonText
//                ("Never").positiveButtonTextColor(R.color.colorPrimary).negativeButtonTextColor(R.color.colorPrimary).formTitle("Submit Feedback")
//                .formHint("Tell us where we can improve").formSubmitText("Submit").formCancelText("Cancel")
//                .ratingBarColor(R.color.ratingBarColor).ratingBarBackgroundColor(R.color.ratingBarBackgroundColor).playstoreUrl(this.playStoreUrl).onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
//            public void onFormSubmitted(String str) {
//                MainActivity.this.EmailUs(str);
//                SharedPreferences.Editor edit = sharedPreferences.edit();
//                edit.putBoolean("shownever", true);
//                edit.commit();
//            }
//        }).build();
//        if (sharedPreferences.getBoolean("shownever", false)) {
//            Toast.makeText(this, "Already Submitted", Toast.LENGTH_SHORT).show();
//        } else {
//            build.show();
//        }
//    }

    public void EmailUs(String str) {
        try {
            String str2 = Build.MODEL;
            String str3 = Build.MANUFACTURER;
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("mailto:rising.studioapps89@gmail.com"));
            intent.putExtra("android.intent.extra.SUBJECT", "Your Suggestion - Financial Calculator & Investment Calculator(" + getPackageName() + ")");
            intent.putExtra("android.intent.extra.TEXT", str + "\n\nDevice Manufacturer : " + str3 + "\nDevice Model : " + str2 + "\nAndroid Version : " + Build.VERSION.RELEASE + "\nApp Version : 1.0");
            startActivityForResult(intent, 9);
        } catch (Exception e) {
            Log.d("", e.toString());
        }
    }

    public void onBackPressed() {
        if (!AppPref.IsRateUS(this)) {
            AppPref.setIsRateUS(this, true);
//            showDialog();
            return;
        }
        super.onBackPressed();
    }
}
