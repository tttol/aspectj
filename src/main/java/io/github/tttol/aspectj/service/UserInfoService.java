package io.github.tttol.aspectj.service;

import io.github.tttol.aspectj.dto.UserInfoDto;
import io.github.tttol.aspectj.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Transactional
    public List<UserInfoDto> getAllUsers() {
        return userInfoRepository.selectAll()
                .stream().map(UserInfoDto::generateFromEntity).toList();
    }

    @Transactional
    public int register(final UserInfoDto userInfoDto) {
        return userInfoRepository.insert(userInfoDto.toEntity());
    }
}
