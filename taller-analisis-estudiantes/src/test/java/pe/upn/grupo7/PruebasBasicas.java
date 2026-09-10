package pe.upn.grupo7;

import java.util.NoSuchElementException;

/** Pruebas sin bibliotecas externas. Un fallo termina con AssertionError. */
public final class PruebasBasicas {
    private static int comprobaciones;

    private static void verificar(boolean condicion, String nombre) {
        if (!condicion) { throw new AssertionError(nombre); }
        comprobaciones++;
        System.out.println("OK: " + nombre);
    }

    private static void rechaza(Class<? extends Throwable> tipo, Runnable accion,
                                String nombre) {
        try {
            accion.run();
        } catch (Throwable e) {
            verificar(tipo.isInstance(e), nombre);
            return;
        }
        throw new AssertionError("Debio rechazar: " + nombre);
    }

    public static void main(String[] args) {
        GestorEstudiantes gestor = new GestorEstudiantes();
        verificar(gestor.listarEstudiantes().isEmpty(), "Listado inicial vacio");
        gestor.registrarEstudiante(" e001 ", " Ana Perez ");
        Estudiante ana = gestor.buscarEstudiante("E001").orElseThrow();
        verificar(ana.getNombre().equals("Ana Perez"), "Registro y limpieza del nombre");
        verificar(gestor.buscarEstudiante(" e001 ").orElseThrow() == ana,
                "Busqueda normaliza codigo");
        rechaza(IllegalArgumentException.class,
                () -> gestor.registrarEstudiante("e001", "Otra persona"), "Codigo duplicado");
        verificar(gestor.listarEstudiantes().size() == 1, "Duplicado no altera registro");
        verificar(gestor.buscarEstudiante("X").isEmpty(), "Busqueda inexistente");
        rechaza(NoSuchElementException.class,
                () -> gestor.registrarCalificacion("X", 10), "Nota de estudiante inexistente");
        rechaza(NoSuchElementException.class,
                () -> gestor.calcularPromedio("X"), "Promedio de estudiante inexistente");
        verificar(gestor.calcularPromedio("E001").isEmpty(), "Sin notas no hay promedio");
        gestor.registrarCalificacion("E001", 0);
        verificar(gestor.calcularPromedio("E001").orElseThrow() == 0, "Promedio cero valido");
        gestor.registrarCalificacion("E001", 20);
        gestor.registrarCalificacion("E001", 15.5);
        verificar(Math.abs(gestor.calcularPromedio("E001").orElseThrow() - 35.5 / 3) < 1e-9,
                "Promedio aritmetico con decimales");
        for (double nota : new double[]{-1, 20.01, Double.NaN, Double.POSITIVE_INFINITY}) {
            rechaza(IllegalArgumentException.class,
                    () -> gestor.registrarCalificacion("E001", nota), "Nota invalida " + nota);
        }
        verificar(ana.getCalificaciones().size() == 3, "Notas invalidas no alteran historial");
        for (String codigo : new String[]{null, "", "   "}) {
            rechaza(IllegalArgumentException.class,
                    () -> gestor.registrarEstudiante(codigo, "Ana"), "Codigo vacio o nulo");
        }
        for (String nombre : new String[]{null, "", "   "}) {
            rechaza(IllegalArgumentException.class,
                    () -> gestor.registrarEstudiante("E002", nombre), "Nombre vacio o nulo");
        }
        rechaza(UnsupportedOperationException.class,
                () -> ana.getCalificaciones().add(99.0), "Notas protegidas contra escritura externa");
        gestor.registrarEstudiante("E002", "Luis");
        verificar(gestor.listarEstudiantes().get(1).getCodigo().equals("E002"),
                "Listado conserva orden de registro");
        rechaza(UnsupportedOperationException.class,
                () -> gestor.listarEstudiantes().clear(), "Listado protegido contra borrado externo");
        System.out.println("RESULTADO: " + comprobaciones + " comprobaciones correctas.");
    }
}
