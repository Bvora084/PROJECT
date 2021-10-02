package com.example.project_1cie2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private final List<Fuel> fuelList;
    Fuel lastFuel = null;
    float diff = 0;

    public ReportAdapter(List<Fuel> fuelList) {
        this.fuelList = fuelList;
    }

    public void differenceHighlight(float diff) {
        this.diff = diff;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_report, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Fuel fuel = fuelList.get(position);
        if (position < fuelList.size() - 1) {
            lastFuel = fuelList.get(position + 1);
        } else {
            lastFuel = null;
        }
        holder.dateTv.setText(fuel.getDate());
        holder.volumeTv.setText(String.valueOf(fuel.getVolume()));
        holder.priceTv.setText(String.valueOf(fuel.getPrice()));
        holder.odometerTv.setText(String.valueOf(fuel.getOdoMeter()));
        holder.avgTv.setText(String.valueOf(fuel.getAvg()));
        holder.rsPrKM.setText(String.valueOf(fuel.getRsPrKM()));
        if (lastFuel != null) {
            if (diff > 0) {
                if ((fuelList.get(position).getAvg() - diff) > lastFuel.getAvg()) {
                    holder.avgTv.setTextColor(Color.RED);
                } else {
                    holder.avgTv.setTextColor(Color.BLACK);
                }
            } else {
                holder.avgTv.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public int getItemCount() {
        return fuelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTv, volumeTv, priceTv, odometerTv, avgTv, rsPrKM;

        public ViewHolder(View itemView) {
            super(itemView);
            this.dateTv = itemView.findViewById(R.id.date);
            this.volumeTv = itemView.findViewById(R.id.volume);
            this.priceTv = itemView.findViewById(R.id.price);
            this.odometerTv = itemView.findViewById(R.id.odometer);
            this.avgTv = itemView.findViewById(R.id.avg);
            this.rsPrKM = itemView.findViewById(R.id.rsPrKm);
        }
    }
}
