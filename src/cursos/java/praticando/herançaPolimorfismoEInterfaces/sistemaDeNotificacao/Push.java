package cursos.java.praticando.herançaPolimorfismoEInterfaces.sistemaDeNotificacao;

class Push extends Notificacao {
    private String titulo;

    public Push(String destinatario, String mensagem, String titulo) {
        super(destinatario, mensagem);
        this.titulo = titulo;
    }

    @Override
    public void enviar() {
        System.out.printf("\nEnviando Push para: %s\nTítulo: %s\nConteúdo: %s", destinatario, titulo, mensagem);
    }
}
