package cursos.java.praticando.encapsulamento;

public class Usuario2 {
    private String login;
    private String senha;

    public Usuario2(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public boolean validarSenha(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    public String getLogin() {
        return this.login;
    }
}
