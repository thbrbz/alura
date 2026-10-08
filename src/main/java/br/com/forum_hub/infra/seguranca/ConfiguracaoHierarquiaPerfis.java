package br.com.forum_hub.infra.seguranca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration
public class ConfiguracaoHierarquiaPerfis {

    @Bean
    public RoleHierarchy roleHierarchyBean() {
        /*String hierarchy = "ROLE_ADMIN > ROLE_MODERADOR\n" +
                "ROLE_MODERADOR > ROLE_INSTRUTOR\n" +
                "ROLE_MODERADOR > ROLE_ESTUDANTE";            -> Uma outra maneira de definir a hierarquia dos perfis

        return RoleHierarchyImpl.fromHierarchy(hierarchy);*/

        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("MODERADOR")
                .role("MODERADOR").implies("ESTUDANTE", "INSTRUTOR")
                .build();
    }
}
