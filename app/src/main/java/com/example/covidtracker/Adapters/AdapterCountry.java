package com.example.covidtracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidtracker.ModelClasses.CountryInfo;
import com.example.covidtracker.ModelClasses.ModelClassCountryData;
import com.example.covidtracker.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCountry extends RecyclerView.Adapter<AdapterCountry.MyViewHolder> {

    Context context;
    List<ModelClassCountryData> countryData;
    CountryInfo extraInfo;

    public AdapterCountry(Context context, List<ModelClassCountryData> countryData) {
        this.context = context;
        this.countryData = countryData;
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
        String flagUrl = extraInfo.getFlag();

        holder.countryLabel.setText(countryDataList.getCountry());
        holder.casesLabel.setText(String.valueOf(countryDataList.getCases()));
        Glide.with(context).load(flagUrl).into(holder.countryFlag);

    }

    @Override
    public int getItemCount() {
        return countryData.size();
    }

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
