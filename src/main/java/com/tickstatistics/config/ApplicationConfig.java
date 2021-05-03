package com.tickstatistics.config;

import com.tickstatistics.domain.InstrumentTickDTO;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public Map<String, InstrumentTickDTO> instrumentsMap(){
        final Map<String, InstrumentTickDTO> map = new ConcurrentHashMap<String, InstrumentTickDTO>();
        return map;
    }
}
