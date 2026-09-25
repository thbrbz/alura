package med.voll.web_application.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final int UMA_DIA = 60*60*24;

    @Value("${api.auth.secret.key}")
    private String secretKey;

    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http, OncePerRequestFilter filtroAlteracaoSenha) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "/assets/**", "/",
                            "/index", "/home", "/esqueci-minha-senha").permitAll();
                    /*req.requestMatchers("/pacientes/**").hasRole("ATENDENTE");
                    req.requestMatchers(HttpMethod.GET, "/medicos").hasAnyRole("ATENDENTE", "PACIENTE");
                    req.requestMatchers("/medicos/**").hasRole("ATENDENTE");
                    req.requestMatchers(HttpMethod.POST, "/consultas/**").hasAnyRole("ATENDENTE", "PACIENTE");
                    req.requestMatchers(HttpMethod.PUT, "/consultas/**").hasAnyRole("ATENDENTE", "PACIENTE");*/ // -> Configuração feita diretamente nos controllers.
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(filtroAlteracaoSenha, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout ->
                        logout.logoutSuccessUrl("/login?logout").permitAll())
                .rememberMe(rememberMe ->
                        rememberMe.key(this.secretKey)
                                //.alwaysRemember(true)           // -> Caso queira que o login seja sempre lembrado, criando o cookie remember-me com validade de 2 semanas.
                                .tokenValiditySeconds(UMA_DIA))   // -> Válido por um dia.
                .csrf(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder  passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    /*
    --> Caso queira configurar usuários em memória de execução
    @Bean
    public UserDetailsService userDetailsServiceBean() {
        UserDetails user1 = User.builder().username("admin@email.com").password("{noop}admin").roles("ADMIN").build();
        UserDetails user2 = User.builder().username("user@email.com").password("{noop}user").roles("USER").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    --> Caso fosse necessário configurar o AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManagerBean(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    } */
}
