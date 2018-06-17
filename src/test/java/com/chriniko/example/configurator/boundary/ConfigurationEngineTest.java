package com.chriniko.example.configurator.boundary;

import com.chriniko.example.configurator.entity.Config;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ConfigurationEngineTest {

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClasses(ConfigurationEngine.class, Threshold.class, Config.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource("arquillian.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(javaArchive.toString(true));

        return javaArchive;
    }

    @Inject
    ConfigurationEngine configurationEngine;

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    UserTransaction utx;

    @Test
    public void getThreshold_fallback_case() {

        String threshold = configurationEngine.getThreshold();

        assertEquals("undefined", threshold);
    }

    @Test
    public void getThreshold_hasValue_case() throws SystemException,
            NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

        utx.begin();
        entityManager.joinTransaction();
        entityManager.persist(new Config(UUID.randomUUID().toString(), "threshold", "0.42"));
        utx.commit();

        String threshold = configurationEngine.getThreshold();

        assertEquals("0.42", threshold);
    }
}
