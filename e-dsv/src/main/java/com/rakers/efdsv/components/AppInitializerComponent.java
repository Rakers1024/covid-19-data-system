package com.rakers.efdsv.components;

import com.rakers.efdsv.Constants;
import com.rakers.efdsv.exceptions.APIRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class AppInitializerComponent implements Constants {
    private static final Logger logger = LoggerFactory.getLogger(AppInitializerComponent.class);

    @Autowired
    CoronaVirusDataImpl virusData;

    /**
     * 每30分钟触发一次，用于初始化和同步redis数据
     * @throws APIRuntimeException
     * @throws IOException
     */
    @PostConstruct
    @Scheduled(cron = "0 */30 * ? * *")
    public void init() throws APIRuntimeException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                virusData.setData(VIRUS_DATA_URL, true);
                virusData.setDeathData(DEATH_DATA_URL, true);
                virusData.setCountryDataMap();
            }
        }).start();
    }
}
