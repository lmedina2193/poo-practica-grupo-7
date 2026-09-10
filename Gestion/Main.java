import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionEstudiantes sistema = new GestionEstudiantes();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Listar estudiantes");
            System.out.println("3. Buscar estudiante");
            System.out.println("4. Registrar calificacion");
            System.out.println("5. Salir");
            System.out.print("Opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1:
                        System.out.print("Codigo: ");
                        String cod = sc.nextLine().trim();
                        System.out.print("Nombre: ");
                        String nom = sc.nextLine().trim();
                        sistema.registrarEstudiante(cod, nom);
                        break;
                    case 2:
                        sistema.listarEstudiantes();
                        break;
                    case 3:
                        System.out.print("Codigo a buscar: ");
                        String codB = sc.nextLine().trim();
                        Estudiante e = sistema.buscarPorCodigo(codB);
                        if (e != null) {
                            System.out.println("Encontrado -> " + e);
                        } else {
                            System.out.println("Error: Estudiante no encontrado.");
                        }
                        break;
                    case 4:
                        System.out.print("Codigo del estudiante: ");
                        String codN = sc.nextLine().trim();
                        System.out.print("Nota (0-20): ");
                        double nota = Double.parseDouble(sc.nextLine());
                        sistema.registrarCalificacion(codN, nota);
                        break;
                    case 5:
                        System.out.println("Hasta luego!");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero valido.");
            }
        } while (opcion != 5);

        sc.close();
    }
}