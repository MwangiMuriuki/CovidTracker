package com.example.covidtracker.Fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidtracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragmentCountryDetails extends DialogFragment {

    public DialogFragmentCountryDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_country_details, container, false);
    }
}
