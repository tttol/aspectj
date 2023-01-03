package io.github.tttol.aspectj.dto;

import com.google.common.hash.Hashing;
import io.github.tttol.aspectj.form.UserInfoForm;
import io.github.tttol.aspectj.mbgenerate.entity.UserInfo;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public record UserInfoDto(String id,
                          String name,
                          String email,
                          String createdBy,
                          LocalDateTime createdAt,
                          String updatedBy,
                          LocalDateTime updateAt) {
    public static UserInfoDto generateFromEntity(final UserInfo entity) {
        return new UserInfoDto(entity.getId(), entity.getName(), entity.getEmail(),
                entity.getCreatedBy(), entity.getCreatedAt(), entity.getUpdatedBy(),
                entity.getUpdatedAt());
    }

    public static UserInfoDto generateFromForm(final UserInfoForm form) {
        final var id = Hashing.sha256()
                .hashString(Long.toString(System.currentTimeMillis()), StandardCharsets.UTF_8)
                .toString();
        return new UserInfoDto(id, form.name(), form.email(), null, null, null, null);
    }

    public UserInfoForm toForm() {
        return new UserInfoForm(this.id, this.name, this.email);
    }

    public UserInfo toEntity() {
        final var entity = new UserInfo();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setEmail(this.email);

        return entity;
    }
}
