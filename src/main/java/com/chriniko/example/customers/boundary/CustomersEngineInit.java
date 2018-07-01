package com.chriniko.example.customers.boundary;


import com.chriniko.example.customers.domain.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class CustomersEngineInit {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        for (int i = 1; i <= 10; i++) {
            entityManager.persist(Customer.builder()
                    .firstname("firstname" + i)
                    .initials("initials" + i)
                    .surname("surname" + i)
                    .build());
        }
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
    }

}
