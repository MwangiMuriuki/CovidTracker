package com.example.covidtracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.covidtracker.Adapters.AdapterCountry;
import com.example.covidtracker.Config;
import com.example.covidtracker.ModelClasses.ModelClassCountryData;
import com.example.covidtracker.ModelClasses.ModelClassGlobal;
import com.example.covidtracker.R;
import com.example.covidtracker.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "My Activity";
    List<ModelClassCountryData> countryDataArrayList = new ArrayList<>();
    List<ModelClassGlobal> globalDataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AdapterCountry adapterCountry;


    private PieChartData data;
    PieChart chart;

    String dataTitle[] = {"Total Cases", "Total Deaths", "Active Cases", "Recoveries"};
    Float dataValue[] = {3364110.0f, 237465.0f, 2057604.0f, 1069041.0f };

    private boolean hasLabels = true;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Covid Tracker");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AndroidNetworking.initialize(getApplicationContext());

        binding.indeterminateBar.bringToFront();

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        chart = findViewById(R.id.pieChart);
        binding.showHideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.pieChart.getVisibility() == View.VISIBLE){
                    binding.pieChart.setVisibility(View.GONE);
                    binding.showHideLabel.setText("Show");
                }
                else {
                    binding.pieChart.setVisibility(View.VISIBLE);
                    binding.showHideLabel.setText("Hide");
                }
            }
        });


        setUpPieChart();

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

                            binding.indeterminateBar.setVisibility(View.GONE);

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Error" + anError, Toast.LENGTH_SHORT).show();

                        Log.e(TAG, "Error fetching Response" + String.valueOf(anError));
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView search_view = (SearchView) item.getActionView();

        search_view.setQueryHint("Search");

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCountry.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setUpPieChart() {

        AndroidNetworking.get(Config.BASE_URL + Config.GLOBAL_ENDPOINT)
                .setTag("all")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(ModelClassGlobal.class, new ParsedRequestListener<ModelClassGlobal>() {
                    @Override
                    public void onResponse(ModelClassGlobal response) {
                        if (response!=null){
                            float total_cases = response.getCases();
                            float total_deaths = response.getDeaths();
                            float active_cases = response.getActive();
                            float recoveries = response.getRecovered();

                            String dataTitle[] = {"Total Cases", "Total Deaths", "Active Cases", "Recoveries"};

                            Float dataValue[] = {total_cases, total_deaths, active_cases, recoveries };

                            List<PieEntry> pieEntries = new ArrayList<>();

                            //Populate list of colors:
                            int[] My_Colors = {
                                    Color.rgb(26, 76, 255),//total cases #1a4cff
                                    Color.rgb(215,10,10),//deaths #f01764
                                    Color.rgb(128,0,255),//Active cases
                                    Color.rgb(7,201,39) //recoveries
                            };

                            ArrayList<Integer> colors = new ArrayList<>();
                            for (int c : My_Colors){
                                colors.add(c);
                            }

                            for(int i = 0; i<dataValue.length; i++){
                                pieEntries.add(new PieEntry(dataValue[i], dataTitle[i]));
                            }

                            PieDataSet dataSet = new PieDataSet(pieEntries, "");
                            dataSet.setColors(My_Colors);
                            dataSet.setHighlightEnabled(true);
                            dataSet.setDrawValues(true);
                            dataSet.setValueTextColor(Color.WHITE);
                            dataSet.setValueTextSize(12);
                            PieData data = new PieData(dataSet);

                            Legend legend = chart.getLegend();
                            legend.setTextSize(12);
                            legend.setFormSize(12);

                            //Populate the chart
                            chart.setData(data);
                            chart.animateY(1200);
                            chart.getDescription().setEnabled(false);
                            chart.setDrawSliceText(false);
                            chart.setHoleRadius(45.0f);
                            chart.setDragDecelerationFrictionCoef(0.98f);
                            chart.setHighlightPerTapEnabled(true);
                            chart.setTransparentCircleColor(Color.WHITE);
                            chart.setTransparentCircleAlpha(110);
                            chart.setTransparentCircleRadius(61f);
                            chart.invalidate();

                            adapterCountry = new AdapterCountry(getApplicationContext(), countryDataArrayList);
                            binding.recyclerView.setAdapter(adapterCountry);

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

}
