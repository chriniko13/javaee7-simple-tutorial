package com.chriniko.example.tracker.boundary;

import com.chriniko.example.logging.boundary.LoggerProducer;
import com.chriniko.example.tracker.control.TriggeredMethod;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class TrackerEngineTest {

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClasses(TrackerEngine.class, TriggeredMethod.class, LoggerProducer.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource("arquillian.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(javaArchive.toString(true));

        return javaArchive;
    }

    @Inject
    TrackerEngine trackerEngine;

    @Inject
    Event<TriggeredMethod> triggeredMethodEvent;

    @Test
    public void init() {
        List<TriggeredMethod> events = trackerEngine.getEvents();
        Assert.assertNotNull(events);
    }

    @Test
    public void onUITriggeredMethod() {

        trackerEngine.getEvents().clear();

        triggeredMethodEvent.fire(new TriggeredMethod("clazzName", "methodName", Collections.emptyList()));

        List<TriggeredMethod> events = trackerEngine.getEvents();

        assertEquals(1, events.size());
        assertEquals("clazzName", events.get(0).getClassName());

    }

    @Test
    public void onUITriggeredMethodAsync() {

        trackerEngine.getEvents().clear();

        CompletionStage<TriggeredMethod> result = triggeredMethodEvent.fireAsync(
                new TriggeredMethod("clazzName", "methodName", Collections.emptyList())
        );

        result.toCompletableFuture().join();

        List<TriggeredMethod> events = trackerEngine.getEvents();

        assertEquals(1, events.size());
        assertEquals("clazzName", events.get(0).getClassName());

    }
}
