package com.tickstatistics.processor;

import com.tickstatistics.domain.InstrumentTickDTO;
import com.tickstatistics.function.InstrumentFunctions;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TicksProcessor {
    private final Map<String, InstrumentTickDTO> instrumentsMap;
    private final InstrumentFunctions            instrumentFunctions;

    /**
     * post ticks to storage
     *
     * @param instrumentTickDTO
     * @return the boolean
     */
    public Mono<Boolean> postData(final InstrumentTickDTO instrumentTickDTO) {
        return Mono
            .just(instrumentTickDTO)
            .filter(instrumentTickDTO1 -> instrumentFunctions.tickWithInSixtySec
                .test(instrumentTickDTO.getTimestamp()))
            .map(instrumentTickDTO1 -> {
                instrumentsMap.putIfAbsent(instrumentTickDTO.getInstrument(), instrumentTickDTO);
                return true;
            })
            .switchIfEmpty(Mono.just(false));
    }
}
