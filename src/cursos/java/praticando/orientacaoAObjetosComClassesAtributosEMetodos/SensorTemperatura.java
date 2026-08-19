package cursos.java.praticando.orientacaoAObjetosComClassesAtributosEMetodos;

public class SensorTemperatura {
    String local;
    double temperaturaAtual;

    void exibirRelatorio() {
        System.out.println("Sensor no local: " + local);
        System.out.printf("Temperatura: %.1f ºC\n", temperaturaAtual);

        if (temperaturaAtual > 37.5)
            System.out.println("Alerta: Temperatura acima do limite!");
    }

    public static void main(String[] args) {
        SensorTemperatura sensor = new SensorTemperatura();
        sensor.local = "Setor A";
        sensor.temperaturaAtual = 39.2;

        sensor.exibirRelatorio();
    }
}
