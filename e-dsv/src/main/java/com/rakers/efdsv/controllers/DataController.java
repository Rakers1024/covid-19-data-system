package com.rakers.efdsv.controllers;

import com.rakers.efdsv.Constants;
import com.rakers.efdsv.components.CoronaVirusDataImpl;
import com.rakers.efdsv.models.CoronaCountryModel;
import com.rakers.efdsv.services.CoronaVirusDataServiceImpl;
import com.rakers.efdsv.services.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/dsv")
public class DataController implements Constants {
    public static final Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired
    DataService dataService;

    @Autowired
    CoronaVirusDataImpl virusData;

    @GetMapping("/data")
    @ResponseBody
    public Map<String, Object> data(){
        logger.info("请求获取数据"+dataService.getData());
        return dataService.getData();
    }
}
