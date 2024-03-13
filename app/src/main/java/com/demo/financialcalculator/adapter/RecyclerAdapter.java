package com.demo.financialcalculator.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.financialcalculator.Interface.RemoveEditText;
import com.demo.financialcalculator.R;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<String> doubleArrayList;
    RemoveEditText removeEditText;

    public RecyclerAdapter(Context context2, ArrayList<String> arrayList, RemoveEditText removeEditText2) {
        this.context = context2;
        this.doubleArrayList = arrayList;
        this.removeEditText = removeEditText2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.recycler_item, viewGroup, false), new MyCustomEditTextListener());
    }

    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextView textView = viewHolder.txtYear;
        textView.setText("Year " + (i + 1));
        viewHolder.myCustomEditTextListener.updatePosition(viewHolder.getAdapterPosition());
        viewHolder.etYear.setText(String.valueOf(this.doubleArrayList.get(viewHolder.getAdapterPosition())));
    }

    public int getItemCount() {
        return this.doubleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText etYear;
        LinearLayout linYear;
        MyCustomEditTextListener myCustomEditTextListener;
        ImageView remove;
        TextView txtYear;

        public ViewHolder(@NonNull View view, MyCustomEditTextListener myCustomEditTextListener2) {
            super(view);
            this.linYear = (LinearLayout) view.findViewById(R.id.linYear);
            this.txtYear = (TextView) view.findViewById(R.id.txtYear);
            this.etYear = (EditText) view.findViewById(R.id.etYear);
            this.remove = (ImageView) view.findViewById(R.id.remove);
            this.myCustomEditTextListener = myCustomEditTextListener2;
            this.etYear.addTextChangedListener(myCustomEditTextListener2);
            this.remove.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view2) {
                    ViewHolder viewHolder = ViewHolder.this;
                    RecyclerAdapter.this.removeEditText.removePosition(viewHolder.getAdapterPosition());
                }
            });
        }
    }

    public class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        private MyCustomEditTextListener() {
        }

        public void updatePosition(int i) {
            this.position = i;
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try {
                RecyclerAdapter.this.doubleArrayList.set(this.position, charSequence.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
