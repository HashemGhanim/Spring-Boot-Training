package com.hashem.restdemo.cachingconfig;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


//@EnableCaching
//@Configuration
public class CachingConfig {

//    @Bean
//    public CaffeineCacheManager caffeineCacheManager(){
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(caffeine());
//        return cacheManager;
//    }
//
//    public Caffeine<Object , Object> caffeine(){
//        return Caffeine.newBuilder()
//                .initialCapacity(100)
//                .maximumSize(500)
//                .expireAfterWrite(30 , TimeUnit.MINUTES)
//                .recordStats();
//    }
}
