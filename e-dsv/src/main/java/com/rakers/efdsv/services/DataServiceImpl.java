package com.rakers.efdsv.services;

import com.rakers.efdsv.components.CoronaVirusData;
import com.rakers.efdsv.exceptions.APIRuntimeException;
import com.rakers.efdsv.models.CoronaCountryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    CoronaVirusData virusData;

    @Autowired
    private RestTemplate client;

    @Override
    public Map<String, Object> getData() {
        Map<String, CoronaCountryModel> dataMap;
        Map<String, Object> list = new HashMap<>();
        try {
            dataMap = virusData.getCountryDataMap();
            List<CoronaCountryModel> countryStats = new ArrayList<>();
            for (Map.Entry<String, CoronaCountryModel> map : dataMap.entrySet()) {
//                CoronaCountryModel coronaCountryModel = new CoronaCountryModel(map.getValue());
//                coronaCountryModel.setCountry(this.client.getForEntity(URL_TSV_TEXT, String.class, coronaCountryModel.getCountry()).getBody());
                countryStats.add(map.getValue());
            }
            int totalReportedCases = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getLatestCases()).sum();
            int totalNewCases = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getDiffFromPrevDay()).sum();
            int totalDeaths = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getDeath()).sum();
            int totalDeathsToday = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getDeathDiffFromPrevDay()).sum();
            list.put("locationsStats", countryStats);
            list.put("totalReportedCases", totalReportedCases);
            list.put("totalNewCases", totalNewCases);
            list.put("totalDeaths", totalDeaths);
            list.put("totalDeathsToday", totalDeathsToday);


        }catch (Exception e){
            logger.error("获取数据异常");
            throw new APIRuntimeException(e);
        }
        return list;
    }
}
