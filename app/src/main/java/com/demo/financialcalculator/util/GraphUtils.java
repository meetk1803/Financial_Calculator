package com.demo.financialcalculator.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.demo.financialcalculator.R;
import com.demo.financialcalculator.model.GraphModel;
import com.demo.financialcalculator.model.MonthModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphUtils {
    public static int COMON_GRAPH = 1;
    public static int MORTGAGE_GRAPH = 3;
    public static int SIMPLE_INTEREST_GRAPH = 2;
    String MONTH_FORMATE = "MMM-yyyy";
    ArrayList<String> PieEntryLabels;
    String YEAR_FORMATE = "yyyy";
    BarChart barChart;
    Context context;
    ArrayList<IBarDataSet> dataSets;
    ArrayList<PieEntry> entries;
    ArrayList<GraphModel> graphModelArrayList;
    int graphType;
    boolean isMonthWise;
    ArrayList<MonthModel> monthModels;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;

    public GraphUtils(PieChart pieChart2, ArrayList<GraphModel> arrayList, Context context2) {
        this.pieChart = pieChart2;
        this.graphModelArrayList = arrayList;
        this.context = context2;
    }

    public GraphUtils(BarChart barChart2, ArrayList<MonthModel> arrayList, Context context2, boolean z, int i) {
        this.barChart = barChart2;
        this.monthModels = arrayList;
        this.context = context2;
        this.isMonthWise = z;
        this.graphType = i;
    }

    @SuppressLint("RestrictedApi")
    public void setupPieData() {
        Typeface font = ResourcesCompat.getFont(this.context, R.font.regular);
        this.entries = new ArrayList<>();
        this.PieEntryLabels = new ArrayList<>();
        this.pieDataSet = new PieDataSet(this.entries, "projects");
        this.pieChart.setUsePercentValues(true);
        this.pieChart.setDrawHoleEnabled(true);
        this.pieChart.setHoleColor(this.context.getResources().getColor(R.color.colorWhite));
        this.pieChart.setTransparentCircleAlpha(110);
        this.pieChart.setHoleRadius(58.0f);
        this.pieChart.setEntryLabelColor(this.context.getResources().getColor(R.color.colorDark));
        this.pieChart.setEntryLabelTypeface(font);
        Legend legend = this.pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(false);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.graphModelArrayList.size(); i++) {
            this.entries.add(new PieEntry((float) this.graphModelArrayList.get(i).getValue(), this.graphModelArrayList.get(i).getLabel()));
            arrayList.add(Integer.valueOf(this.graphModelArrayList.get(i).getColor()));
        }
        this.pieDataSet.setColors((List<Integer>) arrayList);
        this.pieDataSet.setValueLinePart1OffsetPercentage(80.0f);
        this.pieDataSet.setValueLinePart1Length(0.2f);
        this.pieDataSet.setValueLinePart2Length(0.4f);
        this.pieDataSet.setSliceSpace(3.0f);
        this.pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData pieData2 = new PieData(this.pieDataSet);
        this.pieData = pieData2;
        pieData2.setValueFormatter(new PercentFormatter());
        this.pieData.setValueTextSize(12.0f);
        this.pieData.setValueTextColor(this.context.getResources().getColor(R.color.colorWhite));
        this.pieChart.setData(this.pieData);
        this.pieChart.getDescription().setEnabled(false);
        ((PieData) this.pieChart.getData()).setDrawValues(true);
        this.pieChart.animateY(PathInterpolatorCompat.MAX_NUM_POINTS);
        this.pieChart.invalidate();
    }

    public void setupBarChartData() {
        this.barChart.getAxisRight().removeAllLimitLines();
        this.barChart.getAxisLeft().removeAllLimitLines();
        this.barChart.clear();
        this.barChart.getAxisLeft().resetAxisMaximum();
        this.barChart.getAxisLeft().resetAxisMinimum();
        this.barChart.getAxisRight().resetAxisMaximum();
        this.barChart.getAxisRight().resetAxisMinimum();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = this.graphType;
        if (i == COMON_GRAPH) {
            for (int i2 = 0; i2 < this.monthModels.size(); i2++) {
                float[] fArr = {(float) this.monthModels.get(i2).getPrincipalAmount(), (float) this.monthModels.get(i2).getInterest()};
                arrayList2.add(Integer.valueOf(this.context.getResources().getColor(R.color.graphcolor1)));
                arrayList2.add(Integer.valueOf(this.context.getResources().getColor(R.color.graphcolor2)));
                arrayList.add(new BarEntry((float) i2, fArr, (Object) arrayList2));
            }
        } else if (i == SIMPLE_INTEREST_GRAPH) {
            for (int i3 = 0; i3 < this.monthModels.size(); i3++) {
                float[] fArr2 = {(float) this.monthModels.get(i3).getTotalPrincipal(), (float) this.monthModels.get(i3).getTotalInterest()};
                arrayList2.add(Integer.valueOf(this.context.getResources().getColor(R.color.graphcolor1)));
                arrayList2.add(Integer.valueOf(this.context.getResources().getColor(R.color.graphcolor2)));
                arrayList.add(new BarEntry((float) i3, fArr2, (Object) arrayList2));
            }
        } else if (i == MORTGAGE_GRAPH) {
            for (int i4 = 0; i4 < this.monthModels.size(); i4++) {
                float[] fArr3 = {(float) this.monthModels.get(i4).getTotalPrincipal(), (float) this.monthModels.get(i4).getTotalInterest(), (float) this.monthModels.get(i4).getTotalTax()};
                arrayList2.add(Integer.valueOf(this.context.getResources().getColor(R.color.graphcolor1)));
                arrayList2.add(Integer.valueOf(this.context.getResources().getColor(R.color.graphcolor2)));
                arrayList2.add(Integer.valueOf(this.context.getResources().getColor(R.color.graphcolor3)));
                arrayList.add(new BarEntry((float) i4, fArr3, (Object) arrayList2));
            }
        }
        if (this.barChart.getData() == null || ((BarData) this.barChart.getData()).getDataSetCount() <= 0) {
            BarDataSet barDataSet = new BarDataSet(arrayList, "Statistic");
            barDataSet.setDrawIcons(false);
            barDataSet.setColors((List<Integer>) arrayList2);
            ArrayList<IBarDataSet> arrayList3 = new ArrayList<>();
            this.dataSets = arrayList3;
            arrayList3.add(barDataSet);
            BarData barData = new BarData((List<IBarDataSet>) this.dataSets);
            barData.setBarWidth(0.5f);
            barData.setValueFormatter(new MyValueFormatter());
            barData.setValueTextColor(0);
            this.barChart.setData(barData);
            this.barChart.setScaleEnabled(false);
            this.barChart.getAxisLeft().setDrawGridLines(false);
            this.barChart.getXAxis().setDrawGridLines(false);
            this.barChart.getAxisLeft().setDrawLabels(true);
            this.barChart.setDrawBorders(false);
            this.barChart.setDrawGridBackground(false);
            XAxis xAxis = this.barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularityEnabled(true);
            xAxis.setEnabled(true);
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new CustomXAxis(this.monthModels));
            xAxis.setDrawAxisLine(true);
            YAxis axisLeft = this.barChart.getAxisLeft();
            axisLeft.setEnabled(false);
            axisLeft.setDrawGridLines(false);
            axisLeft.setDrawAxisLine(false);
            YAxis axisRight = this.barChart.getAxisRight();
            axisRight.setEnabled(true);
            axisRight.setDrawGridLines(false);
            axisRight.setDrawAxisLine(false);
            axisLeft.setAxisMinimum(0.0f);
            axisRight.setAxisMinimum(0.0f);
            this.barChart.getDescription().setEnabled(false);
            this.barChart.getLegend().setEnabled(true);
            this.barChart.notifyDataSetChanged();
            this.barChart.setFitBars(true);
            this.barChart.getLegend().setEnabled(false);
            this.barChart.invalidate();
            ((BarData) this.barChart.getData()).notifyDataChanged();
            this.barChart.notifyDataSetChanged();
            this.barChart.setVisibleXRangeMaximum(10.0f);
            this.barChart.moveViewToX((float) this.monthModels.size());
            return;
        }
        ((BarDataSet) ((BarData) this.barChart.getData()).getDataSetByIndex(0)).setValues(arrayList);
        ((BarData) this.barChart.getData()).notifyDataChanged();
        this.barChart.notifyDataSetChanged();
    }

    public String getDate(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }

    public class CustomXAxis extends ValueFormatter implements IAxisValueFormatter {
        List<MonthModel> monthModels;

        public CustomXAxis(List<MonthModel> list) {
            this.monthModels = list;
        }

        public String getFormattedValue(float f, AxisBase axisBase) {
            List<MonthModel> list = this.monthModels;
            if (list == null) {
                return "";
            }
            int i = (int) f;
            if (list.get(i) == null) {
                return "";
            }
            GraphUtils graphUtils = GraphUtils.this;
            if (graphUtils.isMonthWise) {
                return graphUtils.getDate(this.monthModels.get(i).getDateofGraph(), GraphUtils.this.MONTH_FORMATE);
            }
            return String.valueOf(this.monthModels.get(i).getYear());
        }
    }

    public class MyValueFormatter extends ValueFormatter implements IValueFormatter {
        public MyValueFormatter() {
        }

        public String getFormattedValue(float f, Entry entry, int i, ViewPortHandler viewPortHandler) {
            if (((double) f) <= Utils.DOUBLE_EPSILON) {
                return "";
            }
            return "" + ((int) f) + " ";
        }
    }
}
