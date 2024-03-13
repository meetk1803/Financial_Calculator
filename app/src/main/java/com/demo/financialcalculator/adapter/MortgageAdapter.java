package com.demo.financialcalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.financialcalculator.R;
import com.demo.financialcalculator.model.MonthModel;
import com.demo.financialcalculator.util.Utils;
import java.util.ArrayList;

public class MortgageAdapter extends RecyclerView.Adapter<MortgageAdapter.ViewHolder> {
    Context context;
    boolean isMonthly;
    ArrayList<MonthModel> monthModels;

    public MortgageAdapter(Context context2, ArrayList<MonthModel> arrayList, boolean z) {
        this.context = context2;
        this.monthModels = arrayList;
        this.isMonthly = z;
    }

    public void setList(ArrayList<MonthModel> arrayList, boolean z) {
        this.isMonthly = z;
        this.monthModels = arrayList;
        notifyDataSetChanged();
    }

    public ArrayList<MonthModel> getMonthModels() {
        return this.monthModels;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mortgage, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (this.isMonthly) {
            viewHolder.txtMonth.setText(String.valueOf(this.monthModels.get(i).getDate()));
        } else {
            viewHolder.txtMonth.setText(String.valueOf(this.monthModels.get(i).getYear()));
        }
        viewHolder.txtPrincipal.setText(Utils.decimalFormat.format(this.monthModels.get(i).getPrincipalAmount()));
        viewHolder.txtInterest.setText(Utils.decimalFormat.format(this.monthModels.get(i).getInterest()));
        viewHolder.txtTaxInsPMI.setText(Utils.decimalFormat.format(this.monthModels.get(i).getTaxInsPMI()));
        viewHolder.txtPaid.setText(Utils.decimalFormat.format(this.monthModels.get(i).getTotalPaid()));
        viewHolder.txtBalance.setText(Utils.decimalFormat.format(this.monthModels.get(i).getBalance()));
        if (i % 2 == 0) {
            viewHolder.llMain.setBackgroundColor(this.context.getResources().getColor(R.color.colorWhite));
        } else {
            viewHolder.llMain.setBackgroundColor(this.context.getResources().getColor(R.color.colorLight));
        }
    }

    public int getItemCount() {
        return this.monthModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llMain;
        TextView txtBalance;
        TextView txtInterest;
        TextView txtMonth;
        TextView txtPaid;
        TextView txtPrincipal;
        TextView txtTaxInsPMI;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.llMain = (LinearLayout) view.findViewById(R.id.llMain);
            this.txtMonth = (TextView) view.findViewById(R.id.txtMonth);
            this.txtPrincipal = (TextView) view.findViewById(R.id.txtPrincipal);
            this.txtInterest = (TextView) view.findViewById(R.id.txtInterest);
            this.txtTaxInsPMI = (TextView) view.findViewById(R.id.txtTaxInsPMI);
            this.txtPaid = (TextView) view.findViewById(R.id.txtPaid);
            this.txtBalance = (TextView) view.findViewById(R.id.txtBalance);
        }
    }
}
