package br.com.forum_hub.domain.usuario;

public record DadosListagemUsuario(Long id,
                                   String email,
                                   String nome,
                                   String username,
                                   String miniBiografia,
                                   String biografia
) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername(),
                usuario.getNome(), usuario.getUsername(),
                usuario.getMiniBiografia(), usuario.getBiografia());
    }
}
