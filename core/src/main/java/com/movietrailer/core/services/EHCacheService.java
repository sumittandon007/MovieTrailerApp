package com.movietrailer.core.services;

import org.ehcache.Cache;

public interface EHCacheService {

    /**
     * Method to return existing cache if available.
     *
     * @param cacheName
     *
     * @return
     */
    Cache getCache(final String cacheName);

}
