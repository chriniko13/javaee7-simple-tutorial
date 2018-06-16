package com.chriniko.example.configurator.boundary;


import com.chriniko.example.configurator.entity.Config;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;


@Singleton
public class ConfigurationEngine {

    @PersistenceContext
    EntityManager entityManager;

    @Produces
    @Threshold
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public String getThreshold() {

        TypedQuery<Config> q = entityManager.createQuery("select c from Config c where c.label = :l", Config.class);
        q.setParameter("l", "threshold");
        try {
            Config c = q.getSingleResult();
            return c.getValue();
        } catch (NoResultException error) {
            return "undefined";
        }
    }

    public void release(@Disposes @Threshold String threshold) {
        System.out.println("### disposing threshold configuration");
    }
}
