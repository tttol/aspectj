package io.github.tttol.aspectj.aspect;

import io.github.tttol.aspectj.mbgenerate.crud.UserInfoMapper;
import io.github.tttol.aspectj.mbgenerate.entity.UserInfo;
import io.github.tttol.aspectj.repository.UserInfoRepository;
import org.apache.ibatis.executor.BatchResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.GeneralInsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertSelectStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryAspectTest {
    private static final LocalDateTime NOW = LocalDateTime.of(1970, 1, 1, 0, 0, 0);

    @Test
    @DisplayName("insertメソッド実行時に作成/更新者・作成/更新日時がセットされていること")
    void testInsert() {
        try (final MockedStatic<LocalDateTime> localDateTime = Mockito.mockStatic(LocalDateTime.class)) {
            localDateTime.when(LocalDateTime::now).thenReturn(NOW);
            final var userInfoRepository = new UserInfoRepository(new UserInfoMapper() {
                @Override
                public List<UserInfo> selectMany(final SelectStatementProvider selectStatement) {
                    return null;
                }

                @Override
                public Optional<UserInfo> selectOne(final SelectStatementProvider selectStatement) {
                    return Optional.empty();
                }

                @Override
                public long count(final SelectStatementProvider selectStatement) {
                    return 0;
                }

                @Override
                public int delete(final DeleteStatementProvider deleteStatement) {
                    return 0;
                }

                @Override
                public int insert(final InsertStatementProvider<UserInfo> insertStatement) {
                    return 0;
                }

                @Override
                public int generalInsert(final GeneralInsertStatementProvider insertStatement) {
                    return 0;
                }

                @Override
                public int insertSelect(final InsertSelectStatementProvider insertSelectStatement) {
                    return 0;
                }

                @Override
                public int insertMultiple(final MultiRowInsertStatementProvider<UserInfo> insertStatement) {
                    return 0;
                }

                @Override
                public List<BatchResult> flush() {
                    return null;
                }

                @Override
                public int update(final UpdateStatementProvider updateStatement) {
                    return 0;
                }
            });

            final var factory = new AspectJProxyFactory(userInfoRepository);
            factory.addAspect(new RepositoryAspect());

            final var userInfo = new UserInfo();
            final var proxy = (UserInfoRepository) factory.getProxy();
            proxy.insert(userInfo);
            assertThat(userInfo).extracting("createdBy", "createdAt", "updatedBy", "updatedAt")
                    .containsExactly("hogeUser", NOW, "hogeUser", NOW);
        }
    }
}
