package com.tickstatistics.function;

import com.tickstatistics.domain.InstrumentTickDTO;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;

/**
 * Instrument Statistics Fuctions
 */
@Component
public class InstrumentFunctions {

    /**
     * Check if tick is in with 60 sec limit
     */
    public final Predicate<Long> tickWithInSixtySec = timeStamp -> {
        long timeInMillis = System.currentTimeMillis();
        return timeStamp < timeInMillis - (600 * 1000L);
    };

    /**
     * Calculate average price of given instrument list
     */
    public final Function<List<InstrumentTickDTO>, Double> calculateAverage = list -> list
        .stream()
        .mapToDouble(InstrumentTickDTO::getPrice)
        .average()
        .orElse(0.0);


    /**
     * Calculate maximum price of given instrument list
     */
    public final Function<List<InstrumentTickDTO>, Double> calculateMax = list -> list
        .stream()
        .mapToDouble(InstrumentTickDTO::getPrice)
        .max()
        .orElse(0.0);


    /**
     * Calculate min price of given instrument list
     */
    public final Function<List<InstrumentTickDTO>, Double> calculateMin = list -> list
        .stream()
        .mapToDouble(InstrumentTickDTO::getPrice)
        .min()
        .orElse(0.0);
}
