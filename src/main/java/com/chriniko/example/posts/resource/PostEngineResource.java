package com.chriniko.example.posts.resource;

import com.airhacks.interceptor.monitoring.boundary.PerformanceSensor;
import com.airhacks.porcupine.execution.boundary.Dedicated;
import com.chriniko.example.posts.boundary.PostEngine;
import com.chriniko.example.posts.entity.Post;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Path("posts")

@Interceptors(PerformanceSensor.class)

public class PostEngineResource {

    @Inject
    PostEngine postEngine;

    @Resource(name = "concurrent/__defaultManagedExecutorService")
    ManagedExecutorService managedExecutorService;

    @Inject
    @Dedicated(value = "postsPoolPipeline")
    ExecutorService postsPool;

    // Note: in order to hit it: http://localhost:8080/posts-engine/resources/posts/first-way
    @GET
    @Path("first-way")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray all() {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        postEngine.findAll()
                .forEach(post -> arrayBuilder.add(
                        Json.createObjectBuilder()
                                .add("id", post.getId())
                                .add("title", post.getTitle())
                                .add("text", post.getText())
                                .add("created", post.getCreated().toString())
                                .build()
                        )
                );

        return arrayBuilder.build();
    }

    // Note: in order to hit it: http://localhost:8080/posts-engine/resources/posts/second-way
    @GET
    @Path("second-way")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> all2() {
        return postEngine.findAll();
    }

    // Note: in order to hit it: http://localhost:8080/posts-engine/resources/posts/second-way-xml
    @GET
    @Path("second-way-xml")
    @Produces(MediaType.APPLICATION_XML)
    public List<Post> all2Xml() {
        return postEngine.findAll();
    }

    // Note: in order to hit it: http://localhost:8080/posts-engine/resources/posts/third-way
    @GET
    @Path("third-way")
    @Produces(MediaType.APPLICATION_JSON)
    public void all3(@Suspended AsyncResponse asyncResponse) {

        List<Post> posts = postEngine.findAll();
        asyncResponse.resume(posts);
    }

    // Note: in order to hit it: http://localhost:8080/posts-engine/resources/posts/fourth-way
    @GET
    @Path("fourth-way")
    @Produces(MediaType.APPLICATION_JSON)
    public void all4(@Suspended AsyncResponse asyncResponse) {

        asyncResponse.setTimeout(1, TimeUnit.SECONDS);

        Consumer<Object> consumer = asyncResponse::resume;
        Consumer<Throwable> errorConsumer = asyncResponse::resume;
        Supplier<Object> supplier = () -> {

            // Note: uncomment in order to see the usage of OverloadListener.
            /*
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }*/
            return postEngine.findAll();
        };

        CompletableFuture
                .supplyAsync(supplier, postsPool/*managedExecutorService*/)
                .whenComplete((result, error) -> {
                    if (error != null) {
                        errorConsumer.accept(error);
                    } else {
                        consumer.accept(result);
                    }
                });
    }
}
