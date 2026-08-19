package cursos.java.praticando.herançaPolimorfismoEInterfaces.controleDeReservas;

public class Reserva {

    public void reservar() {
        System.out.println("Reserva realizada");
    }

    public void reservar(String date) {
        System.out.printf("\nReserva feita para o dia %s", date);
    }

    public void reservar(String date, int quantidade) {
        System.out.printf("\nReserva feita para o dia %s para %d pessoas", date, quantidade);
    }
}
