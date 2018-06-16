package com.chriniko.example.config;

import com.airhacks.porcupine.configuration.control.ExecutorConfigurator;
import com.airhacks.porcupine.execution.control.ExecutorConfiguration;

import javax.enterprise.inject.Specializes;

@Specializes
public class CustomExecutorConfigurator extends ExecutorConfigurator {

    @Override
    public ExecutorConfiguration forPipeline(String name) {

        if ("postsPoolPipeline".equals(name)) {

            return new ExecutorConfiguration.Builder()
                    .corePoolSize(42)
                    .maxPoolSize(150)
                    .queueCapacity(500)
                    .keepAliveTime(15)
                    .discardPolicy()
                    .build();

            // Note: uncomment in order to see the usage of OverloadListener.
            /*return new ExecutorConfiguration.Builder()
                    .corePoolSize(1)
                    .maxPoolSize(1)
                    .queueCapacity(1)
                    .keepAliveTime(1)
                    .build();*/

        } else {
            return ExecutorConfiguration.defaultConfiguration();
        }
    }
}
