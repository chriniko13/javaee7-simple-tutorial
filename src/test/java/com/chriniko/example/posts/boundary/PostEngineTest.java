package com.chriniko.example.posts.boundary;

import com.chriniko.example.configurator.boundary.ConfigurationEngine;
import com.chriniko.example.configurator.entity.Config;
import com.chriniko.example.logging.boundary.LoggerProducer;
import com.chriniko.example.posts.control.PostValidator;
import com.chriniko.example.posts.entity.Post;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
public class PostEngineTest {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive webArchive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClasses(PostEngine.class,
                        PostValidator.class,
                        ConfigurationEngine.class,
                        LoggerProducer.class,
                        //PostEngineInitialization.class, //Note: if you uncomment this, you will have 100 posts.
                        Post.class,
                        Config.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource("arquillian.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");


        System.out.println(webArchive.toString(true));

        return webArchive;
    }

    @Inject
    PostEngine postEngine;

    @Test
    public void findAll_1() {

        List<Post> posts = postEngine.findAll();

        Assert.assertEquals(0, posts.size());
    }

    @Test
    @UsingDataSet("datasets/posts.yml")
    @ShouldMatchDataSet("datasets/expected-posts.yml")
    public void findAll_2() {

    }

    @Test
    public void save_1() {

        postEngine.store("some-title", "some-text");

        Assert.assertEquals(1, postEngine.findAll().size());
    }
}
