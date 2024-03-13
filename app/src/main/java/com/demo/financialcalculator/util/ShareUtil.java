package com.demo.financialcalculator.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.ScrollView;
import com.demo.financialcalculator.activity.SaveShareActivity;
import java.io.File;
import java.io.FileOutputStream;

public class ShareUtil {
    public static File Path_File = null;

    public static void print(Context context, ScrollView scrollView, String str) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Saving...");
        progressDialog.show();
        Bitmap bitmapFromView = getBitmapFromView(scrollView, scrollView.getChildAt(0).getHeight(), scrollView.getChildAt(0).getWidth());
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Financial Calculator");
            if (!file.exists()) {
                file.mkdir();
            }
            String str2 = str + "_" + Long.valueOf(System.currentTimeMillis() / 1000).toString() + ".jpg";
            File file2 = new File(file, str2);
            if (file2.exists()) {
                file2.delete();
                file2 = new File(file, str2);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmapFromView.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            progressDialog.dismiss();
            Path_File = file2;
            context.startActivity(new Intent(context, SaveShareActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }

    private static Bitmap getBitmapFromView(View view, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        view.draw(canvas);
        return createBitmap;
    }
}
