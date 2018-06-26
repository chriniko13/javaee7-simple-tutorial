package com.chriniko.example.infrastructure;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import javax.persistence.Entity;

public class VetoEntity implements Extension {

    void vetoEntity(@Observes @WithAnnotations(Entity.class) ProcessAnnotatedType<?> pat) {
        pat.veto();
    }
}
