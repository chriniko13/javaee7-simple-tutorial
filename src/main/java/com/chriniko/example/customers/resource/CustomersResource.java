package com.chriniko.example.customers.resource;


import com.chriniko.example.customers.boundary.CustomersEngine;
import com.chriniko.example.customers.domain.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

@ApplicationScoped
@Path("customers")
public class CustomersResource {

    @Inject
    CustomersEngine customersEngine;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("info")
    public JsonObject getInfo() {
        return Json.createObjectBuilder()
                .add("_self", uriInfo.getBaseUri().toString())
                .build();
    }

    @GET
    @Path("{id}")
    public Customer get(@PathParam("id") String id) {
        return customersEngine.get(id);
    }

    @GET
    public Collection<Customer> getAll() {
        return customersEngine.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@NotNull @Valid final Customer customer) {
        customersEngine.create(customer);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        long idL = Long.parseLong(id);
        customersEngine.delete(idL);
        return Response.ok().build();
    }

    @PATCH
    public Response update(@NotNull @Valid final Customer customer) {
        customersEngine.patch(customer);
        return Response.ok().build();
    }

}
