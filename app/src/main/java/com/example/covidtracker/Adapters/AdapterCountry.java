package com.example.covidtracker.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidtracker.Activities.MainActivity;
import com.example.covidtracker.Fragments.DialogFragmentCountryDetails;
import com.example.covidtracker.ModelClasses.CountryInfo;
import com.example.covidtracker.ModelClasses.ModelClassCountryData;
import com.example.covidtracker.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCountry extends RecyclerView.Adapter<AdapterCountry.MyViewHolder> implements Filterable {

    Context context;
    List<ModelClassCountryData> countryData;
    List<ModelClassCountryData> countryDataFiltered;

    CountryInfo extraInfo;

    public AdapterCountry(Context context, List<ModelClassCountryData> countryData) {
        this.context = context;
        this.countryData = countryData;
        countryDataFiltered = new ArrayList<>(countryData);

    }

    @NonNull
    @Override
    public AdapterCountry.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_county_list, parent, false);
        return new AdapterCountry.MyViewHolder(vv);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCountry.MyViewHolder holder, int position) {

        ModelClassCountryData countryDataList = countryData.get(position);
        extraInfo = countryDataList.getCountryInfo();
        final String countryName = countryDataList.getCountry();
        final String continent = countryDataList.getContinent();
        final String totalCases = String.valueOf(countryDataList.getCases());
        final String newCases = String.valueOf(countryDataList.getTodayCases());
        final String activeCases = String.valueOf(countryDataList.getActive());
        final String criticalCases = String.valueOf(countryDataList.getCritical());
        final String totalDeaths = String.valueOf(countryDataList.getDeaths());
        final String newDeaths = String.valueOf(countryDataList.getTodayDeaths());
        final String recoveredCases = String.valueOf(countryDataList.getRecovered());
        final String tests = String.valueOf(countryDataList.getTests());

        final String flagUrl = extraInfo.getFlag();

        holder.countryLabel.setText(countryDataList.getCountry());
        holder.casesLabel.setText(String.valueOf(countryDataList.getCases()));
        Glide.with(context).load(flagUrl).into(holder.countryFlag);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(view.getContext(), countryName,continent, totalCases, newCases, activeCases,
                        criticalCases,totalDeaths, newDeaths, recoveredCases, tests, flagUrl);
            }
        });

    }

    private void showAlertDialog(Context context, String countryName, String continent, String totalCases, String newCases, String activeCases, String criticalCases, String totalDeaths, String newDeaths, String recoveredCases, String tests, String flag_Url) {

        View customView = LayoutInflater.from(context).inflate(R.layout.country_details, null);
        TextView country_name = customView.findViewById(R.id.country);
        CircleImageView flagIcon = customView.findViewById(R.id.imageViewFlag);
        TextView continentTV = customView.findViewById(R.id.continent);
        TextView total_tests = customView.findViewById(R.id.totalTests);
        TextView total_cases = customView.findViewById(R.id.totalCases);
        TextView new_cases = customView.findViewById(R.id.newCases);
        TextView Active_cases = customView.findViewById(R.id.activeCases);
        TextView critical_cases = customView.findViewById(R.id.criticalCases);
        TextView total_deaths = customView.findViewById(R.id.totalDeaths);
        TextView new_deaths = customView.findViewById(R.id.newDeaths);
        TextView recovered_cases = customView.findViewById(R.id.recoveredCases);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customView);

        country_name.setText(countryName);
        continentTV.setText(continent);
        total_tests.setText(tests);
        total_cases.setText(totalCases);
        new_cases.setText(newCases);
        Active_cases.setText(activeCases);
        critical_cases.setText(criticalCases);
        total_deaths.setText(totalDeaths);
        new_deaths.setText(newDeaths);
        recovered_cases.setText(recoveredCases);
//        flagIcon.setImageURI(Uri.parse(flag_Url));
        Glide.with(context).load(flag_Url).into(flagIcon);


        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return countryData.size();
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ModelClassCountryData> filteredDataList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredDataList.addAll(countryDataFiltered);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelClassCountryData data:countryDataFiltered) {

                    if (data.getCountry().toLowerCase().contains(filterPattern)){
                        filteredDataList.add(data);
                    }
                    
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredDataList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            countryData.clear();
            countryData.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView countryLabel;
        TextView casesLabel;
        CircleImageView countryFlag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.main_layout);
            countryLabel = itemView.findViewById(R.id.countryName);
            casesLabel = itemView.findViewById(R.id.countryCases);
            countryFlag = itemView.findViewById(R.id.countryFlag);
        }
    }
}
