//package com.demo.financialcalculator;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.util.Log;
//import android.view.View;
//import android.widget.RelativeLayout;
//import androidx.annotation.NonNull;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//import com.google.android.gms.ads.interstitial.InterstitialAd;
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
//
//public class AdAdmob {
//    String BannerAdID = "/6499/example/banner";
//    String FullscreenAdID = "/6499/example/interstitial";
//    ProgressDialog ProgressDialog;
//
//    public AdAdmob(Activity activity) {
//        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//    }
//
//    public void BannerAd(final RelativeLayout Ad_Layout, Activity activity) {
//        final AdView mAdView = new AdView(activity);
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId(this.BannerAdID);
//        mAdView.loadAd(new AdRequest.Builder().build());
//        Ad_Layout.addView(mAdView);
//        mAdView.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                Ad_Layout.setVisibility(View.VISIBLE);
//                super.onAdLoaded();
//                Log.e("ddddd", "dddd");
//            }
//
//            public void onAdOpened() {
//                super.onAdOpened();
//                Ad_Layout.setVisibility(View.INVISIBLE);
//                Log.e("ddddd1", "dddd");
//            }
//
//            public void onAdFailedToLoad(LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                mAdView.destroy();
//                Ad_Layout.setVisibility(View.INVISIBLE);
//                Log.e("ddddd2", "dddd" + loadAdError.getMessage());
//            }
//        });
//    }
//
//    public void FullscreenAd(final Activity activity) {
//        Ad_Popup(activity);
//        InterstitialAd.load(activity, this.FullscreenAdID, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                interstitialAd.show(activity);
//                AdAdmob.this.ProgressDialog.dismiss();
//            }
//
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                AdAdmob.this.ProgressDialog.dismiss();
//            }
//        });
//    }
//
//    private void Ad_Popup(Context mContext) {
//        ProgressDialog show = ProgressDialog.show(mContext, "", "Ad Loading . . .", true);
//        this.ProgressDialog = show;
//        show.setCancelable(true);
//        this.ProgressDialog.show();
//    }
//}
