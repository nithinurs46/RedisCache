package com.demo.config;

import java.time.Duration;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public JedisConnectionFactory getConnection() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName("localhost");
		configuration.setPort(6379);
		JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
				.connectTimeout(Duration.ofSeconds(10000)).readTimeout(Duration.ofSeconds(10000)).build();
		return new JedisConnectionFactory(configuration,clientConfig);

	}
	
	@Override
	public CacheErrorHandler errorHandler() {
		return new RedisCacheErrorHandler();
	}
	
}
