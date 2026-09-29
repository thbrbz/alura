package br.com.forum_hub.domain.usuario;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {

    private static final long TEMPO_EXPIRACAO_TOKEN = 30;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String username;
    private String biografia;
    private  String miniBiografia;
    private String refreshToken;
    private LocalDateTime expiracaoRefreshToken;
    private String token;
    private LocalDateTime expiracaoToken;
    private Boolean verificado;

    @Deprecated
    public Usuario() {}

    public Usuario(DadosCadastroUsuario dados, String senhaCriptografada) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.senha = senhaCriptografada;
        this.username = dados.username();
        this.biografia = dados.biografia();
        this.miniBiografia = dados.miniBiografia();
        this.token = UUID.randomUUID().toString();
        this.expiracaoToken = LocalDateTime.now().plusMinutes(TEMPO_EXPIRACAO_TOKEN);
        this.verificado = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getMiniBiografia() {
        return miniBiografia;
    }

    public Long getId() {
        return this.id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiracaoToken() {
        return expiracaoToken;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public boolean refreshTokenExpirado() {
        return expiracaoRefreshToken.isBefore(LocalDateTime.now());
    }

    public String novoRefreshToken() {
        this.refreshToken = UUID.randomUUID().toString();
        this.expiracaoRefreshToken = LocalDateTime.now().plusMinutes(120);
        return refreshToken;
    }

    public void verificar() {
        this.verificado = true;
        this.token = null;
        this.expiracaoToken = null;
    }
}
