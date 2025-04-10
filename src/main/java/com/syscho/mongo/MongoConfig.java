package com.syscho.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {

    @Bean
    public DbRefResolver dbRefResolver(MongoDatabaseFactory mongoDatabaseFactory) {
        return new DefaultDbRefResolver(mongoDatabaseFactory);
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoMappingContext mongoMappingContext,
            MongoCustomConversions customConversions,
            DbRefResolver dbRefResolver
    ) {
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.setCustomConversions(customConversions);
        return converter;
    }
}
