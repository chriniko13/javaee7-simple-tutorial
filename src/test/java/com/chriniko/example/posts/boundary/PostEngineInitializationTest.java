package com.chriniko.example.posts.boundary;

import com.chriniko.example.posts.entity.Post;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class PostEngineInitializationTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClasses(PostEngineInitialization.class, Post.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource("arquillian.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(javaArchive.toString(true));

        return javaArchive;
    }

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void init() {

        Long posts = entityManager.createQuery("select count(p) from Post p", Long.class).getSingleResult();

        assertEquals(10, (long) posts);
    }
}
