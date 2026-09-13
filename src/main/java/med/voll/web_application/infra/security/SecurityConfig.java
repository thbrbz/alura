package med.voll.web_application.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsServiceBean() {
        UserDetails user1 = User.builder().username("admin").password("{noop}admin").roles("ADMIN").build();
        UserDetails user2 = User.builder().username("user").password("{noop}user").roles("USER").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
