package com.chriniko.example.language.boundary;

import com.chriniko.example.language.control.Language;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class LanguageEngineTest {

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addPackage(LanguageEngine.class.getPackage())
                .addPackage(Language.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource("arquillian.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(javaArchive.toString(true));

        return javaArchive;
    }

    @Inject
    @Any
    Instance<LanguageEngine> languageEngines;

    @Test
    public void welcomeMessage() {

        Integer totalEngines = languageEngines.stream().map(languageEngine -> 1).reduce(0, Integer::sum);

        assertEquals(2, (int) totalEngines);
    }
}
