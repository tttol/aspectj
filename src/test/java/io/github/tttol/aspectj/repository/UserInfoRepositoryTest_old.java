package io.github.tttol.aspectj.repository;

import com.google.common.hash.Hashing;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import io.github.tttol.aspectj.mbgenerate.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.truncate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserInfoRepositoryTest_old {
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${spring.datasource.username}")
    private String datasourceUserName;
    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    private static final String ID_1 = Hashing.sha256().hashString("1", StandardCharsets.UTF_8).toString();
    private static final String ID_2 = Hashing.sha256().hashString("2", StandardCharsets.UTF_8).toString();
    private final List<UserInfo> userInfoList = new ArrayList<>();

    private static final DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    void init() {
        final var insertOperation = Operations.insertInto("user_info")
                .columns("id", "name", "email")
                .values(ID_1, "Tom", "tom@example.com")
                .values(ID_2, "John", "john@example.com")
                .build();
        final var dbSetup = new DbSetup(new DriverManagerDestination(datasourceUrl, datasourceUserName, datasourcePassword),
                sequenceOf(truncate("user_info"), insertOperation));
        dbSetupTracker.launchIfNecessary(dbSetup);

        userInfoList.addAll(userInfoRepository.selectAll());
    }

    @Nested
    class SelectByPrimaryKey {
        @Test
        @DisplayName("primary keyをもとにユーザー情報を取得できること")
        void selectByPrimaryKeyTest() {
            dbSetupTracker.skipNextLaunch();
            // dbsetupで登録済みのためget()する
            final var expected = userInfoList.stream().filter(e -> StringUtils.equals(e.getId(), ID_1)).findFirst().get();
            final var actual = userInfoRepository.selectByPrimaryKey(ID_1);
            assertThat(actual).isPresent().get().usingRecursiveComparison().isEqualTo(expected);
            ;
        }
    }

    @Nested
    class SelectAll {
        @Test
        @DisplayName("全件取得ができること")
        void selectAllTest() {
            dbSetupTracker.skipNextLaunch();
            final var user1 = new UserInfo();
            user1.setId(ID_1);
            user1.setName("Tom");
            user1.setEmail("tom@example.com");
            final var user2 = new UserInfo();
            user2.setId(ID_2);
            user2.setName("John");
            user2.setEmail("john@example.com");
            final var expected = List.of(user1, user2);
            final var actual = userInfoRepository.selectAll();

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }
    }

    @Nested
    class Insert {
        @Test
        @DisplayName("user_infoへのINSERTができること")
        void insertTest() {
            final var target = new UserInfo();
            final var id = Hashing.sha256().hashString("insert test", StandardCharsets.UTF_8).toString();
            target.setId(id);
            target.setName("Taro");
            target.setEmail("taro@example.com");
            userInfoRepository.insert(target);
            final var actual = userInfoRepository.selectByPrimaryKey(id);

            assertThat(actual).isPresent().get().usingRecursiveComparison().isEqualTo(target);
        }
    }
}
