package com.demo.config;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

import io.lettuce.core.RedisCommandTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisCacheErrorHandler implements CacheErrorHandler {
	private static final Logger log = LoggerFactory.getLogger(RedisCacheErrorHandler.class);

	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		handleException(exception);
		log.info("Error while fetching from cache " + cache.getName() + " : " + exception.getMessage());
	}

	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
		handleException(exception);
		log.info("Error while putting into cache " + cache.getName() + " : " + exception.getMessage());
	}

	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
		handleException(exception);
		log.info("Error while evicting cache " + cache.getName() + " : " + exception.getMessage());
	}

	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		handleException(exception);
		log.info("Error while cleaning cache " + cache.getName() + " : " + exception.getMessage());
	}

	// when exception is thrown, data will be returned from the DB
	private void handleException(RuntimeException exception) {
		if (exception instanceof RedisCommandTimeoutException)
			return;
	}
}
