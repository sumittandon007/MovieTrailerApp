package com.movietrailer.core.services;

import com.movietrailer.core.services.impl.EHCacheServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.ehcache.Cache;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.movietrailer.core.services.impl.EHCacheServiceImpl.DEFAULT_CACHE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class, AemContextExtension.class})
class EHCacheServiceTest {

    @Test
    @DisplayName("Test - EHCache Get Cache")
    void testGetCache(AemContext context) {
        EHCacheService ehcacheService = context.registerInjectActivateService(new EHCacheServiceImpl());
        Cache<String, Object> cache = ehcacheService.getCache(DEFAULT_CACHE);
        cache.put("Field A", "value A");

        assertEquals("value A", cache.get("Field A"));
    }
}
