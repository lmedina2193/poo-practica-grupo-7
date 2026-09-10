public class Estudiante {

    private String nombre;
    private int edad;
    private String codigo;

    public Estudiante(String nombre, int edad, String codigo) {
        this.nombre = nombre;
        this.edad = edad;
        this.codigo = codigo;
    }

    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);
        System.out.println("Codigo: " + codigo);
    }
}