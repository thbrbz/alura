package med.voll.web_application.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final int UMA_DIA = 60*60*24;

    @Value("${api.auth.secret.key}")
    private String secretKey;

    @Bean
    public UserDetailsService userDetailsServiceBean() {
        UserDetails user1 = User.builder().username("admin@email.com").password("{noop}admin").roles("ADMIN").build();
        UserDetails user2 = User.builder().username("user@email.com").password("{noop}user").roles("USER").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "/assets/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout ->
                        logout.logoutSuccessUrl("/login?logout").permitAll())
                .rememberMe(rememberMe ->
                        rememberMe.key(this.secretKey)
                                //.alwaysRemember(true)           // -> Caso queira que o login seja sempre lembrado, criando o cookie remember-me com validade de 2 semanas.
                                .tokenValiditySeconds(UMA_DIA))   // -> Válido por um dia.
                .build();
    }
}
