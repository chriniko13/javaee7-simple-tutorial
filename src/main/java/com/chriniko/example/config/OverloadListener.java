package com.chriniko.example.config;

import com.airhacks.porcupine.execution.entity.Rejection;

import javax.enterprise.event.Observes;

public class OverloadListener {

    public void onOverload(@Observes Rejection rejection) {
        System.out.println("rejection [statistics = " + rejection.getStatistics() + ", taskClass = " + rejection.getTaskClass() + "]");
    }
}
