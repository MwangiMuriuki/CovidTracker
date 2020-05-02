package com.example.covidtracker.ModelClasses;

public class ModelClassGlobal {

    float updated;
      float cases;
      float todayCases;
      float deaths;
      float todayDeaths;
      float recovered;
      float active;
      float critical;
      float casesPerOneMillion;
      float deathsPerOneMillion;
      float tests;
      float testsPerOneMillion;
      float affectedCountries;

    public ModelClassGlobal() {
    }

    public float getUpdated() {
        return updated;
    }

    public void setUpdated(float updated) {
        this.updated = updated;
    }

    public float getCases() {
        return cases;
    }

    public void setCases(float cases) {
        this.cases = cases;
    }

    public float getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(float todayCases) {
        this.todayCases = todayCases;
    }

    public float getDeaths() {
        return deaths;
    }

    public void setDeaths(float deaths) {
        this.deaths = deaths;
    }

    public float getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(float todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public float getRecovered() {
        return recovered;
    }

    public void setRecovered(float recovered) {
        this.recovered = recovered;
    }

    public float getActive() {
        return active;
    }

    public void setActive(float active) {
        this.active = active;
    }

    public float getCritical() {
        return critical;
    }

    public void setCritical(float critical) {
        this.critical = critical;
    }

    public float getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(float casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public float getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(float deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public float getTests() {
        return tests;
    }

    public void setTests(float tests) {
        this.tests = tests;
    }

    public float getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(float testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public float getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(float affectedCountries) {
        this.affectedCountries = affectedCountries;
    }
}
