package com.tickstatistics.processor;

import static com.tickstatistics.testUtils.TestDataUtils.INSTRUMENT_MAP;
import static com.tickstatistics.testUtils.TestDataUtils.TICK_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;

import com.tickstatistics.function.InstrumentFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class StatisticsProcessorTest {
    private StatisticsProcessor statisticsProcessor;
    private InstrumentFunctions instrumentFunctions;

    @BeforeEach
    void setUp() {
        instrumentFunctions = spy(new InstrumentFunctions());
        statisticsProcessor = new StatisticsProcessor(INSTRUMENT_MAP.get(), instrumentFunctions);

    }



    @Test
    void calculateStatistics() {
        StepVerifier
            .create(statisticsProcessor.calculateStatistics())
            .consumeNextWith(response -> assertNotNull(response))
            .expectComplete()
            .verify();
    }

    @Test
    void calculateStatisticsByInstrumentId() {
        StepVerifier
            .create(statisticsProcessor.calculateStatisticsByInstrumentId("122"))
            .consumeNextWith(response -> assertNotNull(response))
            .expectComplete()
            .verify();
    }
}