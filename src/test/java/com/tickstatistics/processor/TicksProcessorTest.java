package com.tickstatistics.processor;

import static com.tickstatistics.testUtils.TestDataUtils.INSTRUMENT_MAP;
import static com.tickstatistics.testUtils.TestDataUtils.TICK_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

import com.tickstatistics.function.InstrumentFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class TicksProcessorTest {
    private TicksProcessor ticksProcessor;
    private InstrumentFunctions instrumentFunctions;


    @BeforeEach
    void setUp() {
        instrumentFunctions = spy(new InstrumentFunctions());
        ticksProcessor = new TicksProcessor(INSTRUMENT_MAP.get(), instrumentFunctions);

    }
    @Test
    void postData() {
        StepVerifier
            .create(ticksProcessor.postData(TICK_REQUEST
                .get()
                .block()))
            .consumeNextWith(response -> assertThat(response, is(Boolean.TRUE)))
            .expectComplete()
            .verify();
    }

}