# Taller de análisis de problemas - Grupo 7

Proyecto Java de consola para NetBeans. Requiere JDK 17 o posterior. No requiere base de datos ni bibliotecas externas para la lógica del programa.

## Abrir en NetBeans

1. En el repositorio, selecciona la rama `taller-analisis-estudiantes`.
2. En NetBeans, usa **File > Open Project** y selecciona esta carpeta, la que contiene `pom.xml` (no la raíz del repositorio).
3. Espera a que Maven termine de preparar el proyecto. La primera vez puede necesitar Internet para descargar plugins.
4. En **Source Packages > pe.upn.grupo7**, abre `Main.java` y elige **Run File**. También puedes ejecutar el proyecto y seleccionar `pe.upn.grupo7.Main` como clase principal si lo solicita.
5. Escribe las respuestas en la ventana **Output**. Introduce una respuesta por línea.
6. Para pruebas, usa **Test** sobre el proyecto o ejecuta `mvn test`. La salida debe contener `RESULTADO: 25 comprobaciones correctas.` y `BUILD SUCCESS`. Las comprobaciones las ejecuta `PruebasBasicas` mediante el plugin exec durante la fase test; no se presentan como pruebas JUnit en el panel gráfico.

## Uso del menú

| Opción | Operación |
| --- | --- |
| 1 | Registrar código y nombre de un estudiante |
| 2 | Listar estudiantes y sus notas |
| 3 | Buscar por código |
| 4 | Agregar una nota al estudiante |
| 5 | Calcular su promedio |
| 0 | Cerrar |

Ejemplo: registra `E001`, nombre `Ana Perez`; agrega las notas `16` y `18`; consulta el promedio: `17.00`. Intenta registrar otra vez `e001`: debe rechazar el duplicado. Consulta `X999`: debe informar que no existe.

Las notas aceptan punto o coma decimal. La escala de 0 a 20 es una decisión del equipo que debe confirmarse con el docente. Los datos se guardan solo en memoria y se pierden al cerrar. El promedio es simple, sin ponderación.

## Archivos y entregables

- `docs/ANALISIS.md`: problema, objetos, atributos, métodos, reglas, colecciones y errores.
- `docs/PRUEBAS.md`: casos de prueba y procedimiento de demostración.
- `docs/Analisis_Grupo_7.docx`: versión Word del análisis para compartir.
- `src/main/java/pe/upn/grupo7/`: implementación funcional.
- `src/test/java/pe/upn/grupo7/PruebasBasicas.java`: comprobaciones automatizadas.
- `evidencias/`: salidas de pruebas reales, identificadas como registros de texto.

## Ejecutar sin NetBeans

Desde esta carpeta, con Maven y Java disponibles:

```text
mvn test
mvn compile exec:java
```

## Trabajo del grupo y evidencias

El proyecto se prepara en una rama separada de `main`. Antes de fusionar, los compañeros deben revisar el análisis, ejecutar los casos y dejar una revisión real en el PR. Esta base no acredita commits individuales de quienes todavía no han participado.

Para obtener capturas reales, mostrar el proyecto abierto en NetBeans, registro/listado/búsqueda, notas y promedio, rechazo de duplicados y datos inválidos, y el resultado de las pruebas. El nuevo PDF exige pruebas básicas, pero no establece un formato específico de capturas. Si sigue vigente la práctica anterior, conservar también sus evidencias individuales de Git y revisión.

No subir `target/` ni archivos `.class`. No copiar las clases de este proyecto dentro del `src` de la práctica anterior: se abre como un proyecto independiente dentro del mismo repositorio.
