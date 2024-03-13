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

public class SimpleInterestAdapter extends RecyclerView.Adapter<SimpleInterestAdapter.ViewHolder> {
    Context context;
    ArrayList<MonthModel> monthModels;

    public SimpleInterestAdapter(Context context2, ArrayList<MonthModel> arrayList) {
        this.context = context2;
        this.monthModels = arrayList;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_simple_intrest, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtYear.setText(Utils.decimalFormat.format((long) this.monthModels.get(i).getYear()));
        viewHolder.txtInterest.setText(Utils.decimalFormat.format(this.monthModels.get(i).getInterest()));
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
        TextView txtYear;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.llMain = (LinearLayout) view.findViewById(R.id.llMain);
            this.txtYear = (TextView) view.findViewById(R.id.txtYear);
            this.txtInterest = (TextView) view.findViewById(R.id.txtInterest);
            this.txtBalance = (TextView) view.findViewById(R.id.txtBalance);
        }
    }
}
