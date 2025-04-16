package com.syscho.mongo;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.mongodb.client.MongoClient;
import com.syscho.mongo.order.changestream.OrderChangeStreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

import static com.syscho.mongo.order.changestream.OrderChangeStreamListener.RESUME_TOKEN_MAP;

//For Resume token & distributed locking
@Configuration
public class HazelcastConfig {

    @Bean
    public HazelcastInstance hazelcastInstance(MongoClient mongoClient) {
        Config config = new Config();
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName(RESUME_TOKEN_MAP);
        mapConfig.setTimeToLiveSeconds(3600);  // TTL of 1 hour
        MapStoreConfig mapStoreConfig = new MapStoreConfig()
                .setEnabled(true)
                .setImplementation(new ResumeTokenMapStore(mongoClient));

        mapConfig.setMapStoreConfig(mapStoreConfig);
        config.getNetworkConfig().getRestApiConfig().setEnabled(true);
        config.addMapConfig(mapConfig);
        return Hazelcast.newHazelcastInstance(config);
    }

}
