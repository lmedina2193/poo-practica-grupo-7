# Pruebas del sistema de estudiantes

## Comprobaciones automatizadas

Ejecutar `mvn test` desde la carpeta que contiene pom.xml. El objetivo exec asociado a test ejecuta PruebasBasicas y falla si alguna comprobación lanza AssertionError. Se verifican 25 condiciones: registro, normalización, duplicados sin pérdida de información, búsqueda, inexistencia en notas/promedio, ausencia de notas, cero, media decimal, límites y valores no finitos, datos obligatorios, orden y protección de las colecciones.

## Demostración manual en NetBeans

| Caso | Pasos | Resultado esperado |
| --- | --- | --- |
| Listado vacío | Iniciar; opción 2 | No hay estudiantes registrados |
| Registro | Opción 1; E001; Ana Perez | Confirmación de registro |
| Búsqueda | Opción 3; e001 | E001 y Ana Perez |
| Sin notas | Opción 5; E001 | No hay promedio |
| Duplicado | Opción 1; E001; Otro Nombre | Error de duplicado; Ana permanece |
| Nota válida | Opción 4; E001; 16 | Nota registrada |
| Segunda nota | Opción 4; E001; 18 | Nota registrada |
| Promedio | Opción 5; E001 | 17.00 |
| Texto inválido | Opción 4; E001; abc | Error numérico; menú disponible |
| Fuera de escala | Opción 4; E001; 21 | Error; no se agrega la nota |
| Código inexistente | Opción 3; X999 | No existe un estudiante |
| Nota a inexistente | Opción 4; X999 | No existe; vuelve al menú sin pedir nota |
| Decimal con coma | Opción 4; E001; 15,5 | Registra 15.5 |
| Menú inválido | Introducir texto o 9 como opción | Opción inválida; continúa |
| Cierre | Opción 0 | Programa finalizado |

También se debe probar nombre/código vacío y los extremos 0 y 20. Una nueva ejecución comienza con la colección vacía; esto corresponde al alcance en memoria.

## Evidencias y límites

Los archivos en evidencias contienen salidas reales de las comprobaciones y escenarios de consola. Son registros de texto, no capturas de NetBeans. Para la presentación, cada integrante debe ejecutar los escenarios y capturar su propia ventana Output. La validación automatizada no reemplaza que el equipo pueda explicar el código.

La aplicación se verifica con Java y con Maven. La apertura y el uso manual del menú dentro de la interfaz de NetBeans deben realizarse siguiendo README.md.
