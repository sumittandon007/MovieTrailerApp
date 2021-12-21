package com.movietrailer.core.services.impl;

import com.movietrailer.core.services.EHCacheService;
import com.movietrailer.core.services.config.EHCacheConfig;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;

import java.time.Duration;

@Component(immediate = true, service = EHCacheService.class)
@Designate(ocd = EHCacheConfig.class)
public class EHCacheServiceImpl implements EHCacheService {

    private int heapSize = 0;

    private int timeToLive = 0;

    public static final String DEFAULT_CACHE = "defaultCache";

    private CacheManager cacheManager;

    @Activate
    protected void activate(final EHCacheConfig ehCacheConfig) {
        this.heapSize = ehCacheConfig.getHeapSize();
        this.timeToLive = ehCacheConfig.getTimeToLive();
        final CacheConfiguration<String, Object> cacheConfiguration = getCacheConfiguration();
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache(DEFAULT_CACHE, cacheConfiguration).build(true);
    }

    private CacheConfiguration<String, Object> getCacheConfiguration() {
        return CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(this.heapSize, MemoryUnit.MB))
                                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(timeToLive)))
                                        .build();
    }

    @Override
    public Cache<String, Object> getCache(final String cacheName) {
        return cacheManager.getCache(cacheName, String.class, Object.class);
    }

    @Deactivate
    protected void deactivate() {
        if (cacheManager != null) {
            cacheManager.close();
        }
    }

}
