package pe.upn.grupo7;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;

/** Representa un estudiante y protege la validez de sus calificaciones. */
public final class Estudiante {
    private final String codigo;
    private final String nombre;
    private final List<Double> calificaciones = new ArrayList<>();

    public Estudiante(String codigo, String nombre) {
        this.codigo = normalizarCodigo(codigo);
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        this.nombre = nombre.strip();
    }

    static String normalizarCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio.");
        }
        return codigo.strip().toUpperCase(Locale.ROOT);
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    /** Devuelve una copia inmutable para impedir notas sin validacion. */
    public List<Double> getCalificaciones() {
        return List.copyOf(calificaciones);
    }

    public void registrarCalificacion(double nota) {
        if (!Double.isFinite(nota) || nota < 0 || nota > 20) {
            throw new IllegalArgumentException("La nota debe ser un numero entre 0 y 20.");
        }
        calificaciones.add(nota);
    }

    /** Ausencia de promedio y promedio cero son resultados diferentes. */
    public OptionalDouble calcularPromedio() {
        return calificaciones.stream().mapToDouble(Double::doubleValue).average();
    }
}
