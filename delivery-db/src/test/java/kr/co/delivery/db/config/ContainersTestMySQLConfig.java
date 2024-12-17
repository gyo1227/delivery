package kr.co.delivery.db.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class ContainersTestMySQLConfig {

    private static final String MYSQL_CONTAINER_NAME = "mysql:8.0.40";

    @Container
    private static final MySQLContainer<?> MYSQL_CONTAINER;

    static {
        MYSQL_CONTAINER =
                new MySQLContainer<>(DockerImageName.parse(MYSQL_CONTAINER_NAME))
                        .withDatabaseName("delivery")
                        .withUsername("root")
                        .withPassword("1234")
                        .withReuse(true);

        MYSQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void setRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
    }
}
