# RedisCache
A spring boot application using with Redis Cache and Hibernate as JPA.

Redis cache server download location for windows - https://github.com/microsoftarchive/redis/releases/tag/win-3.2.100

1. Add starter - Spring-data-redis(Access+Driver)
2. Add Redis.client - Jedis
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.6.0</version>
</dependency>

3. Create a RedisConfig class - 
 - Create a method which returns JedisConnectionFactory instance, provide RedisDB hostname and port.
 
4. Add @EnableCaching annotation to main class 

Following annotation is used in service class - 

@Cacheable - Annotation indicating that the result of invoking a method (or all methodsin a class) can be cached. 
Each time an advised method is invoked, caching behavior will be applied,checking whether the method has been already invoked for the given arguments.A sensible default simply uses the method parameters to compute the key, buta SpEL expression can be provided via the key attribute, or a custom org.springframework.cache.interceptor.KeyGenerator implementation canreplace the default one

@Caching - Group annotation for multiple cache annotations (of different or the same type). This annotation may be used as a meta-annotation to create custom composed annotations with attribute overrides.

@CacheEvict - Annotation indicating that a method (or all methods on a class) triggers a cache evict operation. 
This annotation may be used as a meta-annotation to create custom composed annotations with attribute overrides.

@CachePut - Annotation indicating that a method (or all methods on a class) triggers a cache put operation. 
In contrast to the @Cacheable annotation, this annotation does not cause the advised method to be skipped. Rather, it always causes the method to be invoked and its result to be stored in the associated cache. Note that Java8's Optional return types are automatically handled and itscontent is stored in the cache if present. 
This annotation may be used as a meta-annotation to create custom composed annotations with attribute overrides.

If RedisCache is down, we can fetch the data from DB by adding appropriate error handler
In RedisConfig.java override the CacheErrorHandler and create an Handler class -

@Override
public CacheErrorHandler errorHandler() {
	return new RedisCacheErrorHandler();
}

