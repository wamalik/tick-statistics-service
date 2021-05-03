package com.tickstatistics.processor;

import com.tickstatistics.domain.InstrumentTickDTO;
import com.tickstatistics.domain.StatisticsDTO;
import com.tickstatistics.function.InstrumentStatisticsFunctions;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InstrumentStatisticsProcessor {
    private Map<String, InstrumentTickDTO> instruments = new ConcurrentHashMap<>();

    private final InstrumentStatisticsFunctions instrumentStatisticsFunctions;

    /**
     * post ticks to storage
     *
     * @param instrumentTickDTO
     * @return the boolean
     */
    public Mono<Boolean> postData(final InstrumentTickDTO instrumentTickDTO) {
        return Mono
            .just(instrumentTickDTO)
            .filter(instrumentTickDTO1 -> instrumentStatisticsFunctions.tickWithInSixtySec
                .test(instrumentTickDTO.getTimestamp()))
            .map(instrumentTickDTO1 -> {
                instruments.putIfAbsent(instrumentTickDTO.getInstrument(), instrumentTickDTO);
                return true;
            })
            .switchIfEmpty(Mono.just(false));
    }

    /**
     * calculate the statistics for ticks of all instruments
     * @return the statistics of all ticks
     */
    public Mono<StatisticsDTO> calculateStatistics() {
        return Mono
            .just(instruments.values())
            .map(values -> new ArrayList<>(values))
            .map(list -> StatisticsDTO
                .builder()
                .avg(instrumentStatisticsFunctions.calculateAverage.apply(list))
                .max(instrumentStatisticsFunctions.calculateMax.apply(list))
                .min(instrumentStatisticsFunctions.calculateMin.apply(list))
                .count(list.size())
                .build());
    }


    /**
     * calculate the statistics for ticks of instruments
     * @return the statistics of all ticks
     */
    public Mono<StatisticsDTO> calculateStatisticsByInstrumentId(final String instrumentId) {
        return Mono
            .just(instruments.values())
            .map(values -> values
                .stream()
                .filter(instrumentTickDTO -> instrumentId.equalsIgnoreCase(instrumentTickDTO.getInstrument()))
                .collect(Collectors.toList()))
            .map(list -> StatisticsDTO
                .builder()
                .avg(instrumentStatisticsFunctions.calculateAverage.apply(list))
                .max(instrumentStatisticsFunctions.calculateMax.apply(list))
                .min(instrumentStatisticsFunctions.calculateMin.apply(list))
                .count(list.size())
                .build());
    }
}

