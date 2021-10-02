package com.example.project_1cie2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Fuel> fuelList;
    ReportAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        recyclerView = findViewById(R.id.recycler_view);
        fuelList = new ArrayList<>();
        try {
            fuelList = getDatabaseObject(this).getFuelList();
        } catch (Exception e) {
            Log.e("Error >>", e.getMessage());
        }
        calculateAvg();
        adapter = new ReportAdapter(fuelList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void calculateAvg() {
        int size = fuelList.size();
        Fuel nextFuelItem = null;
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                nextFuelItem = fuelList.get(i + 1);
            } else {
                nextFuelItem = null;
            }
            if (nextFuelItem != null) {
                float km = nextFuelItem.getOdoMeter() - fuelList.get(i).getOdoMeter();
                float avg = km / nextFuelItem.getVolume();
                fuelList.get(i).setAvg(avg);
                float rsPkm = (fuelList.get(i).getPrice() * nextFuelItem.getVolume()) / km;
                fuelList.get(i).setRsPrKM(rsPkm);
            } else {
                fuelList.get(i).setAvg(0);
                fuelList.get(i).setRsPrKM(0);
            }
        }
    }
    public static DatabaseHelper getDatabaseObject(Context context){
        return new DatabaseHelper(context);
    }
}