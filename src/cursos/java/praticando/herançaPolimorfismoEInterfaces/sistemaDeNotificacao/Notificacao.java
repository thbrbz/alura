package cursos.java.praticando.herançaPolimorfismoEInterfaces.sistemaDeNotificacao;

public class Notificacao {
    protected String destinatario;
    protected String mensagem;

    public Notificacao(String destinatario, String mensagem) {
        this.destinatario = destinatario;
        this.mensagem = mensagem;
    }

    void enviar() {};
}
