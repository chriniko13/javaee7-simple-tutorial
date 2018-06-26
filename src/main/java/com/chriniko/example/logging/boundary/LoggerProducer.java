package com.chriniko.example.logging.boundary;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class LoggerProducer {

    @Produces
    @InfoLevel
    @Dependent
    public Consumer<String> exposeInfoLogger(InjectionPoint ip) {
        String clazzName = getClassName(ip);
        return message -> Logger.getLogger(clazzName).info(message);
    }

    @Produces
    @WarnLevel
    public Consumer<String> exposeWarnLogger(InjectionPoint ip) {
        String clazzName = getClassName(ip);
        return message -> Logger.getLogger(clazzName).warning(message);
    }

    private String getClassName(InjectionPoint ip) {
        return ip.getMember().getDeclaringClass().getName();
    }

}
