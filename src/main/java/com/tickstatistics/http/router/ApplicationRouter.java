package com.tickstatistics.http.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import com.tickstatistics.http.handler.InstrumentTicksHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class ApplicationRouter {

    @Bean
    public RouterFunction<ServerResponse> route(InstrumentTicksHandler instrumentTicksHandler) {
        return RouterFunctions
            .route(GET("/statistics/{instrumentId}").and(accept(APPLICATION_JSON)), instrumentTicksHandler::getStatisticsByInstrument)
            .andRoute(GET("/statistics").and(accept(APPLICATION_JSON)), instrumentTicksHandler::getStatistics)
            .andRoute(POST("/ticks").and(contentType(APPLICATION_JSON)), instrumentTicksHandler::postTick);
    }
}
