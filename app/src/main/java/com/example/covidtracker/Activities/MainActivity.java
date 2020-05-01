package com.example.covidtracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.covidtracker.Adapters.AdapterCountry;
import com.example.covidtracker.Config;
import com.example.covidtracker.ModelClasses.ModelClassCountryData;
import com.example.covidtracker.R;
import com.example.covidtracker.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "My Activity";
    List<ModelClassCountryData> countryDataArrayList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AdapterCountry adapterCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        generateData();

        AndroidNetworking.get(Config.BASE_URL + Config.COUNTRY_ENDPOINT)
                .setTag("countries_api")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(ModelClassCountryData.class, new ParsedRequestListener<List<ModelClassCountryData>>() {
                    @Override
                    public void onResponse(List<ModelClassCountryData> response) {
                        if (response!=null){
                            countryDataArrayList.addAll(response);

                            adapterCountry = new AdapterCountry(getApplicationContext(), countryDataArrayList);
                            binding.recyclerView.setAdapter(adapterCountry);

                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        }

//                        countryDataArrayList = new AdapterCountry(newLeadsList, getContext());

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Error" + anError, Toast.LENGTH_SHORT).show();

                        Log.e(TAG, "Error fetching Response" + String.valueOf(anError));
                    }
                });
    }

    private void generateData() {

    }

}
