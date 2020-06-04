package com.rakers.efdsv.services;

import com.rakers.efdsv.exceptions.APIRuntimeException;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Iterator;

public interface CoronaVirusDataService {

    String fetchVirusData(String uri, boolean isSyn) throws APIRuntimeException, IOException;

    Iterator<CSVRecord> parseCSVIterator(String uri, boolean isSyn);

}
