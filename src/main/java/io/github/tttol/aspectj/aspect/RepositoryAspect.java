package io.github.tttol.aspectj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
public class RepositoryAspect {
    /*
     メソッド名
     作成者
     更新者
    */

    @Before("execution(* io.github.tttol.aspectj.repository.*Repository.insert(..))")
    public void setValue(final JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException {
//        final var methodSignature = (MethodSignature) joinPoint.getSignature();
//        final var methodName = methodSignature.getMethod().getName();
        final var argEntity = joinPoint.getArgs()[0];

        final var setCreatedByMethod = ReflectionUtils.findMethod(argEntity.getClass(), "setCreatedBy", String.class);
        if (Objects.nonNull(setCreatedByMethod)) {
            setCreatedByMethod.invoke(argEntity, "hogeUser");
        }

        final var setCreatedAtMethod = ReflectionUtils.findMethod(argEntity.getClass(), "setCreatedAt", LocalDateTime.class);
        if (Objects.nonNull(setCreatedAtMethod)) {
            setCreatedAtMethod.invoke(argEntity, LocalDateTime.now());
        }

        final var setUpdatedByMethod = ReflectionUtils.findMethod(argEntity.getClass(), "setUpdatedBy", String.class);
        if (Objects.nonNull(setUpdatedByMethod)) {
            setUpdatedByMethod.invoke(argEntity, "hogeUser");
        }

        final var setUpdatedAtMethod = ReflectionUtils.findMethod(argEntity.getClass(), "setUpdatedAt", LocalDateTime.class);
        if (Objects.nonNull(setUpdatedAtMethod)) {
            setUpdatedAtMethod.invoke(argEntity, LocalDateTime.now());
        }
    }
}
