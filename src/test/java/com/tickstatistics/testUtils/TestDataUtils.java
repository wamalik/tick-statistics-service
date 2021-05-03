package com.tickstatistics.testUtils;

import com.tickstatistics.domain.InstrumentTickDTO;
import com.tickstatistics.domain.StatisticsDTO;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import reactor.core.publisher.Mono;

public class TestDataUtils {

    public static final Supplier<Mono<InstrumentTickDTO>> TICK_REQUEST = () -> Mono.just(InstrumentTickDTO
        .builder()
        .instrument("IBM.N")
        .price(143.82)
        .timestamp(1478192204000L)
        .build());

    public static final Supplier<Mono<StatisticsDTO>> STATISTICS_RESPONSE = () -> Mono.just(StatisticsDTO
        .builder()
        .avg(143.832)
        .max(143.82)
        .min(143.82)
        .count(1)
        .build());

    public static final Supplier<Map<String, InstrumentTickDTO>> INSTRUMENT_MAP = () -> {
        final Map<String, InstrumentTickDTO> map = new ConcurrentHashMap<String, InstrumentTickDTO>();
        InstrumentTickDTO instrumentTickDTO = InstrumentTickDTO
            .builder()
            .instrument("IBM.N")
            .price(143.82)
            .timestamp(1478192204000L)
            .build();

        map.put("IBM.N", instrumentTickDTO);
        return map;
    };

}
