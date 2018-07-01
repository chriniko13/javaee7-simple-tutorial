package com.chriniko.example.customers.control;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomerNotFoundMapper implements ExceptionMapper<CustomerNotFound> {

    @Override
    public Response toResponse(CustomerNotFound exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
