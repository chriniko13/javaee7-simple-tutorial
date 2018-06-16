package com.chriniko.example.statistics.resource;

import com.airhacks.porcupine.execution.entity.Statistics;

import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Stateless
@Path("statistics")
public class StatisticsResource {

    @Inject
    Instance<List<Statistics>> statistics;

    @GET
    public List<Statistics> all() {
        return statistics.get();
    }

}
