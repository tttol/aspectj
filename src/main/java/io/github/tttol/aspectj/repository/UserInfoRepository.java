package io.github.tttol.aspectj.repository;

import io.github.tttol.aspectj.mbgenerate.crud.UserInfoMapper;
import io.github.tttol.aspectj.mbgenerate.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserInfoRepository {
    private final UserInfoMapper userInfoMapper;

    public List<UserInfo> selectAll() {
        return this.userInfoMapper.select(c -> c);
    }

    public int insert(final UserInfo userInfo) {
        return this.userInfoMapper.insert(userInfo);
    }
}
