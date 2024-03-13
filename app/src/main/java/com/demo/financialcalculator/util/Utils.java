package com.demo.financialcalculator.util;

import android.content.Context;
import android.os.Build;
import androidx.core.content.ContextCompat;
import com.demo.financialcalculator.model.MonthModel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static double Interest = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
    public static double Paid = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
    public static double Principal = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
    public static final int REQUEST = 112;
    public static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static boolean isMonthly = true;
    public static boolean isYearly = false;
    public static double mTaxInsPMI;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static double getInterestOnly(double d, double d2) {
        return ((d / 12.0d) / 100.0d) * d2;
    }

    public static double getTotalInterest(double d, double d2, double d3) {
        return (d * d2) - d3;
    }

    public static boolean hasPermissions(Context context, String... strArr) {
        if (Build.VERSION.SDK_INT < 23 || context == null || strArr == null) {
            return true;
        }
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    public static Date CALDATE(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(str);
            System.out.println(simpleDateFormat.format(date));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static double getMonthlyPayment(double d, double d2, double d3) {
        double d4 = (d2 / 100.0d) / 12.0d;
        double pow = Math.pow(d4 + 1.0d, d3);
        return ((d * d4) * pow) / (pow - 1.0d);
    }

    public static ArrayList<MonthModel> getYearlyAmount(ArrayList<MonthModel> arrayList, Date date, Date date2) {
        int i;
        ArrayList<MonthModel> arrayList2 = arrayList;
        Date date3 = date;
        Date date4 = date2;
        ArrayList<MonthModel> arrayList22 = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        int parseInt = Integer.parseInt(simpleDateFormat.format(date3));
        int parseInt2 = Integer.parseInt(simpleDateFormat.format(date4));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM");
        int parseInt3 = Integer.parseInt(simpleDateFormat2.format(date3));
        int parseInt4 = Integer.parseInt(simpleDateFormat2.format(date4));
        if (arrayList2 == null || arrayList.size() <= 0) {
            int i2 = parseInt;
            int i3 = parseInt2;
            SimpleDateFormat simpleDateFormat3 = simpleDateFormat2;
        } else {
            int i22 = parseInt;
            int i32 = 0;
            double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            double d2 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            while (i22 <= parseInt2 && i32 < arrayList.size()) {
                if (parseInt == parseInt2) {
                    i = arrayList.size();
                    i32 = 0;
                } else if (i22 == parseInt) {
                    i = (12 - parseInt3) + 1;
                    i32 = 0;
                } else {
                    i = i22 == parseInt2 ? (i32 + parseInt4) - 1 : i32 + 12;
                }
                int i4 = parseInt3;
                int i5 = parseInt4;
                SimpleDateFormat simpleDateFormat4 = simpleDateFormat;
                int parseInt5 = parseInt;
                double d3 = d;
                int parseInt22 = parseInt2;
                SimpleDateFormat simpleDateFormat22 = simpleDateFormat2;
                double d4 = d2;
                int i6 = parseInt3;
                int i7 = parseInt4;
                double d5 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                double d6 = d;
                double d62 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                double d7 = d6;
                while (i32 < i) {
                    d5 += arrayList2.get(i32).getPrincipalAmount();
                    d62 += arrayList2.get(i32).getInterest();
                    d3 += d62;
                    d4 += d5;
                    i32++;
                }
                int i8 = i32;
                MonthModel monthModel = new MonthModel();
                monthModel.setDate(new Date());
                monthModel.setPrincipalAmount(d5);
                monthModel.setInterest(d62);
                double d8 = d2;
                monthModel.setTotalPaid(d5 + d62);
                monthModel.setTotalPrincipal(d4);
                monthModel.setTotalInterest(d3);
                monthModel.setYear(i22);
                monthModel.setBalance(arrayList2.get(i - 1).getBalance());
                arrayList22.add(monthModel);
                i22++;
                double d22 = d4;
                int i33 = i;
                parseInt4 = i5;
                Date date5 = date;
                parseInt3 = i4;
                d = d3;
                simpleDateFormat = simpleDateFormat4;
                parseInt = parseInt5;
                parseInt2 = parseInt22;
                simpleDateFormat2 = simpleDateFormat22;
                i32 = i33;
                d2 = d22;
                Date date6 = date2;
            }
            int i9 = parseInt;
            int i10 = parseInt2;
            SimpleDateFormat simpleDateFormat5 = simpleDateFormat2;
            double d9 = d;
            double d10 = d2;
            int i11 = parseInt4;
        }
        return arrayList22;
    }

    public static ArrayList<MonthModel> getMonthlyAmount(double d, double d2, double d3, double d4, Date date) {
        ArrayList<MonthModel> arrayList = new ArrayList<>();
        double d5 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d6 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d7 = d;
        for (int i = 0; ((double) i) < d2; i++) {
            MonthModel monthModel = new MonthModel();
            double d8 = ((d3 / 12.0d) * d7) / 100.0d;
            double d9 = d4 - d8;
            d7 -= d9;
            double d10 = d9 + d8;
            d5 += d8;
            d6 += d9;
            if (d7 < com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                d7 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(2, i);
            monthModel.setPrincipalAmount(d9);
            monthModel.setInterest(d8);
            monthModel.setBalance(d7);
            monthModel.setTotalPaid(d10);
            monthModel.setTotalPrincipal(d6);
            monthModel.setTotalInterest(d5);
            monthModel.setDate(calendar.getTime());
            arrayList = arrayList;
            arrayList.add(monthModel);
            Principal += d9;
            Interest += d8;
            Paid += d10;
        }
        return arrayList;
    }

    public static ArrayList<MonthModel> getYearlyMortgage(ArrayList<MonthModel> arrayList, Date date, Date date2) {
        int i;
        ArrayList<MonthModel> arrayList2 = arrayList;
        Date date3 = date;
        Date date4 = date2;
        ArrayList<MonthModel> arrayList22 = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        int parseInt = Integer.parseInt(simpleDateFormat.format(date3));
        int parseInt2 = Integer.parseInt(simpleDateFormat.format(date4));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM");
        int parseInt3 = Integer.parseInt(simpleDateFormat2.format(date3));
        int parseInt4 = Integer.parseInt(simpleDateFormat2.format(date4));
        if (arrayList2 == null || arrayList.size() <= 0) {
            SimpleDateFormat simpleDateFormat3 = simpleDateFormat2;
        } else {
            int i2 = parseInt;
            int i3 = 0;
            double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            double d2 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            while (i2 <= parseInt2 && i3 < arrayList.size()) {
                if (parseInt == parseInt2) {
                    i = arrayList.size();
                    i3 = 0;
                } else if (i2 == parseInt) {
                    i = (12 - parseInt3) + 1;
                    i3 = 0;
                } else {
                    i = i2 == parseInt2 ? (i3 + parseInt4) - 1 : i3 + 12;
                }
                int i4 = parseInt3;
                int i5 = parseInt4;
                int i6 = parseInt2;
                int i7 = parseInt;
                double d5 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                double d6 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                SimpleDateFormat simpleDateFormat4 = simpleDateFormat;
                int i8 = parseInt;
                double d3 = d;
                int i9 = parseInt2;
                SimpleDateFormat simpleDateFormat22 = simpleDateFormat2;
                double d4 = d2;
                double d7 = d5;
                int i10 = parseInt3;
                int i11 = parseInt4;
                double d52 = d7;
                double d8 = d2;
                double d72 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                double d9 = d8;
                double d10 = d;
                double d62 = d6;
                double d63 = d10;
                while (i3 < i) {
                    d62 += arrayList2.get(i3).getPrincipalAmount();
                    d72 += arrayList2.get(i3).getInterest();
                    d52 += arrayList2.get(i3).getTaxInsPMI();
                    d3 += d72;
                    d4 += d62;
                    i3++;
                }
                int i12 = i3;
                MonthModel monthModel = new MonthModel();
                monthModel.setDate(new Date());
                monthModel.setPrincipalAmount(d62);
                monthModel.setInterest(d72);
                monthModel.setTaxInsPMI(d52);
                monthModel.setTotalTax(d52);
                monthModel.setTotalInterest(d3);
                monthModel.setTotalPrincipal(d4);
                double d11 = d62;
                monthModel.setTotalPaid(d62 + d72 + d52);
                monthModel.setYear(i2);
                monthModel.setBalance(arrayList2.get(i - 1).getBalance());
                arrayList22.add(monthModel);
                i2++;
                d = d3;
                double d22 = d4;
                int i32 = i;
                parseInt4 = i5;
                Date date5 = date;
                i3 = i32;
                simpleDateFormat = simpleDateFormat4;
                parseInt = i7;
                simpleDateFormat2 = simpleDateFormat22;
                parseInt2 = i6;
                parseInt3 = i4;
                d2 = d22;
                Date date6 = date2;
            }
            SimpleDateFormat simpleDateFormat5 = simpleDateFormat2;
            double d12 = d;
            double d13 = d2;
            int i13 = parseInt2;
            int i14 = parseInt3;
            int i15 = parseInt4;
        }
        return arrayList22;
    }

    public static ArrayList<MonthModel> getMonthlyMortgage(double d, double d2, double d3, double d4, double d5, double d6, Date date, double d7) {
        double d11;
        double d8;
        ArrayList<MonthModel> arrayList = new ArrayList<>();
        double d10 = d - d7;
        int i = 0;
        double d112 = d6;
        double d12 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d16 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d13 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        while (((double) i) < d2) {
            MonthModel monthModel = new MonthModel();
            if ((80.0d * d) / 100.0d > d10) {
                d112 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            double d15 = ((d3 / 12.0d) * d10) / 100.0d;
            double d132 = d16;
            double d133 = d4 - d15;
            double d17 = d10 - d133;
            ArrayList<MonthModel> arrayList2 = arrayList;
            double d9 = d10;
            ArrayList<MonthModel> arrayList3 = arrayList;
            double d18 = d133 + d15 + d5 + d112;
            double d19 = d5 + d112;
            int i2 = i;
            double d20 = d13 + d19;
            d12 += d15;
            double d14 = d13;
            double d142 = d132 + d133;
            if (d17 < com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                d8 = d112;
                d11 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            } else {
                d8 = d112;
                d11 = d17;
            }
            double d21 = d112;
            double d113 = d11;
            double d22 = d21;
            double d82 = d8;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            int i3 = i2;
            calendar.add(2, i3);
            monthModel.setPrincipalAmount(d133);
            monthModel.setInterest(d15);
            monthModel.setBalance(d113);
            double d212 = d113;
            double d23 = d113;
            double d182 = d18;
            monthModel.setTotalPaid(d182);
            monthModel.setTotalPrincipal(d142);
            monthModel.setTaxInsPMI(d19);
            monthModel.setTotalInterest(d12);
            double d24 = d19;
            double d192 = d20;
            monthModel.setTotalTax(d192);
            monthModel.setDate(calendar.getTime());
            ArrayList<MonthModel> arrayList22 = arrayList2;
            arrayList22.add(monthModel);
            Principal += d133;
            Interest += d15;
            mTaxInsPMI += d5;
            Paid += d182;
            i = i3 + 1;
            double d143 = d192;
            d10 = d212;
            d16 = d142;
            arrayList = arrayList22;
            d13 = d143;
            d112 = d82;
        }
        return arrayList;
    }

    public static ArrayList<MonthModel> getYearlyCompound(ArrayList<MonthModel> arrayList, Date date, Date date2) {
        int i;
        ArrayList<MonthModel> arrayList2 = arrayList;
        Date date3 = date;
        Date date4 = date2;
        ArrayList<MonthModel> arrayList22 = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        int parseInt = Integer.parseInt(simpleDateFormat.format(date3));
        int parseInt2 = Integer.parseInt(simpleDateFormat.format(date4));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM");
        int parseInt3 = Integer.parseInt(simpleDateFormat2.format(date3));
        int parseInt4 = Integer.parseInt(simpleDateFormat2.format(date4));
        if (arrayList2 == null || arrayList.size() <= 0) {
            int i2 = parseInt;
            int i3 = parseInt2;
            SimpleDateFormat simpleDateFormat3 = simpleDateFormat2;
        } else {
            int i22 = parseInt;
            int i32 = 0;
            double d = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            double d2 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            while (i22 <= parseInt2 && i32 < arrayList.size()) {
                if (parseInt == parseInt2) {
                    i = arrayList.size();
                    i32 = 1;
                } else if (i22 == parseInt) {
                    i = (12 - parseInt3) + 1;
                    i32 = 0;
                } else {
                    i = i22 == parseInt2 ? (i32 + parseInt4) - 1 : i32 + 12;
                }
                int i4 = parseInt3;
                int i5 = parseInt4;
                SimpleDateFormat simpleDateFormat4 = simpleDateFormat;
                int parseInt5 = parseInt;
                double d3 = d;
                int parseInt22 = parseInt2;
                SimpleDateFormat simpleDateFormat22 = simpleDateFormat2;
                double d4 = d2;
                int i6 = parseInt3;
                int i7 = parseInt4;
                double d5 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                double d6 = d;
                double d62 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                double d7 = d6;
                while (i32 < i) {
                    d5 += arrayList2.get(i32).getPrincipalAmount();
                    d62 += arrayList2.get(i32).getInterest();
                    d3 += d62;
                    d4 += d5;
                    i32++;
                }
                int i8 = i32;
                MonthModel monthModel = new MonthModel();
                monthModel.setDate(new Date());
                monthModel.setPrincipalAmount(d5);
                monthModel.setInterest(d62);
                monthModel.setTotalPrincipal(d4);
                double d8 = d2;
                monthModel.setTotalPaid(d5 + d62);
                monthModel.setTotalInterest(d3);
                monthModel.setYear(i22);
                monthModel.setBalance(arrayList2.get(i - 1).getBalance());
                arrayList22.add(monthModel);
                i22++;
                double d22 = d4;
                int i33 = i;
                parseInt4 = i5;
                Date date5 = date;
                parseInt3 = i4;
                d = d3;
                simpleDateFormat = simpleDateFormat4;
                parseInt = parseInt5;
                parseInt2 = parseInt22;
                simpleDateFormat2 = simpleDateFormat22;
                i32 = i33;
                d2 = d22;
                Date date6 = date2;
            }
            int i9 = parseInt;
            int i10 = parseInt2;
            SimpleDateFormat simpleDateFormat5 = simpleDateFormat2;
            double d9 = d;
            double d10 = d2;
            int i11 = parseInt4;
        }
        return arrayList22;
    }

    public static ArrayList<MonthModel> getMonthlyCompound(double d, double d2, double d3, double d4, Date date) {
        ArrayList<MonthModel> arrayList = new ArrayList<>();
        int i = 0;
        double d5 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d6 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d7 = d;
        while (((double) i) < d2) {
            MonthModel monthModel = new MonthModel();
            double d8 = i == 0 ? d4 + d : d4;
            double d9 = ((d3 / 12.0d) / 100.0d) * (d7 + d4);
            d7 = i == 0 ? d9 + d + d4 : d9 + d8 + d7;
            d5 += d9;
            d6 += d8;
            new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(2, i);
            monthModel.setPrincipalAmount(d8);
            monthModel.setInterest(d9);
            monthModel.setTotalInterest(d5);
            monthModel.setTotalPrincipal(d6);
            monthModel.setBalance(d7);
            monthModel.setDate(calendar.getTime());
            arrayList.add(monthModel);
            Principal += d8;
            Interest += d9;
            i++;
        }
        Date date2 = date;
        Paid = d7;
        return arrayList;
    }

    public static ArrayList<MonthModel> getMonthlyInterest(double d, double d2, double d3, double d4, double d5, Date date) {
        double d6;
        ArrayList<MonthModel> arrayList = new ArrayList<>();
        int i = 0;
        double d62 = d;
        double d7 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        while (true) {
            double d8 = (double) i;
            if (d8 >= d2) {
                return arrayList;
            }
            MonthModel monthModel = new MonthModel();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            calendar.add(2, i);
            calendar.getTime();
            if (d8 < d5) {
                double d9 = ((d3 / 12.0d) * d62) / 100.0d;
                double d10 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                double d102 = d9 + d10;
                d7 += d10;
                if (d62 < d10) {
                    d62 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                }
                monthModel.setPrincipalAmount(d10);
                monthModel.setInterest(d9);
                monthModel.setBalance(d62);
                monthModel.setTotalPaid(d102);
                monthModel.setTotalPrincipal(d7);
                d6 = d62;
                monthModel.setDate(calendar.getTime());
                arrayList.add(monthModel);
                Principal += d10;
                Interest += d9;
                Paid += d102;
            } else {
                double d11 = ((d3 / 12.0d) * d62) / 100.0d;
                double d12 = d4 - d11;
                double d63 = d62 - d12;
                double d13 = d12 + d11;
                d7 += d12;
                if (d63 < com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                    d63 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                }
                monthModel.setPrincipalAmount(d12);
                monthModel.setInterest(d11);
                monthModel.setBalance(d63);
                monthModel.setTotalPrincipal(d7);
                monthModel.setTotalPaid(d13);
                d6 = d63;
                monthModel.setDate(calendar.getTime());
                arrayList.add(monthModel);
                Principal += d12;
                Interest += d11;
                Paid += d13;
            }
            d62 = d6;
            i++;
        }
    }

    public static ArrayList<MonthModel> getYearlyRefinanceAmount(double d, double d2, double d3, double d4, int i) {
        ArrayList<MonthModel> arrayList = new ArrayList<>();
        double d5 = d;
        int i2 = i;
        for (int i3 = 0; ((double) i3) < d2; i3++) {
            MonthModel monthModel = new MonthModel();
            double d6 = ((d3 / 12.0d) * d5) / 100.0d;
            double d7 = d4 - d6;
            d5 -= d7;
            double d8 = d7 + d6;
            if (d5 < com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                d5 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            monthModel.setPrincipalAmount(d7);
            monthModel.setInterest(d6);
            monthModel.setBalance(d5);
            monthModel.setTotalPaid(d8);
            monthModel.setYear(i2);
            arrayList.add(monthModel);
            i2++;
            Principal += d7;
            Interest += d6;
            Paid += d8;
        }
        return arrayList;
    }

    public static ArrayList<MonthModel> getYearlySimpleInterest(double d, double d2, double d3, int i) {
        ArrayList<MonthModel> arrayList = new ArrayList<>();
        double d4 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d5 = d;
        for (int i2 = 0; ((double) i2) < d2; i2++) {
            MonthModel monthModel = new MonthModel();
            d5 += d3;
            d4 += d3;
            monthModel.setPrincipalAmount(d);
            monthModel.setInterest(d3);
            monthModel.setTotalInterest(d4);
            monthModel.setTotalPrincipal(d);
            monthModel.setBalance(d5);
            monthModel.setYear(i);
            arrayList.add(monthModel);
            i++;
        }
        return arrayList;
    }

    public static ArrayList<MonthModel> getYearlyLoanCompare(double d, double d2, double d3, double d4) {
        double d5;
        ArrayList<MonthModel> arrayList = new ArrayList<>();
        double d6 = 12.0d;
        int i = (int) (12.0d * d2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i2 = calendar.get(1);
        int i3 = 0;
        double d7 = d;
        double d8 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d9 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        double d10 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        while (i3 < i) {
            double d11 = ((d3 / d6) * d7) / 100.0d;
            double d12 = d4 - d11;
            d7 -= d12;
            double d13 = d12 + d11;
            if (d7 < com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                d7 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
            }
            double d14 = d6;
            if (i2 != calendar.get(1)) {
                MonthModel monthModel = new MonthModel();
                monthModel.setPrincipalAmount(d8);
                monthModel.setInterest(d9);
                monthModel.setBalance(d7);
                monthModel.setTotalPaid(d10);
                monthModel.setYear(i2);
                arrayList.add(monthModel);
                int i22 = calendar.get(1);
                d5 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                d8 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                d9 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
                i2 = i22;
            } else {
                d5 = d10;
            }
            d8 += d12;
            d9 += d11;
            d10 = d5 + d13;
            Principal += d12;
            Interest += d11;
            Paid += d13;
            calendar.add(2, 1);
            i3++;
            d6 = 12.0d;
        }
        double d15 = d6;
        MonthModel monthModel2 = new MonthModel();
        monthModel2.setPrincipalAmount(d8);
        monthModel2.setInterest(d9);
        monthModel2.setBalance(d7);
        monthModel2.setTotalPaid(d10);
        monthModel2.setYear(calendar.get(1));
        arrayList.add(monthModel2);
        return arrayList;
    }
}
