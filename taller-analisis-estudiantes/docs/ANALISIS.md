# Taller de análisis de problemas

Grupo 7 - Técnicas de programación orientada a objetos

## 1 Descripción del problema

Una institución educativa necesita registrar estudiantes y administrar sus calificaciones. El usuario operador necesita consultar la lista, localizar a una persona por su código, agregar notas y obtener su promedio. El sistema debe impedir códigos duplicados, informar búsquedas fallidas y controlar errores de ingreso sin interrumpir inesperadamente la sesión.

Entradas: opción de menú, código, nombre y nota. Salidas: confirmaciones, listado, datos del estudiante, promedio y mensajes comprensibles de error. El resultado esperado es un registro consistente: cada código identifica a un único estudiante y solo se incluyen notas válidas en el promedio.

El alcance es una aplicación de consola en Java para NetBeans, con datos en memoria. La persistencia, los usuarios con contraseña, los cursos, las matrículas y las notas ponderadas no se especifican en el taller. No se agregan como requisitos.

## 2 Identificación de objetos

Estudiante es el objeto central del dominio. Calificación representa un valor numérico asociado al estudiante; se modela con Double porque el enunciado no pide fecha, curso o tipo de evaluación. Institución es el contexto del problema, sin comportamiento propio requerido. GestorEstudiantes es el servicio que administra la colección y la unicidad. Main es la interfaz de consola, responsable del diálogo con el operador.

La relación es: Main usa un GestorEstudiantes; el gestor administra varios Estudiante; cada estudiante contiene cero o más calificaciones. No hace falta herencia para estas responsabilidades; se utiliza composición y encapsulamiento.

## 3 Atributos de cada clase

| Clase | Atributo | Tipo y significado |
| --- | --- | --- |
| Estudiante | codigo | String, identificador obligatorio, normalizado e inmutable |
| Estudiante | nombre | String, nombre obligatorio e inmutable |
| Estudiante | calificaciones | List<Double>, lista privada de notas |
| GestorEstudiantes | estudiantes | Map<String, Estudiante>, registro indexado por código |
| Main | entrada | Scanner, lectura de líneas de consola |
| Main | gestor | GestorEstudiantes, servicio utilizado por el menú |

Los atributos son privados. Las listas se consultan mediante copias inmutables. Así, el usuario de la clase no puede agregar una nota inválida directamente a la colección. El estudiante puede recibir nuevas notas mediante su método validado, pero no cambiar su código después de registrarse.

## 4 Responsabilidades y métodos

| Clase | Métodos principales | Responsabilidad |
| --- | --- | --- |
| Estudiante | constructor, getCodigo, getNombre | Crear y consultar un estudiante válido |
| Estudiante | normalizarCodigo | Quitar espacios exteriores y convertir a mayúsculas |
| Estudiante | registrarCalificacion | Validar y agregar una nota |
| Estudiante | getCalificaciones | Consultar notas sin exponer la lista mutable |
| Estudiante | calcularPromedio | Retornar media aritmética u OptionalDouble vacío |
| GestorEstudiantes | registrarEstudiante | Impedir duplicados e incorporar estudiantes |
| GestorEstudiantes | listarEstudiantes, buscarEstudiante | Listar en orden de registro y buscar por código |
| GestorEstudiantes | registrarCalificacion, calcularPromedio | Localizar al estudiante o informar su ausencia y delegar la operación |
| Main | main, ejecutar | Iniciar y mantener el menú |
| Main | leer, registrar, listar, buscar, calificar, promedio, mostrar | Traducir entradas, invocar el servicio y presentar resultados |

PruebasBasicas es una clase de verificación, no un objeto del negocio. Ejecuta escenarios de éxito y error, y lanza AssertionError cuando una condición no se cumple. FinEntrada es una excepción interna que permite finalizar limpiamente si se termina el flujo de entrada.

## 5 Reglas de negocio

