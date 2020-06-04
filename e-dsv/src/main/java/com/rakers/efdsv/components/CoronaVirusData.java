package com.rakers.efdsv.components;


import com.rakers.efdsv.models.CoronaCountryModel;

import java.util.Map;

public interface CoronaVirusData {

    void setData(String uri, boolean isSyn);

    Map<String, CoronaCountryModel> getCountryDataMap();

    void setCountryDataMap();

}
