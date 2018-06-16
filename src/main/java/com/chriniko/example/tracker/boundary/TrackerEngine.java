package com.chriniko.example.tracker.boundary;

import com.chriniko.example.logging.boundary.InfoLevel;
import com.chriniko.example.tracker.control.TriggeredMethod;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@ApplicationScoped
public class TrackerEngine {

    private List<TriggeredMethod> events;

    @Inject
    @InfoLevel
    Consumer<String> LOG;

    @PostConstruct
    public void init() {
        events = Collections.synchronizedList(new LinkedList<>());
    }

    /*
        Thread which fires the event, and the thread which observes/listens for the event
        are the same.

        Logs:

        [2018-06-16T14:39:57.911+0300] [Payara 5.182] [INFO] [] [] [tid: _ThreadID=36 _ThreadName=http-thread-pool::http-listener-1(1)] [timeMillis: 1529149197911] [levelValue: 800] [[
            PostsViewBean#fetchAllPosts -- Thread = http-thread-pool::http-listener-1(1)]]

        [2018-06-16T14:39:57.912+0300] [Payara 5.182] [INFO] [] [com.chriniko.example.tracker.boundary.TrackerEngine] [tid: _ThreadID=36 _ThreadName=http-thread-pool::http-listener-1(1)] [timeMillis: 1529149197912] [levelValue: 800] [[
            SYNC::Thread = http-thread-pool::http-listener-1(1) -- event = TriggeredMethod(className=PostsViewBean, methodName=fetchAllPosts, methodArgNames=[])]]

     */
    public void onUITriggeredMethod(
            @Observes(
                    notifyObserver = Reception.ALWAYS,
                    during = TransactionPhase.AFTER_SUCCESS
            ) TriggeredMethod event) {

        LOG.accept("SYNC::Thread = "
                + Thread.currentThread().getName()
                + " -- event = " + event);

        events.add(event);
    }


    /*
        Thread which fires the event, and the thread which observes/listens for the event
        are NOT the same.

        Logs:

        [2018-06-16T14:39:57.911+0300] [Payara 5.182] [INFO] [] [] [tid: _ThreadID=36 _ThreadName=http-thread-pool::http-listener-1(1)] [timeMillis: 1529149197911] [levelValue: 800] [[
            PostsViewBean#fetchAllPosts -- Thread = http-thread-pool::http-listener-1(1)]]

        [2018-06-16T14:39:57.912+0300] [Payara 5.182] [INFO] [] [com.chriniko.example.tracker.boundary.TrackerEngine] [tid: _ThreadID=36 _ThreadName=http-thread-pool::http-listener-1(1)] [timeMillis: 1529149197912] [levelValue: 800] [[
            SYNC::Thread = http-thread-pool::http-listener-1(1) -- event = TriggeredMethod(className=PostsViewBean, methodName=fetchAllPosts, methodArgNames=[])]]

        [2018-06-16T14:39:57.913+0300] [Payara 5.182] [INFO] [] [com.chriniko.example.tracker.boundary.TrackerEngine] [tid: _ThreadID=203 _ThreadName=weld-worker-2] [timeMillis: 1529149197913] [levelValue: 800] [[
            ASYNC::Thread = weld-worker-2 -- event = TriggeredMethod(className=PostsViewBean, methodName=fetchAllPosts, methodArgNames=[])]]

        [2018-06-16T14:39:57.914+0300] [Payara 5.182] [INFO] [] [] [tid: _ThreadID=203 _ThreadName=weld-worker-2] [timeMillis: 1529149197914] [levelValue: 800] [[
            PostsViewBean#fetchAllPosts[COMPLETED-SUCCESS] -- Thread = weld-worker-2]]

     */
    public void onUITriggeredMethodAsync(
            @ObservesAsync(
                    notifyObserver = Reception.ALWAYS
            ) TriggeredMethod event) {

        LOG.accept("ASYNC::Thread = "
                + Thread.currentThread().getName()
                + " -- event = " + event);

        events.add(event);

    }


}
