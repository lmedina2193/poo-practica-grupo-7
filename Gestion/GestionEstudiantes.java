import java.util.HashMap;
import java.util.Map;

public class GestionEstudiantes {
    private Map<String, Estudiante> estudiantes = new HashMap<>();

    public void registrarEstudiante(String codigo, String nombre) {
        if (codigo == null || codigo.trim().isEmpty() || nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: Código y nombre no pueden estar vacíos.");
            return;
        }
        
        // Evita códigos duplicados
        if (estudiantes.containsKey(codigo)) {
            System.out.println("Error: Ya existe un estudiante con el código " + codigo);
            return;
        }

        estudiantes.put(codigo, new Estudiante(codigo, nombre));
        System.out.println("Estudiante registrado exitosamente.");
    }

    public Estudiante buscarPorCodigo(String codigo) {
        return estudiantes.get(codigo);
    }

    public void registrarCalificacion(String codigo, double nota) {
        Estudiante e = buscarPorCodigo(codigo);
        if (e == null) {
            System.out.println("Error: El estudiante con código " + codigo + " no existe.");
            return;
        }
        if (e.agregarCalificacion(nota)) {
            System.out.println("Calificación registrada con éxito.");
        }
    }

    // Método corregido para listar los estudiantes
    public void listarEstudiantes() {
        if (estudiantes == null || estudiantes.isEmpty()) {
            System.out.println("\n--- LISTA DE ESTUDIANTES ---");
            System.out.println("No hay estudiantes registrados en el sistema.");
            return;
        }

        System.out.println("\n--- LISTA DE ESTUDIANTES (" + estudiantes.size() + ") ---");
        for (Estudiante e : estudiantes.values()) {
            System.out.println(e.toString());
        }
    }
}