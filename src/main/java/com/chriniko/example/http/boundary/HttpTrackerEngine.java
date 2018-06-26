package com.chriniko.example.http.boundary;

import com.chriniko.example.logging.boundary.WarnLevel;

import javax.enterprise.context.*;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.function.Consumer;

@ApplicationScoped
public class HttpTrackerEngine {

    @Inject
    @WarnLevel
    Consumer<String> LOG;


    public void onRequestBegin(@Observes @Initialized(RequestScoped.class) ServletRequest servletRequest) {
        LOG.accept("###INIT REQUEST###" + servletRequest.toString());
    }

    public void onRequestEnd(@Observes @Destroyed(RequestScoped.class) ServletRequest servletRequest) {
        LOG.accept("###DESTROYED REQUEST###" + servletRequest.toString());
    }


    public void onSessionBegin(@Observes @Initialized(SessionScoped.class) HttpSession httpSession) {
        LOG.accept("###INIT SESSION###" + httpSession.toString());
    }

    public void onSessionEnd(@Observes @Destroyed(SessionScoped.class) HttpSession httpSession) {
        LOG.accept("###DESTROYED SESSION###" + httpSession.toString());
    }


    public void onApplicationBegin(@Observes @Initialized(ApplicationScoped.class) ServletContext servletContext) {
        LOG.accept("###INIT APP###" + servletContext.toString());
    }

    public void onApplicationEnd(@Observes @Destroyed(ApplicationScoped.class) ServletContext servletContext) {
        LOG.accept("###DESTROYED APP###" + servletContext.toString());
    }
}
