package com.tickstatistics.http.handler;

import static com.tickstatistics.testUtils.TestDataUtils.STATISTICS_RESPONSE;
import static com.tickstatistics.testUtils.TestDataUtils.TICK_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tickstatistics.domain.InstrumentTickDTO;
import com.tickstatistics.processor.StatisticsProcessor;
import com.tickstatistics.processor.TicksProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class InstrumentTicksHandlerTest {

    private InstrumentTicksHandler instrumentTicksHandler;
    private StatisticsProcessor    statisticsProcessor;
    private TicksProcessor         ticksProcessor;
    private ServerRequest          serverRequest;

    @BeforeEach
    void setUp() {
        statisticsProcessor = mock(StatisticsProcessor.class);
        ticksProcessor = mock(TicksProcessor.class);
        instrumentTicksHandler = new InstrumentTicksHandler(statisticsProcessor, ticksProcessor);
        serverRequest = mock(ServerRequest.class);
        when(serverRequest.bodyToMono(InstrumentTickDTO.class)).thenReturn(TICK_REQUEST.get());
        when(serverRequest.pathVariable(anyString())).thenReturn("instruentId");

    }

    @Test
    void postTick_successWith201StatusCode() {

        when(ticksProcessor.postData(any())).thenReturn(Mono.just(true));

        StepVerifier
            .create(instrumentTicksHandler.postTick(serverRequest))
            .consumeNextWith(serverResponse -> assertThat(serverResponse.statusCode(), is(HttpStatus.CREATED)))
            .expectComplete()
            .verify();
    }

    @Test
    void postTick_failureWith204StatusCode() {
        when(ticksProcessor.postData(any())).thenReturn(Mono.just(false));

        StepVerifier
            .create(instrumentTicksHandler.postTick(serverRequest))
            .consumeNextWith(serverResponse -> assertThat(serverResponse.statusCode(), is(HttpStatus.NO_CONTENT)))
            .expectComplete()
            .verify();
    }

    @Test
    void getStatistics_successWith200StatusCode() {

        when(statisticsProcessor.calculateStatistics()).thenReturn(STATISTICS_RESPONSE.get());

        StepVerifier
            .create(instrumentTicksHandler.getStatistics(serverRequest))
            .consumeNextWith(serverResponse -> assertThat(serverResponse.statusCode(), is(HttpStatus.OK)))
            .expectComplete()
            .verify();
    }

    @Test
    void getStatistics_failureWith204StatusCode() {
        when(statisticsProcessor.calculateStatistics()).thenReturn(Mono.empty());
        StepVerifier
            .create(instrumentTicksHandler.getStatistics(serverRequest))
            .consumeNextWith(serverResponse -> assertThat(serverResponse.statusCode(), is(HttpStatus.NO_CONTENT)))
            .expectComplete()
            .verify();
    }

    @Test
    void getStatisticsByInstrument_successWith200StatusCode() {

        when(statisticsProcessor.calculateStatisticsByInstrumentId(anyString())).thenReturn(STATISTICS_RESPONSE.get());

        StepVerifier
            .create(instrumentTicksHandler.getStatisticsByInstrument(serverRequest))
            .consumeNextWith(serverResponse -> assertThat(serverResponse.statusCode(), is(HttpStatus.OK)))
            .expectComplete()
            .verify();
    }

    @Test
    void getStatisticsByInstrument_failureWith204StatusCode() {
        when(statisticsProcessor.calculateStatisticsByInstrumentId(anyString())).thenReturn(Mono.empty());
        StepVerifier
            .create(instrumentTicksHandler.getStatisticsByInstrument(serverRequest))
            .consumeNextWith(serverResponse -> assertThat(serverResponse.statusCode(), is(HttpStatus.NO_CONTENT)))
            .expectComplete()
            .verify();

    }
}