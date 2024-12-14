package kr.co.delivery.db.domains.user.repository;

import kr.co.delivery.db.config.jpa.JpaConfig;
import kr.co.delivery.db.domains.user.domain.Provider;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("db-h2-test")
@EnableAutoConfiguration
@ContextConfiguration(classes = {JpaConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DataJpaTest
class UserJpaRepositoryH2Test {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Nested
    class FindByEmailAndProvider {

        final String newEmail = "newUser@email.com";
        final String existEmail = "existUser@email.com";

        @DisplayName("email과 provider를 갖는 entity가 없다면, empty를 반환한다")
        @Test
        void test1() {
            // when
            final Optional<UserEntity> result = userJpaRepository.findByEmailAndProvider(newEmail, Provider.LOCAL);

            // then
            assertTrue(result.isEmpty());
        }

        @DisplayName("email과 provider를 갖는 entity가 있다면, entity를 반환한다")
        @Test
        void test2() {
            // when
            final Optional<UserEntity> result = userJpaRepository.findByEmailAndProvider(existEmail, Provider.LOCAL);

            // then
            assertTrue(result.isPresent());
            final UserEntity entity = result.get();
            assertNotNull(entity.getId());
            assertEquals(existEmail, entity.getEmail());
        }
    }
}