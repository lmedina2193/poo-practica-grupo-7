import java.util.ArrayList;

public class Curso {

    private String nombre;
    private String codigo;
    private ArrayList<Estudiante> estudiantes;

    public Curso(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public void mostrarInformacion() {
        System.out.println("Curso: " + nombre);
        System.out.println("Codigo: " + codigo);
        System.out.println("Estudiantes:");

        for (Estudiante estudiante : estudiantes) {
            estudiante.mostrarInformacion();
            System.out.println("--------------------");
        }
    }
}
