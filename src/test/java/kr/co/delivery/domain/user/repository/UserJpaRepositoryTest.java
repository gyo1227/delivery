package kr.co.delivery.domain.user.repository;

import kr.co.delivery.config.ContainersTestMySQLConfig;
import kr.co.delivery.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = "/sql/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserJpaRepositoryTest extends ContainersTestMySQLConfig {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @DisplayName("이메일로 유저 조회")
    @Nested
    class findUserByEmail {

        @DisplayName("[1] - 해당하는 이메일을 가진 entity가 없을 경우, empty를 반환한다")
        @Test
        void test1() {
            // given
            String email = "nonExistUser@email.com";

            // when
            Optional<User> result = userJpaRepository.findByEmail(email);

            // then
            assertTrue(result.isEmpty());
        }

        @DisplayName("[2] - 해당하는 이메일을 가진 entity가 있을 경우, entity를 반환한다")
        @Test
        void test2() {
            // given
            String email = "existUser@email.com";

            // when
            Optional<User> result = userJpaRepository.findByEmail(email);

            // then
            assertTrue(result.isPresent());
            final User entity = result.get();
            assertNotNull(entity.getId());
            assertEquals(email, entity.getEmail());
        }

    }

    @DisplayName("전화번호로 유저 조회")
    @Nested
    class findUserByPhone {

        @DisplayName("[1] - 해당하는 전화번호를 가진 entity가 없을 경우, empty를 반환한다")
        @Test
        void test1() {
            // given
            String phone = "01012345678";

            // when
            Optional<User> result = userJpaRepository.findByPhone(phone);

            // then
            assertTrue(result.isEmpty());
        }

        @DisplayName("[2] - 해당하는 전화번호를 가진 entity가 있을 경우, entity를 반환한다")
        @Test
        void test2() {
            // given
            String phone = "01012341234";

            // when
            Optional<User> result = userJpaRepository.findByPhone(phone);

            // then
            assertTrue(result.isPresent());
            final User entity = result.get();
            assertNotNull(entity.getId());
            assertEquals(phone, entity.getPhone());
        }

    }

}