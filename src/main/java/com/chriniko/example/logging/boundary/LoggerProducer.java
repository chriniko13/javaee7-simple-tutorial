package com.chriniko.example.logging.boundary;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class LoggerProducer {

    @Produces
    @InfoLevel
    public Consumer<String> expose(InjectionPoint ip) {
        String clazzName = ip.getMember().getDeclaringClass().getName();
        return message -> Logger.getLogger(clazzName).info(message);
    }


}
