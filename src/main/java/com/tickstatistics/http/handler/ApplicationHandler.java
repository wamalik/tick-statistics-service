package com.tickstatistics.http.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.tickstatistics.domain.InstrumentTickDTO;
import com.tickstatistics.processor.InstrumentStatisticsProcessor;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ApplicationHandler {

    private final InstrumentStatisticsProcessor tickService;

    public Mono<ServerResponse> postTick(final ServerRequest serverRequest) {
        return serverRequest
            .bodyToMono(InstrumentTickDTO.class)
            .flatMap(instrumentTickDTO -> tickService.postData(instrumentTickDTO))
            .flatMap(response -> Optional
                .of(response)
                .filter(Boolean::booleanValue)
                .map(val -> created(URI.create("/")).build())
                .orElse(noContent().build()));
    }

    public Mono<ServerResponse> getStatistics(final ServerRequest serverRequest) {
        return tickService
            .calculateStatistics()
            .flatMap(response -> ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(response))
            .switchIfEmpty(noContent().build());
    }

    public Mono<ServerResponse> getStatisticsByInstrument(final ServerRequest serverRequest) {
        final String instrument = serverRequest.pathVariable("instrumentId");
        return tickService
            .calculateStatisticsByInstrumentId(instrument)
            .flatMap(response -> ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(response))
            .switchIfEmpty(noContent().build());
    }

}