1. El código y el nombre son obligatorios; no se aceptan valores nulos ni solo espacios.
2. El código es único. La comparación ignora mayúsculas/minúsculas y espacios exteriores: E001, e001 y " E001 " identifican al mismo estudiante.
3. Un intento duplicado no reemplaza al estudiante ni borra sus notas.
4. Solo se registran calificaciones para un estudiante existente.
5. Supuesto de esta solución: notas entre 0 y 20, inclusive; admite decimales. El PDF no define la escala, por lo que debe confirmarse con el docente.
6. No se admiten NaN ni infinito, aunque Java permita convertir esos textos a double.
7. El promedio es la suma de notas dividida entre su cantidad. Todas tienen el mismo peso.
8. Sin notas, se informa que no existe promedio. No se muestra cero, porque cero sí puede ser una nota válida.
9. Las notas repetidas son válidas: dos evaluaciones distintas pueden tener el mismo resultado. No se impone una cantidad máxima no solicitada.
10. La pantalla muestra el promedio con dos decimales; el cálculo conserva su precisión interna.

## 6 Colección seleccionada y justificación

Se declara Map<String, Estudiante> y se instancia LinkedHashMap. La clave es el código normalizado. Permite buscar y detectar duplicados en tiempo esperado O(1), además de conservar el orden de registro para listar. No ordena alfabéticamente: se eligió el orden de inserción por facilidad de lectura. Listar cuesta O(n), donde n es el número de estudiantes.

Una ArrayList de estudiantes también podría funcionar, pero exigiría recorrer la lista para cada búsqueda o validación de duplicado, con costo O(n). TreeMap sería adecuado si se requiriera ordenar por código, requisito ausente aquí. Un Set no facilita por sí solo obtener al estudiante por una clave sin definir igualdad o realizar búsquedas adicionales.

Para las calificaciones se usa ArrayList<Double>: conserva el orden, admite notas repetidas y permite incorporar nuevas notas. Calcular el promedio recorre m notas y cuesta O(m). No se usa Set porque eliminaría repeticiones válidas. El espacio total depende de los estudiantes y las notas registradas.

## 7 Errores potenciales y estrategia de manejo

| Situación | Manejo y estado final |
| --- | --- |
| Código o nombre vacío | IllegalArgumentException; no se registra al estudiante |
| Código duplicado | Mensaje claro; se conserva el registro original |
| Estudiante inexistente | Búsqueda devuelve Optional vacío; operaciones de notas/promedio lanzan NoSuchElementException y la consola muestra el mensaje |
| Texto en una nota | Se captura NumberFormatException y se explica el formato numérico |
| Nota fuera de escala, NaN o infinito | Se rechaza antes de modificar la lista |
| Estudiante sin notas | OptionalDouble vacío y mensaje sin división por cero |
| Opción de menú inválida | Se informa el error y vuelve el menú |
| Fin de entrada | Cierre controlado, sin bucle infinito |
| Listado vacío | Mensaje explícito de que no hay estudiantes |

Main lee líneas completas y convierte únicamente la nota. Esto evita la mezcla de nextInt y nextLine, que suele dejar saltos de línea pendientes. Se acepta coma decimal convirtiéndola a punto. Ante un dato inválido, se cancela esa operación y se vuelve al menú; el usuario puede intentarla otra vez. No se ocultan errores de programación con una captura genérica de Exception.

## 8 Implementación funcional

El código se encuentra en src/main/java/pe/upn/grupo7 y el proyecto Maven en pom.xml. El menú permite ejecutar todas las operaciones solicitadas. Las reglas están en las clases del dominio y del servicio, de modo que no dependen exclusivamente de lo que valide la interfaz. El almacenamiento está en memoria durante la sesión; cerrar el programa borra los datos.

## 9 Pruebas básicas y criterio de aceptación

La solución se acepta si registra estudiantes válidos, lista y busca correctamente, conserva un único registro por código, permite notas válidas, calcula promedios correctos y maneja errores sin alterar registros ni terminar la sesión. PruebasBasicas contiene 25 comprobaciones automatizadas. Los escenarios de consola y los pasos reproducibles se detallan en PRUEBAS.md.

Fuente de requisitos: Taller_Analisis_Problemas (3).pdf, página 1. Las decisiones adicionales de escala, normalización y almacenamiento se declaran en este documento para que el equipo pueda explicarlas y ajustarlas si el docente indica otras condiciones.
