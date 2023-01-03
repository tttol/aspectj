package io.github.tttol.aspectj.repository;

import io.github.tttol.aspectj.mbgenerate.crud.UserInfoMapper;
import io.github.tttol.aspectj.mbgenerate.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserInfoRepository {
    private final UserInfoMapper userInfoMapper;

    public Optional<UserInfo> selectByPrimaryKey(final String id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    public List<UserInfo> selectAll() {
        return userInfoMapper.select(c -> c);
    }

    public int insert(final UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }
}
