package kr.co.delivery.db.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class ContainersTestRedisConfig {

    private static final String REDIS_CONTAINER_NAME = "redis:7.2";

    @Container
    private static final GenericContainer<?> REDIS_CONTAINER;

    static {
        REDIS_CONTAINER =
                new GenericContainer<>(DockerImageName.parse(REDIS_CONTAINER_NAME))
                        .withExposedPorts(6379)
                        .withReuse(true);

        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void setRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }
}
