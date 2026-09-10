package pe.upn.grupo7;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.Scanner;

/** Punto de entrada. Lee lineas completas para evitar problemas con nextInt(). */
public final class Main {
    private final Scanner entrada;
    private final GestorEstudiantes gestor = new GestorEstudiantes();

    public Main(Scanner entrada) { this.entrada = entrada; }

    public static void main(String[] args) {
        new Main(new Scanner(System.in)).ejecutar();
    }

    private String leer(String mensaje) {
        System.out.print(mensaje);
        if (!entrada.hasNextLine()) {
            throw new FinEntrada();
        }
        return entrada.nextLine().strip();
    }

    public void ejecutar() {
        System.out.println("GRUPO 7 - SISTEMA DE GESTION DE ESTUDIANTES");
        System.out.println("Datos en memoria: se pierden al cerrar. Notas de 0 a 20.");
        try {
            while (true) {
                System.out.println("\n1. Registrar estudiante\n2. Listar estudiantes"
                        + "\n3. Buscar por codigo\n4. Registrar calificacion"
                        + "\n5. Calcular promedio\n0. Salir");
                try {
                    switch (leer("Opcion: ")) {
                        case "1" -> registrar();
                        case "2" -> listar();
                        case "3" -> buscar();
                        case "4" -> calificar();
                        case "5" -> promedio();
                        case "0" -> {
                            System.out.println("Programa finalizado.");
                            return;
                        }
                        default -> System.out.println("Opcion invalida. Elige de 0 a 5.");
                    }
                } catch (IllegalArgumentException | NoSuchElementException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (FinEntrada e) {
            System.out.println("\nEntrada finalizada. Programa cerrado.");
        }
    }

    private void registrar() {
        String codigo = leer("Codigo: ");
        String nombre = leer("Nombre completo: ");
        gestor.registrarEstudiante(codigo, nombre);
        System.out.println("Estudiante registrado correctamente.");
    }

    private void listar() {
        List<Estudiante> lista = gestor.listarEstudiantes();
        if (lista.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            lista.forEach(this::mostrar);
        }
    }

    private void buscar() {
        gestor.buscarEstudiante(leer("Codigo: ")).ifPresentOrElse(this::mostrar,
                () -> System.out.println("No existe un estudiante con ese codigo."));
    }

    private void calificar() {
        String codigo = leer("Codigo: ");
        if (gestor.buscarEstudiante(codigo).isEmpty()) {
            System.out.println("No existe un estudiante con ese codigo.");
            return;
        }
        String texto = leer("Nota (0 a 20): ");
        double nota;
        try {
            nota = Double.parseDouble(texto.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingresa una nota numerica; por ejemplo, 15.5.");
        }
        gestor.registrarCalificacion(codigo, nota);
        System.out.println("Calificacion registrada correctamente.");
    }

    private void promedio() {
        OptionalDouble resultado = gestor.calcularPromedio(leer("Codigo: "));
        if (resultado.isPresent()) {
            System.out.printf(Locale.ROOT, "Promedio: %.2f%n", resultado.getAsDouble());
        } else {
            System.out.println("El estudiante no tiene calificaciones; no hay promedio.");
        }
    }

    private void mostrar(Estudiante estudiante) {
        System.out.println(estudiante.getCodigo() + " | " + estudiante.getNombre()
                + " | Notas: " + estudiante.getCalificaciones());
    }

    private static final class FinEntrada extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
