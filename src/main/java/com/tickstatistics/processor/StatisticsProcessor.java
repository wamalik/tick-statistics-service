package com.tickstatistics.processor;

import com.tickstatistics.domain.InstrumentTickDTO;
import com.tickstatistics.domain.StatisticsDTO;
import com.tickstatistics.function.InstrumentFunctions;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StatisticsProcessor {
    private final Map<String, InstrumentTickDTO> instrumentsMap;
    private final InstrumentFunctions            instrumentFunctions;


    /**
     * calculate the statistics for ticks of all instruments
     *
     * @return the statistics of all ticks
     */
    public Mono<StatisticsDTO> calculateStatistics() {
        return Mono
            .just(instrumentsMap.values())
            .map(values -> new ArrayList<>(values))
            .map(list -> StatisticsDTO
                .builder()
                .avg(instrumentFunctions.calculateAverage.apply(list))
                .max(instrumentFunctions.calculateMax.apply(list))
                .min(instrumentFunctions.calculateMin.apply(list))
                .count(list.size())
                .build())
            .switchIfEmpty(Mono.empty());
    }

    /**
     * calculate the statistics for ticks of instruments
     *
     * @return the statistics of all ticks
     */
    public Mono<StatisticsDTO> calculateStatisticsByInstrumentId(final String instrumentId) {
        return Mono
            .just(instrumentsMap.values())
            .map(values -> values
                .stream()
                .filter(instrumentTickDTO -> instrumentId.equalsIgnoreCase(instrumentTickDTO.getInstrument()))
                .collect(Collectors.toList()))
            .map(list -> StatisticsDTO
                .builder()
                .avg(instrumentFunctions.calculateAverage.apply(list))
                .max(instrumentFunctions.calculateMax.apply(list))
                .min(instrumentFunctions.calculateMin.apply(list))
                .count(list.size())
                .build());
    }
}

