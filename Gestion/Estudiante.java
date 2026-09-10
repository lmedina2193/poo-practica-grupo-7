import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String codigo;
    private String nombre;
    private List<Double> calificaciones;

    public Estudiante(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.calificaciones = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean agregarCalificacion(double nota) {
        if (nota < 0 || nota > 20) {
            System.out.println("Error: La nota debe estar entre 0 y 20.");
            return false;
        }
        calificaciones.add(nota);
        return true;
    }

    public double calcularPromedio() {
        if (calificaciones.isEmpty()) return 0.0;
        double suma = 0;
        for (double nota : calificaciones) {
            suma += nota;
        }
        return suma / calificaciones.size();
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + " | Nombre: " + nombre + 
               " | Notas: " + calificaciones + " | Promedio: " + String.format("%.2f", calcularPromedio());
    }
}