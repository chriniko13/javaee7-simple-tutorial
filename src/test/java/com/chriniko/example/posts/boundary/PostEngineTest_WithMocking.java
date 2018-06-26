package com.chriniko.example.posts.boundary;

import com.chriniko.example.posts.control.PostCreationTime;
import com.chriniko.example.posts.control.PostValidator;
import com.chriniko.example.posts.entity.Post;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PostEngineTest_WithMocking {

    private PostEngine postEngine;

    @Mock
    private EntityManager mockedEntityManager;

    @Mock
    private PostValidator mockedPostValidator;

    @Mock
    private PostCreationTime mockedPostCreationTime;

    @Mock
    private TypedQuery<Object> mockedTypedQuery;

    private Consumer<String> log = s -> {
    };

    private String threshold = "0.99";

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        postEngine = new PostEngine();
        postEngine.entityManager = mockedEntityManager;
        postEngine.postValidator = mockedPostValidator;
        postEngine.postCreationTime = mockedPostCreationTime;
        postEngine.LOG = log;
        postEngine.threshold = threshold;
    }

    @Test
    public void store() {

        //given
        Mockito.when(mockedPostCreationTime.getCreated()).then(invocation -> Instant.now());

        //when
        postEngine.store("title", "text");

        //then
        Mockito.verify(mockedPostValidator).validate(Mockito.any());
        Mockito.verify(mockedEntityManager).persist(Mockito.any());

    }

    @Test
    public void findAll() {

        //given
        Mockito.when(mockedEntityManager.createQuery(Mockito.anyString(), Mockito.any()))
                .thenReturn(mockedTypedQuery);

        Mockito.when(mockedTypedQuery.getResultList())
                .thenReturn(Collections.singletonList(new Post("id", "title", "text", Instant.now())));

        //when
        List<Post> result = postEngine.findAll();


        //then
        assertThat(result.size(), Is.is(1));

    }

    @Test
    public void delete() {

        //when
        postEngine.delete("some-id");

        //then
        Mockito.verify(mockedEntityManager).find(Mockito.any(), Mockito.anyString());
        Mockito.verify(mockedEntityManager).remove(Mockito.anyObject());
    }
}
