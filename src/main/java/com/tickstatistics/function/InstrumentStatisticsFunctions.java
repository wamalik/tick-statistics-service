package com.tickstatistics.function;

import com.tickstatistics.domain.InstrumentTickDTO;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;

@Component
public class InstrumentStatisticsFunctions {

    private static final Long SIXTY_SEC_IN_MILLIS = 600 * 1000L;

    public final Predicate<Long> tickWithInSixtySec = timeStamp -> {
        long timeInMillis = System.currentTimeMillis();
        return timeStamp < timeInMillis - SIXTY_SEC_IN_MILLIS;
    };

    public final Function<List<InstrumentTickDTO>, Double> calculateAverage = list -> list
        .stream()
        .mapToDouble(InstrumentTickDTO::getPrice)
        .average()
        .orElse(0.0);

    public final Function<List<InstrumentTickDTO>, Double> calculateMax = list -> list
        .stream()
        .mapToDouble(InstrumentTickDTO::getPrice)
        .max()
        .orElse(0.0);

    public final Function<List<InstrumentTickDTO>, Double> calculateMin = list -> list
        .stream()
        .mapToDouble(InstrumentTickDTO::getPrice)
        .min()
        .orElse(0.0);
}
