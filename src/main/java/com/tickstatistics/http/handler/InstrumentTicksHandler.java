package com.tickstatistics.http.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.tickstatistics.domain.InstrumentTickDTO;
import com.tickstatistics.processor.StatisticsProcessor;
import com.tickstatistics.processor.TicksProcessor;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InstrumentTicksHandler {

    private final StatisticsProcessor statisticsProcessor;
    private final TicksProcessor      ticksProcessor;

    /**
     * Post ticks for instrument
     *
     * @param serverRequest the server request
     * @return the server resposne
     */
    public Mono<ServerResponse> postTick(final ServerRequest serverRequest) {
        return serverRequest
            .bodyToMono(InstrumentTickDTO.class)
            .flatMap(instrumentTickDTO -> ticksProcessor.postData(instrumentTickDTO))
            .flatMap(response -> Optional
                .of(response)
                .filter(Boolean::booleanValue)
                .map(val -> created(URI.create("/")).build())
                .orElse(noContent().build()));
    }

    /**
     * Get Statistics for all ticks
     *
     * @param serverRequest the server request
     * @return the server resposne
     */
    public Mono<ServerResponse> getStatistics(final ServerRequest serverRequest) {
        return statisticsProcessor
            .calculateStatistics()
            .flatMap(response -> ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(response))
            .switchIfEmpty(noContent().build());
    }

    /**
     * Get Statistics for specific instrument
     *
     * @param serverRequest the server request
     * @return the server resposne
     */
    public Mono<ServerResponse> getStatisticsByInstrument(final ServerRequest serverRequest) {
        final String instrument = serverRequest.pathVariable("instrumentId");
        return statisticsProcessor
            .calculateStatisticsByInstrumentId(instrument)
            .flatMap(response -> ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(response))
            .switchIfEmpty(noContent().build());
    }

}
