package com.rakers.etsv.controllers;

import com.rakers.etsv.services.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tsv")
public class TranslateController {

    @Autowired
    TranslateService tsi;

    @GetMapping("/tl/{text}")
    @ResponseBody
    public String tl(@PathVariable("text") String text){
        return tsi.usToCn(text);
    }

    @GetMapping("/{text}")
    @ResponseBody
    public String toCN(@PathVariable("text") String text){
        return tsi.usToCn(text);
    }

}
