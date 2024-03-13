package com.demo.financialcalculator.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.demo.financialcalculator.R;
import com.demo.financialcalculator.util.ShareUtil;

public class SaveShareActivity extends AppCompatActivity {
    ImageView img;
    Toolbar toolBar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_save_share);
        init();
        setUpToolbar();
//        AdAdmob adAdmob = new AdAdmob(this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd(this);
    }

    private void init() {
        this.toolBar = (Toolbar) findViewById(R.id.toolBar);
        this.img = (ImageView) findViewById(R.id.img);
        if (ShareUtil.Path_File.exists()) {
            this.img.setImageBitmap(BitmapFactory.decodeFile(ShareUtil.Path_File.getAbsolutePath()));
        }
        findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveShareActivity saveShareActivity = SaveShareActivity.this;
                Uri uriForFile = FileProvider.getUriForFile(saveShareActivity, SaveShareActivity.this.getPackageName() + ".provider", ShareUtil.Path_File);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.SUBJECT", "");
                intent.putExtra("android.intent.extra.TEXT", "");
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                try {
                    SaveShareActivity.this.startActivity(Intent.createChooser(intent, "Share Screenshot"));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(SaveShareActivity.this, "No App Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpToolbar() {
        this.toolBar.setTitle((CharSequence) getString(R.string.loan_affordability_calculator));
        setSupportActionBar(this.toolBar);
        this.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveShareActivity.this.onBackPressed();
            }
        });
    }
}
