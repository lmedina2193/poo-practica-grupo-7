package pe.upn.grupo7;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

/** Administra el registro unico de estudiantes, sin depender de la consola. */
public final class GestorEstudiantes {
    private final Map<String, Estudiante> estudiantes = new LinkedHashMap<>();

    public void registrarEstudiante(String codigo, String nombre) {
        Estudiante nuevo = new Estudiante(codigo, nombre);
        if (estudiantes.containsKey(nuevo.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un estudiante con el codigo "
                    + nuevo.getCodigo() + ".");
        }
        estudiantes.put(nuevo.getCodigo(), nuevo);
    }

    public List<Estudiante> listarEstudiantes() {
        return List.copyOf(estudiantes.values());
    }

    public Optional<Estudiante> buscarEstudiante(String codigo) {
        return Optional.ofNullable(estudiantes.get(Estudiante.normalizarCodigo(codigo)));
    }

    private Estudiante exigirEstudiante(String codigo) {
        return buscarEstudiante(codigo).orElseThrow(() ->
                new NoSuchElementException("No existe un estudiante con ese codigo."));
    }

    public void registrarCalificacion(String codigo, double nota) {
        exigirEstudiante(codigo).registrarCalificacion(nota);
    }

    public OptionalDouble calcularPromedio(String codigo) {
        return exigirEstudiante(codigo).calcularPromedio();
    }
}
