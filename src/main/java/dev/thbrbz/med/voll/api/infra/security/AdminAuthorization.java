package dev.thbrbz.med.voll.api.infra.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority(T(dev.thbrbz.med.voll.api.domain.usuario.Role).ROLE_ADMIN)")
public @interface AdminAuthorization {
}
