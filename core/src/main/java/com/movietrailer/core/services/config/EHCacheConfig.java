package com.movietrailer.core.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "EHCache Configuration", description = "EHCache Configs")
public @interface EHCacheConfig {

    //Default size of heap.
    int HEAP_SIZE = 50;

    //Max time to live in hrs
    int TTL_HR = 5;

    @AttributeDefinition(name = "HeapSize", description = "Size of heap cache. Should be always less than offheapsize.")
    int getHeapSize() default HEAP_SIZE;

    @AttributeDefinition(name = "TimeToLive", description = "Maximum time to live for the cache")
    int getTimeToLive() default TTL_HR;

}
