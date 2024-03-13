package com.demo.financialcalculator.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.demo.financialcalculator.R;
import com.demo.financialcalculator.model.MonthModel;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;

public class DialogUtils {
    BarChart barChart;
    BottomSheetDialog bottomSheetDialog;
    Context context;
    int graphType = 0;
    boolean isMonthWise;
    ImageView ivClose;
    ArrayList<MonthModel> list;
    LinearLayout llTaxINSPMI;
    TextView txtLabel;

    public DialogUtils(Context context2, ArrayList<MonthModel> arrayList, boolean z, int i) {
        this.context = context2;
        this.list = arrayList;
        this.isMonthWise = z;
        this.graphType = i;
    }

    public void setupDialog() {
        this.bottomSheetDialog = new BottomSheetDialog(this.context, R.style.CustomBottomSheetDialogTheme2);
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.dialog_chart, (ViewGroup) null);
        this.bottomSheetDialog.setContentView(inflate);
        if (this.bottomSheetDialog.isShowing()) {
            this.bottomSheetDialog.dismiss();
        }
        this.barChart = (BarChart) inflate.findViewById(R.id.barchart);
        this.txtLabel = (TextView) inflate.findViewById(R.id.txtLabel);
        this.ivClose = (ImageView) inflate.findViewById(R.id.ivClose);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.llTaxINSPMI);
        this.llTaxINSPMI = linearLayout;
        if (this.graphType == GraphUtils.MORTGAGE_GRAPH) {
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        new GraphUtils(this.barChart, this.list, this.context, this.isMonthWise, this.graphType).setupBarChartData();
        if (this.isMonthWise) {
            this.txtLabel.setText(this.context.getResources().getString(R.string.month));
        } else {
            this.txtLabel.setText(this.context.getResources().getString(R.string.year));
        }
        this.ivClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogUtils.this.bottomSheetDialog.dismiss();
            }
        });
        this.bottomSheetDialog.show();
    }
}
