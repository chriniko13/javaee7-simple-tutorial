package com.chriniko.example.posts.control;

import com.chriniko.example.posts.entity.Post;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.Instant;
import java.util.UUID;

@RunWith(Arquillian.class)
public class PostValidatorTest {

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addClass(PostValidator.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    PostValidator postValidator;


    @Test
    public void validate_valid_case() {

        Post post = new Post(UUID.randomUUID().toString(), "title", "text", Instant.now());

        postValidator.validate(post);
    }

    @Test(expected = PostValidationException.class)
    public void validate_invalid_case_empty_title() {

        Post post = new Post(UUID.randomUUID().toString(), "", "text", Instant.now());

        postValidator.validate(post);
    }

    @Test(expected = PostValidationException.class)
    public void validate_invalid_case_no_title() {

        Post post = new Post(UUID.randomUUID().toString(), null, "text", Instant.now());

        postValidator.validate(post);
    }

    @Test(expected = PostValidationException.class)
    public void validate_invalid_case_empty_text() {

        Post post = new Post(UUID.randomUUID().toString(), "title", "", Instant.now());

        postValidator.validate(post);
    }

    @Test(expected = PostValidationException.class)
    public void validate_invalid_case_no_text() {

        Post post = new Post(UUID.randomUUID().toString(), "title", null, Instant.now());

        postValidator.validate(post);
    }
}
