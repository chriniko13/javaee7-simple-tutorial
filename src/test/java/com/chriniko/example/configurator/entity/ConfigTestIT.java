package com.chriniko.example.configurator.entity;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.UUID;

import static org.junit.Assert.*;

public class ConfigTestIT {

    private EntityManager em;
    private EntityTransaction tx;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("it");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @Test
    public void crud() {

        tx.begin();
        em.merge(new Config(UUID.randomUUID().toString(), "label", "value"));
        tx.commit();

        tx.begin();
        long records = (long) em.createQuery("select count(c) from Config c").getSingleResult();
        tx.commit();
        assertEquals(1, records);
    }
}