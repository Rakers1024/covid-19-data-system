package com.rakers.efdsv.services;

import com.rakers.efdsv.Constants;
import com.rakers.efdsv.exceptions.APIRuntimeException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.ConnectException;
import java.util.Iterator;

@Service
public class CoronaVirusDataServiceImpl implements CoronaVirusDataService, Constants {
    private static final Logger logger = LoggerFactory.getLogger(CoronaVirusDataServiceImpl.class);

    @Override
    public String fetchVirusData(String uri, boolean isSyn) {
        logger.info("开始同步数据");
        String apiOutput = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet getRequest = new HttpGet(uri);
            HttpResponse response = null;
            response = httpClient.execute(getRequest);
            int statusCode = NOT_FOUND;
            if (response != null && response.getStatusLine() != null)
                statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != SUCCESS) {
                throw new APIRuntimeException("Failed with HTTP error code : " + statusCode);
            }
            HttpEntity httpEntity = response.getEntity();
            apiOutput = EntityUtils.toString(httpEntity);
//            vot.set(uri, apiOutput, TIME_OUT, TimeUnit.SECONDS);
        }catch (ConnectException e) {
            logger.error("数据同步失败");
        }catch (Exception e) {
            logger.error("数据同步失败");
            throw new APIRuntimeException(e);
        }
        logger.info("数据同步成功");
        return apiOutput;
//        logger.info("开始获取数据");
//        ValueOperations<String, String> vot = stringRedisTemplate.opsForValue();
//        if (!isSyn && stringRedisTemplate.hasKey(uri)) {
//            logger.info("在redis中找到key="+uri);
//            try {
//                return vot.get(uri);
//            }catch (Exception e){
//                logger.error(e.getMessage(), e);
//                throw new APIRuntimeException("从redis获取数据时发生错误!");
//            }
//        }else{
//            logger.info("在redis中未找到key");
//            String apiOutput = null;
//            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
//                HttpGet getRequest = new HttpGet(uri);
//                HttpResponse response = null;
//                response = httpClient.execute(getRequest);
//                int statusCode = NOT_FOUND;
//                if (response != null && response.getStatusLine() != null)
//                    statusCode = response.getStatusLine().getStatusCode();
//                if (statusCode != SUCCESS) {
//                    throw new APIRuntimeException("Failed with HTTP error code : " + statusCode);
//                }
//                HttpEntity httpEntity = response.getEntity();
//                apiOutput = EntityUtils.toString(httpEntity);
//                vot.set(uri, apiOutput, TIME_OUT, TimeUnit.SECONDS);
//            }catch (Exception e) {
//                throw new APIRuntimeException(e);
//            }
//            return apiOutput;
//        }
    }

    @Override
    public Iterator<CSVRecord> parseCSVIterator(String uri, boolean isSyn) {
        Iterable<CSVRecord> records = null;
        try {
            StringReader csvReader = new StringReader(fetchVirusData(uri, isSyn));
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records.iterator();
    }
}
