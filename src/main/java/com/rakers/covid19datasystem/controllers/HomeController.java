package com.rakers.covid19datasystem.controllers;

import com.rakers.covid19datasystem.exceptions.APIRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final String URL_TSV_TEXT = "http://localhost:8060/tsv/tl/{text}";
    private static final String URL_DSV = "http://localhost:8060/dsv/data";


    @Autowired
    private RestTemplate client;

    @GetMapping("/")
    public String home(Model model) {
        try {
            Map<String, Object> dataMap = this.client.getForEntity(URL_DSV, Map.class).getBody();
            logger.info("通过API网关获取到的数据"+dataMap);
            List<Map<String, Object>> countryStats = (List<Map<String, Object>>)dataMap.get("locationsStats");
            for (Map<String, Object> countryStat : countryStats) {
                countryStat.put("country", this.client.getForEntity(URL_TSV_TEXT, String.class, countryStat.get("country")).getBody());
            }
            logger.info("通过API网关翻译后的数据"+countryStats);
            model.addAttribute("locationsStats", countryStats);
            model.addAttribute("totalReportedCases", dataMap.get("totalReportedCases"));
            model.addAttribute("totalNewCases", dataMap.get("totalNewCases"));
            model.addAttribute("totalDeaths", dataMap.get("totalDeaths"));
            model.addAttribute("totalDeathsToday", dataMap.get("totalDeathsToday"));
            logger.info("页面数据"+model);
        }catch (Exception e){
            throw new APIRuntimeException(e);
        }
        return "home";
    }

    @GetMapping("/data")
    @ResponseBody
    public Map<String, Object> getData(){
        try {
            return this.client.getForEntity(URL_DSV, Map.class).getBody();
        }catch (Exception e){
            logger.error("call city service error", e);
            throw new APIRuntimeException("call city service error", e);
        }
    }

    @GetMapping("/tl/{text}")
    public ResponseEntity<String> getTL(@PathVariable("text") String text){
        try {
            return this.client.getForEntity(URL_TSV_TEXT, String.class, text);
        }catch (Exception e){
            logger.error("call city service error", e);
            throw new APIRuntimeException("call city service error", e);
        }
    }




}
