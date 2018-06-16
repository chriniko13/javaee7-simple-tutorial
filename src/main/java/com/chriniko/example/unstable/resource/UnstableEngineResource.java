package com.chriniko.example.unstable.resource;


import com.airhacks.breakr.Breakr;
import com.airhacks.breakr.CloseCircuit;
import com.airhacks.breakr.IgnoreCallsWhen;

import javax.inject.Singleton;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.ThreadLocalRandom;

@Interceptors(Breakr.class)

@Path("unstable")

@Singleton
public class UnstableEngineResource {

    @GET
    @Path("method1")
    @IgnoreCallsWhen(failures = 2)
    public String unstable() {

        if (ThreadLocalRandom.current().nextDouble(1.1D) <= 0.7D) {
            throw new IllegalStateException();
        }

        return "some-expensive-string";
    }

    @GET
    @Path("clear")
    @CloseCircuit
    public String clear() {
        return "OK";
    }
}
