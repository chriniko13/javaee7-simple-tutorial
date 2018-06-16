package com.chriniko.example.posts.crosscut;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logged
@Interceptor
public class LoggedInterceptor {

    @AroundInvoke
    public Object log(InvocationContext ctx) throws Exception {

        System.out.println("LoggedInterceptor#log: before ---> "
                + ctx.getMethod().getDeclaringClass().getSimpleName()
                + "#"
                + ctx.getMethod().getName());

        Object proceed = ctx.proceed();

        System.out.println("LoggedInterceptor#log: after ---> "
                + ctx.getMethod().getDeclaringClass().getSimpleName()
                + "#"
                + ctx.getMethod().getName());

        return proceed;
    }

}
